package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("导入错误文件dto")
public class ImportErrorFileDTO {

    @ApiModelProperty(value = "业务类型 （1党员导入）")
    private Byte businessType;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "文件访问路径")
    private String fileAccessUrl;

}
