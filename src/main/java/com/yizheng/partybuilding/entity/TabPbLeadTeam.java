package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("领导班子")
public class TabPbLeadTeam {

    @ApiModelProperty(hidden = true)
    private static final String format = "yyyy-MM-dd";

    @ApiModelProperty("领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("党组织主键")
    private Long orgId;

    @ApiModelProperty("届数")
    private Long sessionYear;

    @ApiModelProperty(value = "换届时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = format)
    @JsonFormat(pattern = format)
    private Date changeDate;

    @ApiModelProperty("任期年限")
    private Long duringYear;

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

    @ApiModelProperty("当选方式")
    private Long electedType;

    @ApiModelProperty(value = "当选时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = format)
    @JsonFormat(pattern = format)
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

    @ApiModelProperty(value = "附件实体")
    private List<TabPbAttachment> tabPbAttachments;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

    @ApiModelProperty("组织名称")
    private String deptName;


}