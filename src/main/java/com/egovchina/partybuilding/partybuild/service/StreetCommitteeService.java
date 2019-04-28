package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeDTO;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeQueryBean;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/04
 */
public interface StreetCommitteeService {

    /**
     * 保存或更新街道大公委及数据
     * 如果主键在数据库中存在, 则为更新, 否则为保存
     *
     * @param streetCommitteeDTO
     * @return
     */
    int saveStreetCommittee(StreetCommitteeDTO streetCommitteeDTO);

    /**
     * 添加街道大公委及大公委成员数据
     * @param streetCommitteeDTO
     * @param streetCommitteMemberDTOList
     */
    int addStreetCommittee(StreetCommitteeDTO streetCommitteeDTO, List<StreetCommitteeMemberDTO> streetCommitteMemberDTOList);

    /**
     * 通过街道大公委主键删除, 将同时删除用户
     *
     * @param id
     */
    int deleteStreetCommittee(Long id);

    /**
     * 通过主键id查询
     *
     * @param grantCommitteeId
     * @return
     */
    StreetCommitteeVO getStreetCommitteeById(Long grantCommitteeId);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    PageInfo<StreetCommitteeVO> getStreetCommitteeList(StreetCommitteeQueryBean streetCommitteeQueryBean, Page page);


    /**
     * 添加街道大公委成员数据, 其街道大公委id和userId必须存在
     *
     * @param streetCommitteeMemberDTO
     */
    int addStreetCommitteeMember(StreetCommitteeMemberDTO streetCommitteeMemberDTO);

    /**
     * 通过(街道大公委明细主键)获取数据, 即街道大公委明细表id
     *
     * @param grantCommitteeMemberId
     * @return
     */
    StreetCommitteeMemberVO getStreetCommitteeMemberById(Long grantCommitteeMemberId);

    /**
     * 通过街道大公委明细表主键删除明细, 删除成员
     *
     * @param grantCommitteeMemberId
     */
    int deleteStreetCommitteeMemberById(Long grantCommitteeMemberId);

    /**
     * 通过街道大公委获取其成员列表
     *
     * @param grantCommitteeId
     * @param page
     * @return
     */
    PageInfo<StreetCommitteeMemberVO> getStreetCommitteeMemberList(Long grantCommitteeId, String personName, String positiveName, Page page);

    /**
     * 批量保存大公委成员数据
     *
     * @param streetCommitteMemberDTOList
     * @return
     */
    int addStreetCommitteeMembers(List<StreetCommitteeMemberDTO> streetCommitteMemberDTOList);
}