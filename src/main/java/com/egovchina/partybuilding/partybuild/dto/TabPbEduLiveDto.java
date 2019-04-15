package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLive;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 * 直播实体dto
 *
 * @outhor asd
 * @create 2018-12-10 14:54
 */
@ApiModel(value = "直播实体dto",description = "直播实体dto")
@Data
public class TabPbEduLiveDto extends TabPbEduLive {
    @ApiModelProperty(value = "录播资源")
    private List<TabPbAttachment> recordList;
    @ApiModelProperty(value = "点赞数")
    private Long sumClick;
    @ApiModelProperty(value = "预约数")
    private Long sumSubscribe;
    @ApiModelProperty(value = "留言评论数")
    private Long sumMsg;
    @ApiModelProperty(value = "直播老师名称")
    private String teacherName;
}
