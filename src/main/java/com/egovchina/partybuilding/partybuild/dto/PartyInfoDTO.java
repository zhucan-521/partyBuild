package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;


/**
 * desc: 党员信息数据传输对象
 * Created by FanYanGen on 2019/4/22 09:18
 */
@ApiModel(value = "党员基本信息实体")
@Data
public class PartyInfoDTO {

    @ApiModelProperty(value = "党员基本信息")
    @Valid
    private PartyDTO party;

    @ApiModelProperty(value = "学历信息")
    @Valid
    private List<PartyEducationDTO> educations;

    @ApiModelProperty(value = "技术职务")
    @Valid
    private List<PartyJobTitleDTO> jobTitles;

    @ApiModelProperty(value = "工作信息")
    @Valid
    private List<PartyWorkDTO> works;


    public PartyInfoDTO(List<PartyEducationDTO> educationList, List<PartyJobTitleDTO> jobTitleList, List<PartyWorkDTO> workList) {
        this.educations = educationList;
        this.jobTitles = jobTitleList;
        this.works = workList;
    }

}
