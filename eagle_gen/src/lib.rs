use std::fs::File;
use std::io::{Read, BufRead, BufReader, Write};
use std::path::Path;
use serde_json::Value;

pub fn trans(s: &str, ref_value_to_move: &str, tmp_main: &str){
    let path = Path::new(s);
    let mut file = File::open(&path).expect("打开文件失败");
    let mut content = String::new();
    let _ = file.read_to_string(&mut content).expect("read content failed.");
    // 解析 JSON 字符串
    let mut data: Value = serde_json::from_str(&content).unwrap();
    let index_to_move = data["dependencies"].as_array().unwrap().iter().position(|dep| {
        let ref_value = dep["ref"].as_str().unwrap();
        ref_value.starts_with(tmp_main)
    });
    if let Some(index) = index_to_move {
        let _ = data["dependencies"].as_array_mut().unwrap().remove(index);
    }
    let index_to_move = data["dependencies"].as_array().unwrap().iter().position(|dep| {
        let ref_value = dep["ref"].as_str().unwrap();
        ref_value.starts_with(ref_value_to_move)
    });
    if let Some(index) = index_to_move {
        let dependency_to_move = data["dependencies"].as_array_mut().unwrap().remove(index);
        data["dependencies"].as_array_mut().unwrap().insert(0, dependency_to_move);
    }

    let index_to_move = data["components"].as_array().unwrap().iter().position(|dep| {
        let ref_value = dep["purl"].as_str().unwrap();
        ref_value.starts_with(ref_value_to_move)
    });
    if let Some(index) = index_to_move {
        let main_component = data["components"].as_array_mut().unwrap().remove(index);
        data["metadata"].as_object_mut().unwrap()["component"] = main_component
    }
    // 输出修改后的 JSON
    let modified_json = serde_json::to_string_pretty(&data).unwrap();
    let mut file = File::create("result.json").expect("创建文件失败");
    file.write_all(modified_json.as_bytes())
        .expect("写入文件失败");
}

#[cfg(test)]
mod test {
    #[test]
    pub fn ac() {
        super::trans("E:/projects/eagle_cyclonedx/eagle_gen/bom.json",
        "pkg:maven/org.apache.commons/commons-dbcp2@2.9.0",
            "pkg:maven/com.example/myproject@1.0.0?type=jar"
        );
    }
}