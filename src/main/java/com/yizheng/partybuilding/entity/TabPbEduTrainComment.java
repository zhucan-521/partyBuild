package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 */

@Data
@ApiModel(value = "实体")
@Accessors(chain = true)
public class TabPbEduTrainComment {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "培训人员id")
    private Long userId;

    @ApiModelProperty(value = "评论等级-最多五颗星")
    private Integer level;

    @ApiModelProperty(value = "评论结果：好、较好、一般、差")
    private String result;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}