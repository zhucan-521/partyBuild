package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbEduSubject;
import com.yizheng.partybuilding.entity.TabPbEduTest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 创建试卷dto
 */
@ApiModel(value = "创建试卷实体")
@Data
public class TabPbEduTestAddDto extends TabPbEduTest {

    @ApiModelProperty("题目集合，选题组卷时传入值，随机组卷时不传，只传题目id即可，其他字段忽略")
    List<TabPbEduSubject> danxuanList;

    @ApiModelProperty(value = "单选题多少分")
    private int percent1;
    @ApiModelProperty(value = "单选题数量")
    private int count1;

    //现在只有单选题，下面是多选与填空题的参数
    //private int percent2;
    //private int count2;
    //private int percent3;
    //private int count3;
}
