package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc: 分类定等-数据传输对象
 * Created by FanYanGen on 2019/4/24 21:53
 */
@Data
@ApiModel("分类定等-数据传输对象")
public class OrgClassifyDTO {

    @ApiModelProperty(value = "主键ID")
    private Long orgClassifyId;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "定等级别 dict FLDD", required = true)
    @NotNull(message = "定等级别不能为空")
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd", required = true, dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "定等日期不能为空")
    private Date levelDate;

    @ApiModelProperty(value = "定等描述", required = true)
    @NotBlank(message = "定等描述不能为空")
    private String description;

}
