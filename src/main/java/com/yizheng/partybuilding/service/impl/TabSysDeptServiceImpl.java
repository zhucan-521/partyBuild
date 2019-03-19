package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.util.*;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.SysDeptDto;
import com.yizheng.partybuilding.dto.SysDeptDtoWithCountInfo;
import com.yizheng.partybuilding.dto.TabDeptPositionDto;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbOrgClassify;
import com.yizheng.partybuilding.entity.TabPbUnitInfo;
import com.yizheng.partybuilding.repository.TabPbOrgClassifyMapper;
import com.yizheng.partybuilding.repository.TabPbUnitInfoMapper;
import com.yizheng.partybuilding.repository.TabSysDeptMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;
import com.yizheng.partybuilding.system.entity.SysDept;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组织信息service实现
 *
 * @Author Zhang Fan
 **/
@Service("tabSysDeptService")
public class TabSysDeptServiceImpl implements TabSysDeptService {

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;
    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;
    @Autowired
    private TabPbOrgClassifyMapper tabPbOrgClassifyMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public List<SysDept> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<SysDept> list = tabSysDeptMapper.selectWithConditions(conditions);
        return list;
    }

    @Override
    public boolean checkOrgNameAvailability(String deptName) {
        SysDept dbSysDept = tabSysDeptMapper.checkOrgNameAvailability(deptName);
        return dbSysDept != null;
    }

    @PaddingBaseField
    @Override
    public int insert(SysDept sysDept) {
        sysDept.setOrgCode(IdWorker.getId());
        sysDept.setCreateTime(new Date());
        return tabSysDeptMapper.insert(sysDept);
    }

    @Override
    public SysDept selectByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectByPrimaryKey(deptId);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int insertWithAbout(SysDeptDto sysDeptDto) {
        int retVal = 0;

        //新增组织信息
        retVal += tabSysDeptMapper.insertSelective(sysDeptDto);
        //新增Relation表
        tabSysDeptMapper.insertToDeptRelationTable(sysDeptDto.getParentId(), sysDeptDto.getDeptId());

        //新增分类定等数据，如果需要
        if (sysDeptDto.getOrgLevel() != null) {
            TabPbOrgClassify tabPbOrgClassify = new TabPbOrgClassify();
            tabPbOrgClassify.setOrgLevel(sysDeptDto.getOrgLevel());
            tabPbOrgClassify.setLevelDate(sysDeptDto.getLevelDate());
            tabPbOrgClassify.setDeptId(sysDeptDto.getDeptId().longValue());
            tabPbOrgClassifyMapper.insertSelective(tabPbOrgClassify);
        }

        //新增单位数据
        List<TabPbUnitInfo> tabPbUnitInfoList = sysDeptDto.getTabPbUnitInfos();
        if (tabPbUnitInfoList != null && tabPbUnitInfoList.size() > 0) {
            tabPbUnitInfoList.forEach(tabPbUnitInfo -> insertUnitInfo(sysDeptDto, tabPbUnitInfo));
        }
        //维护组织表单位及 full_path
        modifyFullPathAndSubDeptIfNecessary(sysDeptDto);
        tabSysDeptMapper.updateByPrimaryKeySelective(sysDeptDto);
        return retVal;
    }

    @PaddingBaseField
    @Override
    public int logicDeleteById(Long deptId) {
        return tabSysDeptMapper.logicDeleteById(deptId);
    }

    @Override
    public SysDeptDto selectWithAboutInfoByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectWithAboutInfoByPrimaryKey(deptId);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int updateWithAbout(SysDeptDto sysDeptDto) {
        int retVal = 0;

        List<TabPbUnitInfo> tabPbUnitInfoList = sysDeptDto.getTabPbUnitInfos();
        if (CollectionUtil.isNotEmpty(tabPbUnitInfoList)) {
            List<Long> pendingUnitIdList = tabPbUnitInfoList.stream().map(TabPbUnitInfo::getUnitId).collect(Collectors.toList());
            List<TabPbUnitInfo> dbTabPbUnitInfoList = tabPbUnitInfoMapper.selectByOrgId(sysDeptDto.getDeptId().longValue());
            if (CollectionUtil.isNotEmpty(dbTabPbUnitInfoList)) { //数据库存在
                List<Long> pendingRemoveUnitIdList = dbTabPbUnitInfoList.stream().filter(tabPbUnitInfo -> !pendingUnitIdList.contains(tabPbUnitInfo.getUnitId()))
                        .map(TabPbUnitInfo::getUnitId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveUnitIdList)) {
                    retVal += tabPbUnitInfoMapper.batchLogicDeleteById(pendingRemoveUnitIdList);
                }
            }

            tabPbUnitInfoList.forEach(tabPbUnitInfo -> {
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbUnitInfo);
                if (tabPbUnitInfo.getUnitId() != null && tabPbUnitInfo.getUnitId() > 0) { //修改
                    temporaryUnitId(sysDeptDto, tabPbUnitInfo);
                    tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
                } else { //新增
                    tabPbUnitInfo.setOrgId(sysDeptDto.getDeptId().longValue());
                    insertUnitInfo(sysDeptDto, tabPbUnitInfo);
                }
            });
        }

        modifyFullPathAndSubDeptIfNecessary(sysDeptDto);
        retVal += tabSysDeptMapper.updateWithRelationByPrimaryKeySelective(sysDeptDto);
        return retVal;
    }

    /**
     * 如有必要,维护 full_path 和下级组织 full_path
     *
     * @param sysDept
     */
    @Override
    public boolean modifyFullPathAndSubDeptIfNecessary(SysDept sysDept) {
        Integer deptId = sysDept.getDeptId();
        Integer parentId = sysDept.getParentId();

        boolean ifNecessary = false; //是否需要维护
        boolean subIfNecessary = false;
        String oldFullPath = null;
        if (deptId == null || deptId == 0) { //新增
            ifNecessary = true;
        }
        if (!ifNecessary) {
            //从 db 中拿当前组织旧数据
            SysDept dbOldSysDept = tabSysDeptMapper.selectAloneByPrimaryKey(deptId.longValue());
            //上级节点有改变
            if (dbOldSysDept != null && dbOldSysDept.getParentId() != sysDept.getParentId()) {
                ifNecessary = true;
                subIfNecessary = true;
                oldFullPath = dbOldSysDept.getFullPath();
            }
        }

        //需要维护
        if (ifNecessary) {
            //获取数据库父组织信息
            SysDept dbParentDept = tabSysDeptMapper.selectAloneByPrimaryKey(parentId.longValue());
            //拼接从出新的 full_path
            String newFullPath = String.format("%s,%s", dbParentDept.getFullPath(), deptId);
            sysDept.setFullPath(newFullPath);
//            sysDept.setOrderNum(StringUtil.appearNumber(newFullPath, ","));

            if (subIfNecessary) {
                tabSysDeptMapper.updateFullPathForSubs(oldFullPath, newFullPath, deptId);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<HashMap<String, Object>> selectToMapWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabSysDeptMapper.selectToMapWithConditions(conditions);
    }

    @Override
    public int pushPairOrgIdToDeptTable(Long pairOrgId, Long orgId) {
        return tabSysDeptMapper.pushPairOrgIdToDeptTable(pairOrgId, orgId);
    }

    @Override
    public SysDept selectAloneByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectAloneByPrimaryKey(deptId);
    }

    @Override
    @Cacheable(value = "COUNT::ORG_LIST::ORG_PARTY-MAN", key = "#sysDept.deptId", unless = "#result == null")
    public SysDeptDtoWithCountInfo linkCountInfo(SysDept sysDept) {
        return tabSysDeptMapper.countOrgWithPartyManInfo(sysDept.getDeptId());
    }


    @Override
    public int updateByPrimaryKeySelective(SysDept record) {
        return tabSysDeptMapper.updateByPrimaryKeySelective(record);
    }

    //暂存单位ID
    private void temporaryUnitId(SysDeptDto sysDeptDto, TabPbUnitInfo tabPbUnitInfo) {
        if (tabPbUnitInfo.getIfMaster() == 1) { //主单位, 保留ID
            sysDeptDto.setUnitId(tabPbUnitInfo.getUnitId());
            sysDeptDto.setUnitName(tabPbUnitInfo.getUnitName());
        }
    }

    //新增单位信息
    private void insertUnitInfo(SysDeptDto sysDeptDto, TabPbUnitInfo tabPbUnitInfo) {
        tabPbUnitInfo.setOrgId(sysDeptDto.getDeptId().longValue()); //关联组织ID
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbUnitInfo);
        tabPbUnitInfoMapper.insertSelective(tabPbUnitInfo);

        temporaryUnitId(sysDeptDto, tabPbUnitInfo);
    }

    @Override
    public boolean checkOrgIsExists(Long deptId) {
        return tabSysDeptMapper.selectByPrimaryKey(deptId) != null;
    }

    @Override
    public List<TabDeptPositionDto> selectPositionWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabSysDeptMapper.selectPositionWithConditions(conditions);
    }

    @Override
    public TabDeptPositionDto selectPositionWithAboutInfoByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectPositionWithAboutInfoByPrimaryKey(deptId);
    }

    @PaddingBaseField
    @Override
    public int updatePositionWithAnnexs(SysDept sysDept, List<TabPbAttachment> attList) {
        int retVal = tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(attList,
                    sysDept.getDeptId().longValue(), AttachmentType.ORG_POSITION);
        }
        return retVal;
    }

    @Override
    public void excelImportEffectiveDataToDb(List<SysDept> effectiveList) {
        if (CollectionUtil.isNotEmpty(effectiveList)) {
            tabSysDeptMapper.batchInsert(effectiveList);
            tabSysDeptMapper.batchInsertRelation(effectiveList);
        }
    }

    @Override
    public SysDept excelImportEntityConvert(String[] row) {
        int index = 0;
        String code = row[++index]; //组织编号
        String uncertainty = row[++index]; //组织所在单位情况
        String name = row[++index]; //组织名称
        String dependencyRelation = row[++index]; //党组织所在单位代码
        String orgLevel = row[++index]; //组织类别
        String state = row[++index]; //组织状态
        String parentId = row[++index]; //上级组织
        String orgnizeMaster = row[++index]; //组织书记
        String concat = row[++index]; //联系人
        String phone = row[++index]; //联系电话
        String address = row[++index]; //详细地址
        String faxNumber = row[++index]; //传真
        String postCode = row[++index]; //邮编
        String communityAddr = row[++index]; //所属社区
        String foundedDate = row[++index]; //建立日期
        String foundedFileNumber = row[++index]; //建立文号

        SysDept sysDept = new SysDept();
        sysDept.setOrgCode(code);
        sysDept.setName(name);
        sysDept.setOrgLevel(Long.parseLong(orgLevel));
        sysDept.setOrgStatus(Long.parseLong(state));
        sysDept.setParentId(Integer.parseInt(parentId));
        String[] master = orgnizeMaster.split(",");
        sysDept.setOrgnizeMaster(master[0]);
        sysDept.setOrgnizeMasterId(Long.parseLong(master[1]));
        sysDept.setContactor(concat);
        sysDept.setContactNumber(phone);
        sysDept.setAddress(address);
        sysDept.setFaxNumber(faxNumber);
        sysDept.setPostCode(postCode);
        sysDept.setCommunityAddr(Long.parseLong(communityAddr));
        sysDept.setFoundedDate(new Date(foundedDate));
        sysDept.setFoundedFileNumber(foundedFileNumber);
        PaddingBaseFieldUtil.paddingBaseFiled(sysDept);
        return sysDept;
    }

    @Override
    public String excelImportPreValidate(String[] row) {
        int index = 0;
        String code = row[++index]; //组织编号
        String uncertainty = row[++index]; //组织所在单位情况
        String affiliatedOrganization = row[++index]; //所属组织
        String name = row[++index]; //组织名称
        String dependencyRelation = row[++index]; //党组织所在单位代码
        String orgLevel = row[++index]; //组织类别
        String state = row[++index]; //组织状态
        String parentOrgName = row[++index]; //上级组织
        String orgnizeMaster = row[++index]; //组织书记
        String concat = row[++index]; //联系人
        String phone = row[++index]; //联系电话
        String address = row[++index]; //详细地址
        String faxNumber = row[++index]; //传真
        String postCode = row[++index]; //邮编
        String communityAddr = row[++index]; //所属社区
        String foundedDate = row[++index]; //建立日期
        String foundedFileNumber = row[++index]; //建立文号

        StringBuffer error = new StringBuffer();
        if (StringUtils.isEmpty(code)) {
            error.append("党组织编号不能为空 | ");
        }
        if (StringUtils.isEmpty(name)) {
            error.append("党组织名称不能为空 | ");
        }
        if (StringUtils.isEmpty(state)) {
            error.append("党组织类别不能为空 | ");
        } else {
            //TODO 数据字典校验类别码是否有效
        }
        if (StringUtils.isEmpty(state)) {
            error.append("党组织状态不能为空 | ");
        } else {
            //TODO 数据字典校验状态码是否有效
        }
        if (StringUtils.isEmpty(parentOrgName)) {
            error.append("上级组织不能为空 | ");
        } else {
            //TODO 数据库校验上级组织是否存在
        }
        if (StringUtils.isEmpty(orgnizeMaster)) {
            error.append("党组织书记不能为空 | ");
        }
        if (StringUtils.isEmpty(concat)) {
            error.append("联系人不能为空 | ");
        }
        if (StringUtils.isEmpty(phone)) {
            error.append("联系方式不能为空 | ");
        } else {
            if (!RegexpVerifyUtil.isMobileNo(phone)) {
                error.append("无效的联系方式 | ");
            }
        }
        if (StringUtils.isEmpty(communityAddr)) {
            error.append("所属社区不能为空 | ");
        }
        if (StringUtils.isEmpty(foundedDate)) {
            error.append("建立日期不能为空 | ");
        }
        if (StringUtils.isEmpty(foundedFileNumber)) {
            error.append("建立文号不能为空 | ");
        }
        return error.toString();
    }

    @Override
    public String excelTemplateName() {
        return "temp_orgnaizationInfo";
    }
}
