package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbMsgUpInfoDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgUpInfo;
import com.github.pagehelper.PageInfo;

public interface TabPbMsgUpInfoSerivce {
    /**
     * 添加信息传送扩展类
     * @param dto
     * @return
     */
   int insert(TabPbMsgUpInfoDto dto);

    /**
     * 返回登录人的姓名，组织名称，上级组织名称，上级组织专干人姓名
     * @return TabPbMsgUpInfoDto
     */
    TabPbMsgUpInfo selectAffter(Long deptId);

    /**
     * 上报信息条件查询信息报送列表
     * @param dto
     * @return
     */
    PageInfo<TabPbMsgUpInfoDto> selectActive(TabPbMsgUpInfoDto dto, Page page);


    /**
     * 收到信息条件查询信息报送列表
     * @param dto
     * @return
     */
    PageInfo<TabPbMsgUpInfoDto> selectActiveRec(TabPbMsgUpInfoDto dto, Page page);

    /**
     * 修改
     * @param
     * @return
     */
    int update(TabPbMsgUpInfoDto tabPbMsgUpInfoDto);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TabPbMsgUpInfoDto selectActiveOne(Long id);

    /**
     * 审核结果
     * @param
     * @return
     */
    int auditResult(TabPbMsgUpInfoDto tabPbMsgUpInfoDto);
}
