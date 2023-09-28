package com.eagle.mysql.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.eagle.mysql.pojo.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 妖怪信息表
 * </p>
 *
 * @author eagle
 * @since 2023-09-28
 */
@Getter
@Setter
@ApiModel(value = "Monster对象", description = "妖怪信息表")
public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("年龄")
    private Integer age;

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
    @TableField("is_deleted")
    @ApiModelProperty("是否已删除：0未删除，1已删除")
    private Byte isDeleted;
}
