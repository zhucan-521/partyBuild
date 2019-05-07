package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberIdsDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyGroupQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupVO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberBaseVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * desc: 党小组-服务接口
 * Created by FanYanGen on 2019/4/28 10:34
 */
public interface PartyGroupService {

    /**
     * desc: 添加党组
     *
     * @param partyGroupDTO 党小组信息dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/28 10:49
     **/
    int insertPartyGroup(PartyGroupDTO partyGroupDTO);

    /**
     * desc: 添加成员到党组
     *
     * @param partyGroupMemberDTO 党小组成员信息dto集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/29 15:07
     **/
    int insertMemberToPartyGroup(PartyGroupMemberDTO partyGroupMemberDTO);

    /**
     * desc: 更新党组
     *
     * @param partyGroupDTO 党小组信息dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/28 10:49
     **/
    int updatePartyGroup(PartyGroupDTO partyGroupDTO);

    /**
     * desc: 删除党组
     *
     * @param groupId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/28 10:49
     **/
    int deletePartyGroup(Long groupId);

    /**
     * desc: 移除党组成员
     *
     * @param partyGroupMemberIdsDTOList 成员ID集合
     * @return int
     * @author FanYanGen
     * @date 2019/5/6 9:23
     **/
    int removePartyGroupMembers(List<PartyGroupMemberIdsDTO> partyGroupMemberIdsDTOList);

    /**
     * desc: 撤销党组
     *
     * @param groupId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/5/5 14:20
     **/
    int revokePartyGroup(Long groupId);

    /**
     * desc: 恢复党组
     *
     * @param groupId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/5/5 19:35
     **/
    int recoveryPartyGroup(Long groupId);

    /**
     * desc: 筛选指定组织中未在任何党组存在的党员
     *
     * @param deptId 组织ID
     * @param page   分页
     * @return PartyMemberBaseVO
     * @author FanYanGen
     * @date 2019/5/6 15:55
     **/
    PageInfo<PartyMemberBaseVO>  screenPartyGroupMembers(Long deptId, Page page);

    /**
     * desc: 根据主键ID查询党组详情
     *
     * @param groupId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/28 10:49
     **/
    PartyGroupVO getPartyGroupDetails(Long groupId);

    /**
     * desc: 根据条件分页查询党组信息
     *
     * @param partyGroupQueryBean 查询条件实体
     * @param page                分页实体
     * @return int
     * @author FanYanGen
     * @date 2019/4/28 10:49
     **/
    PageInfo<PartyGroupVO> getPartyGroupList(PartyGroupQueryBean partyGroupQueryBean, Page page);

}
