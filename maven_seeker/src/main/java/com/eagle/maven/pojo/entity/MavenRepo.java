package com.eagle.maven.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author eagle
 * @since 2023-09-29
 */
@Getter
@Setter
@TableName("maven_repo")
@ApiModel(value = "MavenRepo对象", description = "")
public class MavenRepo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("relative_path")
    @ApiModelProperty("相对路径")
    private String relativePath;

    @ApiModelProperty("完整的html text信息")
    private String text;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("记录更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已删除；0未删除，1已删除")
    private Boolean isDeleted;
}
