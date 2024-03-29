package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.dto.LinkLeaderDTO;
import com.egovchina.partybuilding.partybuild.entity.LinkLeaderQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLinkLeader;
import com.egovchina.partybuilding.partybuild.feign.LifeServiceFeignClient;
import com.egovchina.partybuilding.partybuild.repository.TabPbLinkLeaderMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.JointPointInfoService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetAndCopyProperties;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

/**
 * 描述:
 * 联点信息实现类
 *
 * @author wuyunjie
 * Date 2019-04-20 10:38
 */
@Service
public class JointPointInfoServiceImpl implements JointPointInfoService {

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbLinkLeaderMapper tabPbLinkLeaderMapper;

    @Autowired
    private LifeServiceFeignClient lifeServiceFeignClient;

    /**
     * 根据userId查询组织id、职务，联点信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserDeptPositionVO selectJointByUserId(Long userId) {
        return tabSysUserMapper.selectUserDeptPositionVOByUserId(userId);
    }

    /**
     * 查询联点领导信息
     *
     * @param linkLeaderQueryBean 查询实体
     * @param page                分页
     * @return List<LinkLeaderVO>
     */
    @Override
    public List<LinkLeaderVO> selectUserDeptByDeptId(LinkLeaderQueryBean linkLeaderQueryBean, Page page) {
        if (tabSysDeptMapper.selectByPrimaryKey(linkLeaderQueryBean.getRangeDeptId()) != null) {
            PageHelper.startPage(page);
            return tabPbLinkLeaderMapper.selectLinkLeaderVoByDeptId(linkLeaderQueryBean);
        }
        throw new BusinessDataInvalidException("组织不存在");
    }

    /**
     * 根据联点领导id删除联点信息
     *
     * @param linkLedaerId
     * @return
     */
    @Transactional
    @Override
    public int delJointPointInfoByLinkLedaerId(Long linkLedaerId) {
        TabPbLinkLeader dbTabPbLinkLeader = tabPbLinkLeaderMapper.selectByPrimaryKey(linkLedaerId);
        if (ObjectUtils.isEmpty(dbTabPbLinkLeader)) {
            throw new BusinessDataInvalidException("联点领导不存在");
        }
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(dbTabPbLinkLeader.getUserId());
        //调用活动服务 修改活动信息
        ReturnEntity returnEntity =
                lifeServiceFeignClient.updateLianDianLeadership(
                        null, dbTabPbLinkLeader.getUserId(), sysUser.getRealname(), dbTabPbLinkLeader.getDeptId());
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        TabPbLinkLeader tabPbLinkLeader = new TabPbLinkLeader();
        tabPbLinkLeader.setLinkLedaerId(linkLedaerId);
        tabPbLinkLeader.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbLinkLeader);
        return tabPbLinkLeaderMapper.updateByPrimaryKeySelective(tabPbLinkLeader);
    }

    /**
     * 保存联点信息
     *
     * @param linkLeaderDTO
     * @return
     */
    @Transactional
    @Override
    public int saveJointPointInfo(LinkLeaderDTO linkLeaderDTO) {
        linkLeaderDTO.setLinkLedaerId(null);
        if (!tabSysDeptMapper.checkIsExistByOrgId(linkLeaderDTO.getDeptId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!tabSysUserMapper.checkIsExistByUserId(linkLeaderDTO.getUserId())) {
            throw new BusinessDataCheckFailException("该用户不存在");
        }
        //调用活动服务 修改活动信息
        ReturnEntity returnEntity =
                lifeServiceFeignClient.updateLianDianLeadership(
                        linkLeaderDTO.getActivitiesId(), linkLeaderDTO.getUserId(), linkLeaderDTO.getRealName(), linkLeaderDTO.getDeptId());
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        TabPbLinkLeader tabPbLinkLeader =
                generateTargetAndCopyProperties(
                        linkLeaderDTO, TabPbLinkLeader.class);
        List<TabPbLinkLeader> tabPbLinkLeaders = tabPbLinkLeaderMapper.selectByUserIdAndDeptId(tabPbLinkLeader);
        int count = 0;
        if (tabPbLinkLeaders.size() == 0) {
            paddingBaseFiled(tabPbLinkLeader);
            count += tabPbLinkLeaderMapper.insertSelective(tabPbLinkLeader);
        } else {
            for (TabPbLinkLeader linkLeader : tabPbLinkLeaders) {
                paddingUpdateRelatedBaseFiled(tabPbLinkLeader);
                tabPbLinkLeader.setLinkLedaerId(linkLeader.getLinkLedaerId());
                count += tabPbLinkLeaderMapper.updateByPrimaryKeySelective(tabPbLinkLeader);
            }
        }
        return count;
    }

    @Override
    public List<LeadTeamMemberVO> getLeadTeamMembersByIdCardNoOrRealName(Long orgId, String idCardNo, String realName, Page page) {
        PageHelper.startPage(page);
        return tabPbLinkLeaderMapper.selectLeadTeamMembersByIdCardNoOrRealName(orgId, idCardNo, realName);
    }
}
