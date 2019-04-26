package com.egovchina.partybuilding.partybuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.OrganizationUpgradeAndChangeDto;
import com.egovchina.partybuilding.partybuild.dto.OrganizationUpgradeDto;
import com.egovchina.partybuilding.partybuild.dto.Personnel;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.entity.SysDeptUpgradeTemp;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.repository.SysDeptUpgradeTempMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgnizeChangeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.OrganizationUpgradeService;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:
 * 党组织升级实现类
 *
 * @author wuyunjie
 * Date 2019-01-03 14:33
 */
@Service
public class OrganizationUpgradeServiceImpl implements OrganizationUpgradeService {
    @Autowired
    private TabSysUserMapper tabSysUserMapper;
    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;
    @Autowired
    private SysDeptUpgradeTempMapper sysDeptUpgradeTempMapper;
    @Autowired
    private TabSysDeptService tabSysDeptService;
    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 批量修改人员组织信息
     * @param organizationUpgradeDto
     * @return
     */
    @Override
    @Transactional
    public int batchDeptIdByUserId(OrganizationUpgradeDto organizationUpgradeDto) {
        Map<String, List<SysUser>> userListMap = organizationUpgradeDto.getUserListMap();
        Long deptId  = organizationUpgradeDto.getDeptId();
        int retVal = 0;
        Byte b = 1;
        SysDeptUpgradeTemp sysDeptUpgradeTemp = sysDeptUpgradeTempMapper.selectByDeptId(deptId);
        if(sysDeptUpgradeTemp!=null){
            orgDataVerification(sysDeptUpgradeTemp);//校验
            //修改组织信息
            SysDeptDto sysDeptDto = new SysDeptDto();
            sysDeptDto.setDeptId(sysDeptUpgradeTemp.getDeptId().intValue());
            sysDeptDto.setName(sysDeptUpgradeTemp.getUpgradeDeptName());
            sysDeptDto.setOrgShortName(sysDeptUpgradeTemp.getUpgradeShortName());
            sysDeptDto.setIsParent(b);
            sysDeptDto.setOrgnizeProperty(1001L);
            PaddingBaseFieldUtil.paddingBaseFiled(sysDeptDto);
            retVal += tabSysDeptMapper.updateByPrimaryKeySelective(sysDeptDto);
        }else {
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        List<Personnel> personnels = tabSysUserMapper.getAllUserByDeptId(sysDeptUpgradeTemp.getDeptId());
        ArrayList<Integer> longArrayList = new ArrayList<>();
        List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(sysDeptUpgradeTemp.getDeptBranchs(), SysDeptDto.class);
        sysDeptDtos.forEach(branchOrganization -> {
            SysDept parentDept = tabSysDeptService.selectAloneByPrimaryKey(branchOrganization.getParentId().longValue());
            if (parentDept == null) {
                throw new BusinessDataNotFoundException(branchOrganization.getName()+"上级组织不存在");
            }
            //组织单位如果和上级相同就去上级的单位信息
            if(branchOrganization.getUnitState() != null && branchOrganization.getUnitState() == 59139L){
                branchOrganization.setUnitName(parentDept.getUnitName());
                branchOrganization.setUnitId(parentDept.getUnitId());
            }
            PaddingBaseFieldUtil.paddingBaseFiled(branchOrganization);
            //新增支部组织
            tabSysDeptService.insertWithAbout(branchOrganization);
            //批量移动人员
            for(Map.Entry<String, List<SysUser>> vo : userListMap.entrySet()){
                if(branchOrganization.getName().equals(vo.getKey())){
                    //获取userId集合
                    List<Integer> userIds = userListMap.get(vo.getKey()).stream().map(SysUser::getUserId).collect(Collectors.toList());
                    if(userIds.size() == personnels.size()){
                        throw new BusinessDataIncompleteException("不能把所有党员移入一个组织");
                    }
                    longArrayList.addAll(userIds);
                    if(userIds.size() > 0){
                        tabSysUserMapper.batchDeptIdByUserId(
                                userIds,branchOrganization.getDeptId().longValue());
                    }
                }
            }
        });
        if(longArrayList.size() < personnels.size()){
            throw new BusinessDataIncompleteException("请分配升级组织下所有的人员");
        }
        OrganizationUpgradeAndChangeDto organizationUpgradeAndChangeDto =
                sysDeptUpgradeTempMapper.selectByDeptId(deptId);
        retVal += addOrModifyAdjustmentsAndAttachments(organizationUpgradeAndChangeDto);
        return retVal;
    }

    /**
     * 数据校验
     * @param sysDeptUpgradeTemp
     */
    private void orgDataVerification(SysDeptUpgradeTemp sysDeptUpgradeTemp) {
        String deptName = sysDeptUpgradeTemp.getUpgradeDeptName();
        if (StringUtils.isEmpty(deptName)) {
            throw new BusinessDataIncompleteException("组织名称不能为空");
        }
        if (tabSysDeptService.checkOrgNameAvailability(deptName)) {
            throw new BusinessDataInvalidException(deptName+"组织名称重复");
        }
        //支部组织数据校验
        if(StringUtil.isNotEmpty(sysDeptUpgradeTemp.getDeptBranchs())){
            List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(sysDeptUpgradeTemp.getDeptBranchs(), SysDeptDto.class);
            sysDeptDtos.forEach(sysDeptDto -> {
                if (StringUtils.isEmpty(sysDeptDto.getName())) {
                    throw new BusinessDataIncompleteException("支部组织名称不能为空");
                }
                if(sysDeptDto.getParentId() == null){
                    throw new BusinessDataIncompleteException(sysDeptDto.getName()+"上级组织不存在");
                }
                if (sysDeptDto.getDeptId() == null && tabSysDeptService.checkOrgNameAvailability(sysDeptDto.getName())) {
                    throw new BusinessDataInvalidException(sysDeptDto.getName()+"组织名称重复");
                }
                List<TabPbUnitInfo> tabPbUnitInfos = sysDeptDto.getTabPbUnitInfos();
                if (CollectionUtil.isNotEmpty(tabPbUnitInfos)) {
                    tabPbUnitInfos.forEach(tabPbUnitInfo -> {
                        if (StringUtils.isEmpty(tabPbUnitInfo.getUnitName())) {
                            throw new BusinessDataIncompleteException(sysDeptDto.getName()+"支部单位名称不能为空");
                        }
                    });
                }
            });
        }
    }


    /**
     * 查询组织下的直属党员
     *
     * @param deptId
     * @return
     */
    @Override
    public List<Personnel> getAllUserByDeptId(Long deptId) {
        if (tabSysDeptMapper.selectByPrimaryKey(deptId) == null) {
            throw new BusinessDataNotFoundException("组织不存在！");
        }
        List<Personnel> list = tabSysUserMapper.getAllUserByDeptId(deptId);
        return list;
    }

    @Override
    @Transactional
    @PaddingBaseField(recursive = true)
    public Integer insertSelective(OrganizationUpgradeAndChangeDto organizationUpgradeAndChangeDto) {
        int retVal = 0;
        if(!tabSysDeptService.checkOrgIsExists(organizationUpgradeAndChangeDto.getDeptId())){
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        organizationUpgradeAndChangeDto.setDeptName(tabSysDeptService.selectByPrimaryKey(organizationUpgradeAndChangeDto.getDeptId()).getName());
        orgDataVerification(organizationUpgradeAndChangeDto);
        if(sysDeptUpgradeTempMapper.selectByDeptId(organizationUpgradeAndChangeDto.getDeptId())!= null){
            SysDeptUpgradeTemp oldUpgradedOrganization = sysDeptUpgradeTempMapper.selectByDeptId(organizationUpgradeAndChangeDto.getDeptId());
            organizationUpgradeAndChangeDto.setId(oldUpgradedOrganization.getId());
            organizationUpgradeAndChangeDto.setDeptBranchs(oldUpgradedOrganization.getDeptBranchs());
            retVal += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(organizationUpgradeAndChangeDto);
        }else {
            retVal += sysDeptUpgradeTempMapper.insertSelective(organizationUpgradeAndChangeDto);
        }
        retVal += addOrModifyAdjustmentsAndAttachments(organizationUpgradeAndChangeDto);
        return retVal;
    }

    /**
     * 新增或修改组织调整和附件信息
     * @param organizationUpgradeAndChangeDto
     * @return
     */
    @Transactional
    public Integer addOrModifyAdjustmentsAndAttachments(OrganizationUpgradeAndChangeDto organizationUpgradeAndChangeDto){
        int retVal = 0;
        iTabPbAttachmentService.intelligentOperation(
                organizationUpgradeAndChangeDto.getAttachments(),
                organizationUpgradeAndChangeDto.getId(), AttachmentType.ORG_UPGRADE);
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        tabPbOrgnizeChange.setChangeType(59424L);
        tabPbOrgnizeChange.setDeptId(organizationUpgradeAndChangeDto.getDeptId());
        tabPbOrgnizeChange.setFileNumber(organizationUpgradeAndChangeDto.getFileNumber());
        tabPbOrgnizeChange.setChangeReason(organizationUpgradeAndChangeDto.getChangeReason());
        tabPbOrgnizeChange.setChangeDate(organizationUpgradeAndChangeDto.getChangeDate());
        tabPbOrgnizeChange.setOrgnizeName(organizationUpgradeAndChangeDto.getDeptName());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbOrgnizeChange);
        TabPbOrgnizeChange pbOrgnizeChange = tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(
                organizationUpgradeAndChangeDto.getDeptId(), 59424L);
        if(pbOrgnizeChange != null){
            tabPbOrgnizeChange.setChangeId(pbOrgnizeChange.getChangeId());
            retVal += tabPbOrgnizeChangeMapper.updateByPrimaryKeySelective(tabPbOrgnizeChange);
        }else {
            retVal += tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
        }
        return retVal;
    }

    @Override
    @PaddingBaseField
    public Integer updateByPrimaryKeySelective(SysDeptUpgradeTemp sysDeptUpgradeTemp) {
        int retVal = 0;
        List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(sysDeptUpgradeTemp.getDeptBranchs(), SysDeptDto.class);
        if(sysDeptDtos.size() < 2){
            throw new BusinessDataIncompleteException("请添加两个党支部");
        }
        sysDeptDtos.forEach(branchOrganization -> {
            if(branchOrganization.getOrgnizeProperty() == null){
                throw new BusinessDataIncompleteException("请选择组织类别");
            }
                });
        orgDataVerification(sysDeptUpgradeTemp);
        sysDeptUpgradeTemp.setId(sysDeptUpgradeTempMapper.selectByDeptId(sysDeptUpgradeTemp.getDeptId()).getId());
        retVal += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        return retVal;
    }

    @Override
    public OrganizationUpgradeAndChangeDto selectByDeptId(Long deptId) {
        return sysDeptUpgradeTempMapper.selectByDeptId(deptId);
    }
}
