package com.egovchina.partybuilding.partybuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DirectPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.OrgUpgradeDto;
import com.egovchina.partybuilding.partybuild.dto.OrgUpgradedPersonnelTransferDTO;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.SysDeptUpgradeTemp;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.repository.SysDeptUpgradeTempMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgnizeChangeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.OrgUpgradeService;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
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

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesAndPaddingBaseField;

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
    private TabSysDeptService tabSysDeptService;
    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 批量修改人员组织信息
     * @param orgUpgradedPersonnelTransferDTO
     * @return
     */
    @Override
    @Transactional
    public int batchDeptIdByUserId(OrgUpgradedPersonnelTransferDTO orgUpgradedPersonnelTransferDTO) {
        Map<String, List<DirectPartyMemberDTO>> directStaffListMap = orgUpgradedPersonnelTransferDTO.getDirectStaffListMap();
        Long deptId  = orgUpgradedPersonnelTransferDTO.getDeptId();
        int count = 0;
        OrgUpgradeVO orgUpgradeVO = sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(deptId);
        OrgUpgradeDto orgUpgradeDto = new OrgUpgradeDto();
        if(orgUpgradeVO != null){
            BeanUtil.copyPropertiesIgnoreNull(orgUpgradeVO,orgUpgradeDto);
            orgDataVerification(orgUpgradeDto);//校验
            //修改组织信息
            SysDeptDto sysDeptDto = new SysDeptDto();
            sysDeptDto.setDeptId(orgUpgradeDto.getDeptId().intValue());
            sysDeptDto.setName(orgUpgradeDto.getUpgradeDeptName());
            sysDeptDto.setOrgShortName(orgUpgradeDto.getUpgradeShortName());
            sysDeptDto.setIsParent((byte)1);
            sysDeptDto.setOrgnizeProperty(1001L);
            PaddingBaseFieldUtil.paddingBaseFiled(sysDeptDto);
            count += tabSysDeptMapper.updateByPrimaryKeySelective(sysDeptDto);
        }else {
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        List<DirectPartyMemberVO> directPartyMemberVOS =
                tabSysUserMapper.selectDirectPartyMemberVOByDeptId(orgUpgradeDto.getDeptId());
        ArrayList<Integer> longArrayList = new ArrayList<>();
        List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), SysDeptDto.class);
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
            for(Map.Entry<String, List<DirectPartyMemberDTO>> vo : directStaffListMap.entrySet()){
                if(branchOrganization.getName().equals(vo.getKey())){
                    //获取userId集合
                    List<Integer> userIds = directStaffListMap.get(vo.getKey()).stream().map(DirectPartyMemberDTO::getUserId).collect(Collectors.toList());
                    if(userIds.size() == directPartyMemberVOS.size()){
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
        if(longArrayList.size() < directPartyMemberVOS.size()){
            throw new BusinessDataIncompleteException("请分配升级组织下所有的人员");
        }
        count += addOrModifyAdjustmentsAndAttachments(orgUpgradeDto);
        return count;
    }

    /**
     * 数据校验
     * @param orgUpgradeDto
     */
    private void orgDataVerification(OrgUpgradeDto orgUpgradeDto) {
        String deptName = orgUpgradeDto.getUpgradeDeptName();
        if (tabSysDeptService.checkOrgNameAvailability(deptName)) {
            throw new BusinessDataInvalidException(deptName+"组织名称重复");
        }
        //支部组织数据校验
        if(StringUtil.isNotEmpty(orgUpgradeDto.getDeptBranchs())){
            List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), SysDeptDto.class);
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
                            throw new BusinessDataIncompleteException(sysDeptDto.getName()+"单位名称不能为空");
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
    public Integer addOrgUpgrade(OrgUpgradeDto orgUpgradeDto) {
        int count = 0;
        if(!tabSysDeptService.checkOrgIsExists(orgUpgradeDto.getDeptId())){
            throw new BusinessDataNotFoundException("升级组织不存在");
        }
        orgUpgradeDto.setDeptName(tabSysDeptService.selectByPrimaryKey(orgUpgradeDto.getDeptId()).getName());
        //数据校验
        orgDataVerification(orgUpgradeDto);
        SysDeptUpgradeTemp sysDeptUpgradeTemp =
                copyPropertiesAndPaddingBaseField(
                        orgUpgradeDto ,SysDeptUpgradeTemp.class ,true ,true);
        if(sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(sysDeptUpgradeTemp.getDeptId())!= null){
            OrgUpgradeVO orgUpgradeVO = sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(
                    sysDeptUpgradeTemp.getDeptId());
            sysDeptUpgradeTemp.setId(orgUpgradeVO.getId());
            sysDeptUpgradeTemp.setDeptBranchs(orgUpgradeVO.getDeptBranchs());
            orgUpgradeDto.setId(orgUpgradeVO.getId());
            count += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        }else {
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
     * @param orgUpgradeDto
     * @return
     */
    @Transactional
    public Integer addOrModifyAdjustmentsAndAttachments(OrgUpgradeDto orgUpgradeDto){
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
        TabPbOrgnizeChange pbOrgnizeChange = tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(
                orgUpgradeDto.getDeptId(), 59424L);
        if(pbOrgnizeChange != null){
            tabPbOrgnizeChange.setChangeId(pbOrgnizeChange.getChangeId());
            count += tabPbOrgnizeChangeMapper.updateByPrimaryKeySelective(tabPbOrgnizeChange);
        }else {
            count += tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
        }
        return count;
    }

    @Override
    public Integer updateByPrimaryKeySelective(OrgUpgradeDto orgUpgradeDto) {
        int count = 0;
        List<SysDeptDto> sysDeptDtos = JSONObject.parseArray(orgUpgradeDto.getDeptBranchs(), SysDeptDto.class);
        if(sysDeptDtos.size() < 2){
            throw new BusinessDataIncompleteException("请添加两个党支部");
        }
        sysDeptDtos.forEach(branchOrganization -> {
            if(branchOrganization.getOrgnizeProperty() == null){
                throw new BusinessDataIncompleteException("请选择组织类别");
            }
                });
        orgDataVerification(orgUpgradeDto);
        orgUpgradeDto.setId(sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(orgUpgradeDto.getDeptId()).getId());
        SysDeptUpgradeTemp sysDeptUpgradeTemp =
                copyPropertiesAndPaddingBaseField(
                        orgUpgradeDto,SysDeptUpgradeTemp.class, true, true);
        count += sysDeptUpgradeTempMapper.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        return count;
    }

    @Override
    public OrgUpgradeVO selectOrgUpgradeByDeptId(Long deptId) {
        return sysDeptUpgradeTempMapper.selectOrgUpgradeVOByDeptId(deptId);
    }
}
