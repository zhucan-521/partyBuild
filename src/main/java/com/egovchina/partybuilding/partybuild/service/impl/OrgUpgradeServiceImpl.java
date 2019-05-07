package com.egovchina.partybuilding.partybuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.SysDeptUpgradeTemp;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.repository.SysDeptUpgradeTempMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgnizeChangeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.OrgUpgradeService;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;
import com.egovchina.partybuilding.partybuild.vo.OrgUpgradeVO;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * 描述:
 * 党组织升级实现类
 *
 * @author wuyunjie
 * Date 2019-01-03 14:33
 */
@Service
public class OrgUpgradeServiceImpl implements OrgUpgradeService {
    @Autowired
    private TabSysUserMapper tabSysUserMapper;
    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;
    @Autowired
    private SysDeptUpgradeTempMapper sysDeptUpgradeTempMapper;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 批量修改人员组织信息
     *
     * @param orgUpgradedPersonnelTransferDTO
     * @return
     */
    @Override
    @Transactional
    public int batchDeptIdByUserId(OrgUpgradedPersonnelTransferDTO orgUpgradedPersonnelTransferDTO) {
        Map<String, List<DirectPartyMemberDTO>> directStaffListMap = orgUpgradedPersonnelTransferDTO.getDirectStaffListMap();
        Long deptId = orgUpgradedPersonnelTransferDTO.getDeptId();
        int count = 0;
        OrgUpgradeVO orgUpgradeVO = sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(deptId);
        OrgUpgradeDTO orgUpgradeDto = new OrgUpgradeDTO();
        if (orgUpgradeVO != null) {
            BeanUtil.copyPropertiesIgnoreNull(orgUpgradeVO, orgUpgradeDto);
            orgDataVerification(orgUpgradeDto);//校验
            //修改组织信息
            SysDept sysDept = new SysDept();
            sysDept.setDeptId(orgUpgradeDto.getDeptId());
            sysDept.setName(orgUpgradeDto.getUpgradeDeptName());
            sysDept.setOrgShortName(orgUpgradeDto.getUpgradeShortName());
            sysDept.setIsParent((byte) 1);
            sysDept.setOrgnizeProperty(1001L);
            PaddingBaseFieldUtil.paddingBaseFiled(sysDept);
            count += tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
        } else {
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        List<DirectPartyMemberVO> directPartyMemberVOS =
                tabSysUserMapper.selectDirectPartyMemberVOByDeptId(orgUpgradeDto.getDeptId());
        ArrayList<Integer> longArrayList = new ArrayList<>();
        List<OrganizationDTO> organizationDTOS = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), OrganizationDTO.class);
        organizationDTOS.forEach(branchOrganization -> {
            SysDept parentDept = organizationService.selectAloneByPrimaryKey(branchOrganization.getParentId());
            if (parentDept == null) {
                throw new BusinessDataNotFoundException(branchOrganization.getName() + "上级组织不存在");
            }
            //组织单位如果和上级相同就去上级的单位信息
            if (branchOrganization.getUnitState() != null && branchOrganization.getUnitState() == 59139L) {
                branchOrganization.setUnitName(parentDept.getUnitName());
                branchOrganization.setUnitId(parentDept.getUnitId());
            }
            PaddingBaseFieldUtil.paddingBaseFiled(branchOrganization);
            //新增支部组织
            organizationService.insertOrganization(branchOrganization);
            //批量移动人员
            for (Map.Entry<String, List<DirectPartyMemberDTO>> vo : directStaffListMap.entrySet()) {
                if (branchOrganization.getName().equals(vo.getKey())) {
                    //获取userId集合
                    List<Integer> userIds = directStaffListMap.get(vo.getKey()).stream().map(DirectPartyMemberDTO::getUserId).collect(Collectors.toList());
                    if (userIds.size() == directPartyMemberVOS.size()) {
                        throw new BusinessDataIncompleteException("不能把所有党员移入一个组织");
                    }
                    longArrayList.addAll(userIds);
                    if (userIds.size() > 0) {
                        tabSysUserMapper.batchDeptIdByUserId(
                                userIds, branchOrganization.getDeptId());
                    }
                }
            }
        });
        if (longArrayList.size() < directPartyMemberVOS.size()) {
            throw new BusinessDataIncompleteException("请分配升级组织下所有的人员");
        }
        count += addOrModifyAdjustmentsAndAttachments(orgUpgradeDto);
        return count;
    }

    /**
     * 数据校验
     *
     * @param orgUpgradeDto
     */
    private void orgDataVerification(OrgUpgradeDTO orgUpgradeDto) {
        String deptName = orgUpgradeDto.getUpgradeDeptName();
        Long deptId = orgUpgradeDto.getDeptId();
        if (organizationService.checkOrgNameAvailability(deptName, deptId)) {
            throw new BusinessDataInvalidException(deptName + "组织名称重复");
        }
        //支部组织数据校验
        if (StringUtil.isNotEmpty(orgUpgradeDto.getDeptBranchs())) {
            List<OrganizationDTO> organizationDTOS = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), OrganizationDTO.class);
            organizationDTOS.forEach(sysDeptDto -> {
                if (StringUtils.isEmpty(sysDeptDto.getName())) {
                    throw new BusinessDataIncompleteException("支部组织名称不能为空");
                }
                if (sysDeptDto.getParentId() == null) {
                    throw new BusinessDataIncompleteException(sysDeptDto.getName() + "上级组织不存在");
                }
                if (sysDeptDto.getDeptId() == null && organizationService.checkOrgNameAvailability(sysDeptDto.getName(), sysDeptDto.getDeptId())) {
                    throw new BusinessDataInvalidException(sysDeptDto.getName() + "组织名称重复");
                }
                List<UnitInfoDTO> tabPbUnitInfos = sysDeptDto.getUnits();
                if (CollectionUtil.isNotEmpty(tabPbUnitInfos)) {
                    tabPbUnitInfos.forEach(tabPbUnitInfo -> {
                        if (StringUtils.isEmpty(tabPbUnitInfo.getUnitName())) {
                            throw new BusinessDataIncompleteException(sysDeptDto.getName() + "单位名称不能为空");
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
    public List<DirectPartyMemberVO> getDirectPartyMemberByDeptId(Long deptId) {
        if (tabSysDeptMapper.selectByPrimaryKey(deptId) == null) {
            throw new BusinessDataNotFoundException("组织不存在！");
        }
        return tabSysUserMapper.selectDirectPartyMemberVOByDeptId(deptId);
    }

    @Override
    @Transactional
    public Integer addOrgUpgrade(OrgUpgradeDTO orgUpgradeDto) {
        int count = 0;
        if (!organizationService.checkOrgIsExists(orgUpgradeDto.getDeptId())) {
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        orgUpgradeDto.setDeptName(organizationService.selectByPrimaryKey(orgUpgradeDto.getDeptId()).getName());
        //数据校验
        orgDataVerification(orgUpgradeDto);
        SysDeptUpgradeTemp sysDeptUpgradeTemp =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        orgUpgradeDto, SysDeptUpgradeTemp.class,  true);
        if (sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(sysDeptUpgradeTemp.getDeptId()) != null) {
            OrgUpgradeVO orgUpgradeVO = sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(
                    sysDeptUpgradeTemp.getDeptId());
            sysDeptUpgradeTemp.setId(orgUpgradeVO.getId());
            sysDeptUpgradeTemp.setDeptBranchs(orgUpgradeVO.getDeptBranchs());
            orgUpgradeDto.setId(orgUpgradeVO.getId());
            count += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        } else {
            PaddingBaseFieldUtil.paddingBaseFiled(sysDeptUpgradeTemp);
            count += sysDeptUpgradeTempMapper.insertSelective(sysDeptUpgradeTemp);
            orgUpgradeDto.setId(sysDeptUpgradeTemp.getId());
        }
        //维护附件，组织调整信息
        count += addOrModifyAdjustmentsAndAttachments(orgUpgradeDto);
        return count;
    }

    /**
     * 新增或修改组织调整和附件信息
     *
     * @param orgUpgradeDto
     * @return
     */
    @Transactional
    public Integer addOrModifyAdjustmentsAndAttachments(OrgUpgradeDTO orgUpgradeDto) {
        int count = 0;
        iTabPbAttachmentService.intelligentOperation(
                orgUpgradeDto.getAttachments(),
                orgUpgradeDto.getId(), AttachmentType.ORG_UPGRADE);
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        tabPbOrgnizeChange.setChangeType(59424L);
        tabPbOrgnizeChange.setDeptId(orgUpgradeDto.getDeptId());
        tabPbOrgnizeChange.setFileNumber(orgUpgradeDto.getFileNumber());
        tabPbOrgnizeChange.setChangeReason(orgUpgradeDto.getChangeReason());
        tabPbOrgnizeChange.setChangeDate(orgUpgradeDto.getChangeDate());
        tabPbOrgnizeChange.setOrgnizeName(orgUpgradeDto.getDeptName());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbOrgnizeChange);
        OrgChangeVO orgChangeVO = tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(
                orgUpgradeDto.getDeptId(), 59424L);
        if (orgChangeVO != null) {
            tabPbOrgnizeChange.setChangeId(orgChangeVO.getChangeId());
            count += tabPbOrgnizeChangeMapper.updateByPrimaryKeySelective(tabPbOrgnizeChange);
        } else {
            count += tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
        }
        return count;
    }

    @Override
    public Integer updateByPrimaryKeySelective(OrgUpgradeDTO orgUpgradeDto) {
        int count = 0;
        List<OrganizationDTO> organizationDTOS = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), OrganizationDTO.class);
        if (organizationDTOS.size() < 2) {
            throw new BusinessDataIncompleteException("请添加两个党支部");
        }
        organizationDTOS.forEach(branchOrganization -> {
            if (branchOrganization.getOrgnizeProperty() == null) {
                throw new BusinessDataIncompleteException("请选择组织类别");
            }
        });
        orgDataVerification(orgUpgradeDto);
        orgUpgradeDto.setId(sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(orgUpgradeDto.getDeptId()).getId());
        SysDeptUpgradeTemp sysDeptUpgradeTemp =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        orgUpgradeDto, SysDeptUpgradeTemp.class, true);
        count += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        return count;
    }

    @Override
    public OrgUpgradeVO selectOrgUpgradeByDeptId(Long deptId) {
        return sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(deptId);
    }
}
