package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @ApiModel(value = "组织阵地DTO实体")
 * @Author Zhang Fan
 **/
@ApiModel(value = "组织阵地DTO实体")
@Data
public class OrganizationPositionDTO {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织id不能为空")
    private Long deptId;

    @ApiModelProperty(value = "办公面积（平方米）", required = true)
    @NotNull(message = "办公面积不能为空")
    @Min(value = 1, message = "办公面积不能小于1平方米")
    private Long officeSize;

    @ApiModelProperty(value = "位置")
    private String address;

    @ApiModelProperty(value = "经纬度", required = true)
    @NotBlank(message = "经纬度不能为空")
    private String position;

    @ApiModelProperty(value = "建立日期 yyyy-MM-dd", required = true, example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "建立日期不能为空")
    private Date foundedDate;

    @ApiModelProperty(value = "布置照片")
    private List<TabPbAttachment> attachments;
}
