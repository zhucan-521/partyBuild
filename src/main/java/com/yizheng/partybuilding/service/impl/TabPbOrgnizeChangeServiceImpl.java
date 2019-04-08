package com.yizheng.partybuilding.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.exception.BusinessDataInvalidException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbLinkLeaderDto;
import com.yizheng.partybuilding.dto.UserDeptPositiveDto;
import com.yizheng.partybuilding.entity.TabPbActivities;
import com.yizheng.partybuilding.entity.TabPbLinkLeader;
import com.yizheng.partybuilding.entity.TabPbOrgnizeChange;
import com.yizheng.partybuilding.entity.TabPbParticipant;

import com.yizheng.partybuilding.repository.*;
import com.yizheng.partybuilding.service.inf.ITabSysDictService;
import com.yizheng.partybuilding.service.inf.TabPbOrgnizeChangeService;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;

import com.yizheng.partybuilding.system.entity.SysDept;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.yizheng.commons.util.UserContextHolder.currentUser;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 描述:
 * 组织调整实现类
 *
 * @outhor wuyunjie
 * @create 2018-12-02 10:40
 */
@Service("tabPbOrgnizeChangeService")
public class TabPbOrgnizeChangeServiceImpl implements TabPbOrgnizeChangeService {

    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Autowired
    private ITabSysDictService dictService;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;
    @Autowired
    private TabSysDeptService tabSysDeptService;

    @Autowired
    private TabPbLinkLeaderMapper tabPbLinkLeaderMapper;

    @Autowired
    private TabPbParticipantMapper tabPbParticipantMapper;

    @Autowired
    private TabPbActivitiesMapper tabPbActivitiesMapper;

    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;

    @Override
    public TabPbOrgnizeChange selectOrgChangeByDeptIdOrderTime(Long deptId, Long changeType) {
        return tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(deptId, changeType);
    }

    /**
     * 添加组织调整记录
     *
     * @param tabPbOrgnizeChange
     * @return
     */
    @Transactional
    @Override
    public void insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(tabPbOrgnizeChange.getDeptId().intValue());
        SysDept superiorOrganization = deptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getNowSuperiorId());
        if (superiorOrganization != null) {
            sysDept.setParentId(tabPbOrgnizeChange.getNowSuperiorId().intValue());
        } else {
            throw new BusinessDataCheckFailException("上级组织不存在");
        }
        int a = deptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId()).getParentId();
        if (tabPbOrgnizeChange.getNowSuperiorId() == a) {
            throw new BusinessDataCheckFailException("上级组织未改变");
        }
        SysDept sysDept1 = deptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId());
        if(sysDept1 != null ){
            if(sysDept1.getUnitState() != null && sysDept1.getUnitState() == 59139L){
                sysDept.setUnitId(superiorOrganization.getUnitId());
                sysDept.setUnitName(superiorOrganization.getUnitName());
            }
        }
        sysDept.setOrgCode(tabPbOrgnizeChange.getOrgnizeCode());
        sysDept.setFoundedFileNumber(tabPbOrgnizeChange.getFileNumber());
        tabSysDeptService.modifyFullPathAndSubDeptIfNecessary(sysDept);
        deptMapper.updateByPrimaryKeySelective(sysDept);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange) {
        return tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
    }

    /**
     * 组织变动 (分为组织更名 ZZGM, 组织撤销 ZZCX, 组织恢复 ZZHF)
     *
     * @param org
     * @return
     */
    @Transactional
    @Override
    @PaddingBaseField
    public String changeOrg(TabPbOrgnizeChange org) {
        // 我改不了, 只能转int了
        if(Objects.requireNonNull(currentUser()).getDeptId() != null ){
            if (org.getDeptId() == Objects.requireNonNull(currentUser()).getDeptId().longValue()) {
                throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
            }
        }else {
            if (org.getDeptId() == Objects.requireNonNull(currentUser()).getManageDeptId().longValue()) {
                throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
            }
        }

        var dict = this.dictService.selectById(org.getChangeType().intValue());
        if (dict == null) {
            throw new BusinessDataInvalidException("数据字典不存在");
        }

        if (deptMapper.selectByPrimaryKey(org.getDeptId()) == null) {
            throw new BusinessDataInvalidException("组织不存在");
        }


        if (dict.getValue().equals("ZZGM")) {
            this.ZZGM(org);
        } else if (dict.getValue().equals("ZZCX")) {
            this.orgChange(org, "0", 59123L);
        } else if (dict.getValue().equals("ZZHF")) {
            this.orgChange(org, "1", 59122L);
        } else if (dict.getValue().equals("ZZTZ")) {
            this.insertOrgChange(org);
        }
        this.tabPbOrgnizeChangeMapper.insertSelective(org);
        return dict.getLabel() + "成功";
    }


    /**
     * 组织更名
     *
     * @param org
     * @return
     */
    private String ZZGM(TabPbOrgnizeChange org) {

        if (isEmpty(org.getOrgnizeName())) {
            throw new BusinessDataIncompleteException("组织名称不可为null或空白");
        }

        if (isEmpty(org.getShortName())) {
            throw new BusinessDataIncompleteException("组织简称不可为null或空白");
        }

        if (this.deptMapper.checkNameOrShortName(org.getOrgnizeName()) > 0) {
            throw new BusinessDataInvalidException("组织名称已存在");
        }

        if (this.deptMapper.checkNameOrShortName(org.getShortName()) > 0) {
            throw new BusinessDataInvalidException("组织简称已存在");
        }

        var newDept = new SysDept();
        newDept.setDeptId(org.getDeptId().intValue());
        newDept.setName(org.getOrgnizeName());
        newDept.setOrgShortName(org.getShortName());
        int count = this.deptMapper.updateByPrimaryKeySelective(newDept);
        return "修改成功:" + count;
    }


    /**
     * 组织恢复/撤销
     *
     * @param org 变更信息
     * @return
     */
    private void orgChange(TabPbOrgnizeChange org, String flag, Long orgStatus) {
        var newDept = new SysDept();
        newDept.setDeptId(org.getDeptId().intValue());
        newDept.setEblFlag(flag);
        newDept.setOrgStatus(orgStatus);
        this.deptMapper.updateByPrimaryKeySelective(newDept);
    }

    /**
     * 根据userId查询组织id、职务，联点信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserDeptPositiveDto selectJointByUserId(Long userId) {
            return tabSysUserMapper.selectUserDeptPositiveByUserId(userId);
    }

    /**
     * 根据组织id 获得该组织连接领导列表
     *
     * @param deptId
     * @return
     */
    @Override
    public List<TabPbLinkLeaderDto> selectUserDeptByDeptId(Long deptId) {
        if (deptMapper.selectByPrimaryKey(deptId) != null) {
            List<TabPbLinkLeaderDto> tabPbLinkLeaderDtoList = tabPbLinkLeaderMapper.selectTabPbLinkLeaderDtoByDeptId(deptId);
            return tabPbLinkLeaderDtoList;
        } else {
            throw new BusinessDataInvalidException("组织不存在");
        }
    }


    /**
     * 保存联点信息
     *
     * @param tabPbLinkLeaderDto
     * @return
     */
    @Override
    @Transactional
    public int saveJoint(TabPbLinkLeaderDto tabPbLinkLeaderDto) {
        int taiVal = 0;
        Byte b = 1;
        if (deptMapper.selectByPrimaryKey(tabPbLinkLeaderDto.getDeptId()) != null
                && tabSysUserMapper.selectByPrimaryKey(tabPbLinkLeaderDto.getUserId()) != null) {
            TabPbLinkLeader record = new TabPbLinkLeader();
            record.setDeptId(tabPbLinkLeaderDto.getDeptId());
            record.setUserId(tabPbLinkLeaderDto.getUserId());
            BeanUtil.copyProperties(tabPbLinkLeaderDto, record);
            List<TabPbLinkLeader> tabPbLinkLeaders = tabPbLinkLeaderMapper.selectByUserIdAndDeptId(record);
            if (tabPbLinkLeaders.size() == 0) {
                taiVal += tabPbLinkLeaderMapper.insertSelective(tabPbLinkLeaderDto);
            } else {
                for (TabPbLinkLeader tabPbLinkLeader : tabPbLinkLeaders) {
                    record.setLinkLedaerId(tabPbLinkLeader.getLinkLedaerId());
                    taiVal += tabPbLinkLeaderMapper.updateByPrimaryKeySelective(record);
                }
            }
        } else {
            throw new BusinessDataInvalidException("组织或人员不存在");
        }
        if (tabPbLinkLeaderDto.getActivitiesList() != null) {
            List<TabPbActivities> activitiesList = tabPbLinkLeaderDto.getActivitiesList();
            TabPbParticipant pbParticipant = new TabPbParticipant();
            pbParticipant.setUserId(tabPbLinkLeaderDto.getUserId());
            pbParticipant.setIfLinkLeader(b);
            for (TabPbActivities activities : activitiesList) {
                if (tabPbActivitiesMapper.selectByActivitiesId(activities.getActivitiesId()) != null) {
                    activities.setInviteLinkLeader(b);
                    tabPbActivitiesMapper.updateByPrimaryKeySelective(activities);
                    pbParticipant.setActivitiesId(activities.getActivitiesId());
                    pbParticipant.setRealName(tabSysUserMapper.selectByPrimaryKey(tabPbLinkLeaderDto.getUserId()).getUsername());
                    if (tabPbParticipantMapper.selectCountByParticipant(pbParticipant) == 0) {
                        tabPbParticipantMapper.insertSelective(pbParticipant);
                    } else {
                        throw new BusinessDataCheckFailException(activities.getSubject() + "，此活动该联点领导已经存在");
                    }
                } else {
                    throw new BusinessDataNotFoundException("此活动不存在");
                }
            }
        }
        return taiVal;
    }

    @Override
    public PageInfo<TabPbOrgnizeChange> selectByChangeType(String changeType, Long orgId, Page page) {
        PageHelper.startPage(page);
        Map<String, Object> conditions = new HashMap<>();
        if(changeType != null){
            conditions.put("changeType",changeType.split(","));
        }
        conditions.put("deptId",orgId);
        var temp = this.tabPbOrgnizeChangeMapper.selectByChangeType(conditions);
        return new PageInfo<>(temp);
    }

    @Override
    public int delLeaderByLinkLedaerId(Long linkLedaerId) {
        TabPbLinkLeader tabPbLinkLeader = new TabPbLinkLeader();
        tabPbLinkLeader.setLinkLedaerId(linkLedaerId);
        tabPbLinkLeader.setDelFlag("1");
        return tabPbLinkLeaderMapper.updateByPrimaryKeySelective(tabPbLinkLeader);
    }

    @Override
    public List<TabPbOrgnizeChange> selectCombination(Long deptId,Page page){
        PageHelper.startPage(page);
        return tabPbOrgnizeChangeMapper.selectCombination(deptId);
    }
}
