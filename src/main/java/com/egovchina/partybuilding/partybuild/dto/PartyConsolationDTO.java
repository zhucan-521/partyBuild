package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 慰问党员实体
 *
 * @author zhucan
 */
@Data
@ApiModel("慰问党员实体")
public class PartyConsolationDTO {

    @ApiModelProperty(value = "主键")
    @NotNull(groups = Update.class,message = "主键id不能为空")
    private Long id;

    @ApiModelProperty(value = "被慰问的人 user_id")
    @NotNull(message = "慰问的人userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "慰问时间 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date consolationTime;

    @ApiModelProperty(value = "慰问情况")
    private String consolationInfo;

}
