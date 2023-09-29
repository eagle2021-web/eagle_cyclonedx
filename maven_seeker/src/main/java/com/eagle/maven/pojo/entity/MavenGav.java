package com.eagle.maven.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("maven_gav")
@ApiModel(value = "MavenGav对象", description = "")
public class MavenGav implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("组织")
    private String groupId;

    @ApiModelProperty("名称")
    private String artifactId;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("记录更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已删除；0未删除，1已删除")
    private Boolean isDeleted;
}
