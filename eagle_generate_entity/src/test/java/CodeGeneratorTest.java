import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Types;
import java.util.Collections;

public class CodeGeneratorTest {

    @Test
    public void genCode() {
        // https://blog.csdn.net/qq_42263280/article/details/126531993
        String url = "jdbc:mysql://h131:3306/hsp_mybatis?useSSL=true&requireSSL=true&verifyServerCertificate=false&characterEncoding=UTF-8";
        String projectPath = System.getProperty("user.dir");
        String root_java = projectPath + "/" + "src/test/java/";
        System.out.println(root_java);
        System.out.println("root");
        String comPrefix = "com/eagle/maven";
        String xmlPath = (root_java + comPrefix + "/mapper/xml");
        System.out.println(xmlPath);
        System.out.println("===");
        FastAutoGenerator.create(url, "root", "123456")
                .globalConfig(builder -> {
                    builder.author("eagle") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
                            .outputDir(root_java); // 指定输出目录
                })

                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent(comPrefix.replace("/", ".")) // 设置父包名
//                            .moduleName("mapper") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlPath)) // 设置mapperXml生成路径  怎么为mybatis plus生成器设置xml文件的输出位置
                            .entity("pojo.entity")

                    ;
                })
                .strategyConfig(builder -> {
                    builder.addInclude("maven_gav") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .controllerBuilder()
                            .enableRestStyle()
                            .entityBuilder()
                            .enableLombok()
                            .idType(IdType.AUTO)
                    ; // 设置过滤表前缀

                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
