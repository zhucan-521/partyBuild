package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 领导班子VO
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子VO")
@Data
public class LeadTeamVO {

    @ApiModelProperty("领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("组织id")
    private Long orgId;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("届数")
    private Long sessionYear;

    @ApiModelProperty(value = "当选时间", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty("任期年限")
    private Long duringYear;

    @ApiModelProperty(value = "本届期满日期", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expirationDate;

    @ApiModelProperty("选举方式 dict XJFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long voteType;

    @ApiModelProperty("应到人数")
    private Long dueCount;

    @ApiModelProperty("实到人数")
    private Long factCount;

    @ApiModelProperty("总票数")
    private Long voteCount;

    @ApiModelProperty("有效票数")
    private Long validVoteCount;

    @ApiModelProperty("当届标识")
    private Byte current;

    @ApiModelProperty("当选方式 dict YHZSFDX ")
    @JsonSerialize(using = DictSerializer.class)
    private Long electedType;

    @ApiModelProperty(value = "当选时间", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date electedTime;

    @ApiModelProperty("委员人数")
    private Long commMemNum;

    @ApiModelProperty("委员兼职人数")
    private Long commNumParttime;

    @ApiModelProperty("兼职委员人数")
    private Long parttimeCommNum;

    @ApiModelProperty("常务委员人数")
    private Long standingMemNum;

    @ApiModelProperty("候补委员人数")
    private Long backMemNum;

    @ApiModelProperty("数据描述")
    private String description;

    @ApiModelProperty("选举情况")
    private String voteStatus;

    @ApiModelProperty("选举结果")
    private String voteResult;

    @ApiModelProperty(value = "班子成员数")
    private Integer classNum;

    @ApiModelProperty(value = "图片附件数量")
    private Integer imgNum;

    @ApiModelProperty(value = "文档附件数量")
    private Integer docNum;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "单位类别 码表值 DWLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitProperty;

    @ApiModelProperty(value = "班子届数")
    private String classSession;

}
