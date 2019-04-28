package com.egovchina.partybuilding.partybuild.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: huang
 * Date: 2018/12/13
 */
@ApiModel(value = "党籍列表信息")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistryDTO {

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他")
    private Long registryStatus;

    @ApiModelProperty(value = "办理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date handleTime;

    @ApiModelProperty(value = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operatTime;

    @ApiModelProperty(value = "操作人")
    private String operat;
}
