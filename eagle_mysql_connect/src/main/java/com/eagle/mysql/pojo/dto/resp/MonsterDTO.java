package com.eagle.mysql.pojo.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.eagle.mysql.pojo.entity.Monster;
import com.eagle.mysql.pojo.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author eagle
 */
@Data
public class MonsterDTO {

    @ApiModelProperty("郵箱地址")
    private String name;

    @TableField("gender")
    @ApiModelProperty("男1女0")
    private GenderEnum gender;

    @ApiModelProperty("郵箱地址")
    private String email;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("工资保留2位")
    private BigDecimal salary;

    public static MonsterDTO from(Monster monster){
        MonsterDTO dto = new MonsterDTO();
        BeanUtils.copyProperties(monster, dto);
        return dto;
    }
}
