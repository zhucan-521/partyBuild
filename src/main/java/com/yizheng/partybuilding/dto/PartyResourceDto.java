package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/17
 */
@Data
@Accessors(chain = true)
@ApiModel("资源展示dto")
public class PartyResourceDto {

    @ApiModelProperty(value = "附件Id")
    private Long attachmentId;
    @ApiModelProperty(value = "附件HsotId")
    private Long hostId;
    @ApiModelProperty(value = "附件文件Id")
    private String attachmentInstance;
    @ApiModelProperty(value = "附件类型-数据字典")
    private Long attachmentType;
    @ApiModelProperty(value = "文件类型-数据字典")
    private Long attachmentFileType;


    @ApiModelProperty(value = "文件大小")
    private String description;
    @ApiModelProperty(value = "文件名")
    private String attachmentDescription;


    @ApiModelProperty(value = "创建时间,或修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;


    @ApiModelProperty(value = "创建人id")
    private Long createUserid;

    @ApiModelProperty(value = "创建人name")
    private String createUsername;
}
