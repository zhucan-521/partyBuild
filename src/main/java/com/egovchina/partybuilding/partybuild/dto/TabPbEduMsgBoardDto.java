package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduMsgBoard;
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
