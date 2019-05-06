package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 工作计划QueryBean
 *
 * @author Zhang Fan
 **/
@ApiModel("工作计划QueryBean")
@Data
public class WorkPlanQueryBean {

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty("组织ID")
    private Long rangeDeptId;

    @ApiModelProperty("年度 yyyy")
    private String planYear;

    @ApiModelProperty("上报日期-开始 yyyy-MM-dd")
    private String reportStartDate;

    @ApiModelProperty("上报日期-结束 yyyy-MM-dd")
    private String reportEndDate;

    @ApiModelProperty("计划审核结果 dict SHJG")
    private Long checkResult;

    public Long getRangeDeptId() {
        if (this.rangeDeptId == null || this.rangeDeptId == 0) {
            return UserContextHolder.getOrgId();
        }
        return rangeDeptId;
    }
}
