package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文件处理返回VO")
public class FileUploadResultVO {

    @ApiModelProperty(value = "是否成功")
    private boolean complete;

    @ApiModelProperty(value = "文件访问地址")
    private String errorFileAccessUrl;
}
