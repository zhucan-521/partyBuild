package com.yizheng.partybuilding.dto;

import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbEduMsgBoard;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jiang An
 **/
@ApiModel("留言板Dto")
@Data
public class TabPbEduMsgBoardDto extends TabPbEduMsgBoard {

    @ApiModelProperty("附件集合")
    private List<TabPbAttachment> annexs;

   
}
