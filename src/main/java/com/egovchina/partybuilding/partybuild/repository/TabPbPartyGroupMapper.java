package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyGroupQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroup;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyGroupMapper {

    int deleteByPrimaryKey(Long groupId);

    int insert(TabPbPartyGroup record);

    int insertSelective(TabPbPartyGroup record);

    TabPbPartyGroup selectByPrimaryKey(Long groupId);

    int updateByPrimaryKeySelective(TabPbPartyGroup record);

    int updateByPrimaryKeyWithBLOBs(TabPbPartyGroup record);

    int updateByPrimaryKey(TabPbPartyGroup record);

    /**
     * desc: 根据主键查询该条数据是否存在
     *
     * @param groupId 主键ID
     * @return boolean
     * @author FanYanGen
     * @date 2019/4/29 14:42
     **/
    boolean checkIsExistByGroupId(Long groupId);

    /**
     * desc: 根据党小组名称该条数据是否存在
     *
     * @param groupName 党组名称
     * @return boolean
     * @auther FANYANGEN
     * @date 2019-05-10 09:35
     */
    boolean checkIsExistByGroupName(String groupName);

    /**
     * desc: 根据主键获取党小组详情
     *
     * @param groupId 主键ID
     * @return PartyGroupVO
     * @author FanYanGen
     * @date 2019/4/29 15:31
     **/
    PartyGroupVO selectPartyGroupDetailsByGroupId(Long groupId);

    /**
     * desc: 查询党小组列表
     *
     * @param partyGroupQueryBean 条件实体
     * @return List<PartyGroupVO>
     * @author FanYanGen
     * @date 2019/4/29 16:40
     **/
    List<PartyGroupVO> selectPartyGroupDetails(PartyGroupQueryBean partyGroupQueryBean);

}