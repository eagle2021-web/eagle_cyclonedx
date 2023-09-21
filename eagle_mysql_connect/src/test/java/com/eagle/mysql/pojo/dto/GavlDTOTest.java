package com.eagle.mysql.pojo.dto;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class GavlDTOTest {
    @Test
    public void testAbc() throws IOException {
        String fileName = "fixtures/gavlList.json"; // 要读取的 JSON 文件名

        // 使用 ClassLoader 获取 JSON 文件的 InputStream
        InputStream inputStream = GavlDTOTest.class.getClassLoader().getResourceAsStream(fileName);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        String jsonContent = new String(buffer);
        JSONArray objects = JSONObject.parseArray(jsonContent);
        for (Object object : objects) {
            JSONObject jsonObj = (JSONObject) object;
            GavlDTO obj = jsonObj.getObject("obj", GavlDTO.class);
            System.out.println( JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue));
            Boolean expected = jsonObj.getBoolean("expected");
            boolean checkResult = GavlDTO.checkKeyValue(obj);
            Assertions.assertEquals(checkResult, expected);
        }
        inputStream.close();
    }
}
