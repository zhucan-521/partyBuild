package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.partybuild.dto.TabPbMsgUpInfoDto;
import com.egovchina.partybuilding.partybuild.entity.MsgUpInfoQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgUpInfo;
import com.egovchina.partybuilding.partybuild.vo.MsgUpInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMsgUpInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbMsgUpInfo record);

    int insertSelective(TabPbMsgUpInfo record);

    TabPbMsgUpInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbMsgUpInfo record);

    int updateByPrimaryKeyWithBLOBs(TabPbMsgUpInfo record);

    int updateByPrimaryKey(TabPbMsgUpInfo record);

    /**
     * 根据组织Id获取专干党员
     * @param deptId
     * @return
     */
    SysUser selectBydeptId(Long deptId);

    /**
     * 上报条件查询信息报送列表
     * @return
     */
    List<TabPbMsgUpInfoDto> selectActive(TabPbMsgUpInfoDto dto);


    /**
     * 上报条件查询信息报送列表
     * @return
     */
    List<MsgUpInfoVO> selectVoActive(MsgUpInfoQueryBean dto);


    /**
     * 接收条件查询信息报送列表
     * @return
     */
    List<TabPbMsgUpInfoDto> selectActiveRec(TabPbMsgUpInfoDto dto);

    /**
     * 接收条件查询信息报送列表
     * @return
     */
    List<MsgUpInfoVO> selectActiveVoRec(MsgUpInfoQueryBean dto);


    /**
     * 根据id查询详情包含相关信息
     * @param id
     * @return
     */
    TabPbMsgUpInfoDto selectWithAboutById(Long id);
}