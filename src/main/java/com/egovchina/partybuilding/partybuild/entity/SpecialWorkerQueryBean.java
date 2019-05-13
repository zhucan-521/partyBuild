package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 * 专干查询实体
 *
 * @author wuyunjie
 * Date 2019-04-23 10:37
 */
@Data
@ApiModel("专干查询实体")
public class SpecialWorkerQueryBean {
    @ApiModelProperty(value = "专干党员主键")
    private Long specialWorkerId;

    @ApiModelProperty(value = "组织主键")
    private Long rangeDeptId;

    @ApiModelProperty(value = "专干姓名")
    private String username;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "在职状态")
    private Long status;

    @ApiModelProperty(value = "单位类别")
    private String unitProperty;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<String> unitProperties;
}
