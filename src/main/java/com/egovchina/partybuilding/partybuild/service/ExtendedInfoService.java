package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SysUserVO;
import com.github.pagehelper.PageInfo;


public interface ExtendedInfoService {

    PageInfo<SysUserVO> selectPartyByIdCardNoOrUserName(String idCardNo, String username, Page page);

    Boolean restoreUser(Long userId);

    PartyMemberVO selectPartyMemberVOById(Long userId);

    Boolean updateByUserId(DeletePartyMemberDTO reduce);
}
