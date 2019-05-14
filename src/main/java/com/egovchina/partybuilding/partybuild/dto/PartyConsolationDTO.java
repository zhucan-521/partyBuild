package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhucan
 */
@Data
public class PartyConsolationDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "被慰问的人 user_id")
    @NotNull(message = "请传入被慰问的人userId")
    private Long userId;

    @ApiModelProperty(value = "慰问时间 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date consolationTime;

    @ApiModelProperty(value = "慰问情况")
    private String consolationInfo;

}
