package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbMsgUpInfoDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgUpInfo;
import com.egovchina.partybuilding.partybuild.entity.MsgUpInfoQueryBean;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoDTO;
import com.egovchina.partybuilding.partybuild.vo.MsgUpInfoVO;
import com.github.pagehelper.PageInfo;

public interface MsgUpInfoSerivce {
    /**
     * 添加信息传送扩展类
     * @param dto
     * @return
     */
   int insertMsgUpInfo(MsgUpInfoDTO dto);

    /**
     * 返回登录人的姓名，组织名称，上级组织名称，上级组织专干人姓名
     * @return TabPbMsgUpInfoDto
     */
    TabPbMsgUpInfo retrnUpMember(Long deptId);

    /**
     * 上报信息条件查询信息报送列表
     * @param dto
     * @return
     */
    PageInfo<MsgUpInfoVO> selectMsgUpInfoList(MsgUpInfoQueryBean dto, Page page);


    /**
     * 收到信息条件查询信息报送列表
     * @param dto
     * @return
     */
    PageInfo<MsgUpInfoVO> selectReceiveMsgUpInfoList(MsgUpInfoQueryBean dto, Page page);

    /**
     * 修改
     * @param
     * @return
     */
    int editMsgUpInfo(MsgUpInfoDTO tabPbMsgUpInfoDto);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int deleteMsgUpInfo(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    MsgUpInfoVO getMsgUpInfoById(Long id);

    /**
     * 审核结果
     * @param
     * @return
     */
    int auditMsgUpInfo(MsgUpInfoDTO msgUpInfoDto);
}
