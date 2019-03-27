package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDeptSecretary;
import com.yizheng.partybuilding.entity.TabPbFamily;
import com.yizheng.partybuilding.entity.TabPbPositives;
import com.yizheng.partybuilding.repository.TabPbDeptSecretaryMapper;
import com.yizheng.partybuilding.repository.TabPbFamilyMapper;
import com.yizheng.partybuilding.repository.TabPbPositivesMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ITabPbDeptSecretaryService;
import com.yizheng.partybuilding.service.inf.TabPbFamilyService;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYingXiang on 2019/03/01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TabPbDeptSecretaryServiceImpl implements ITabPbDeptSecretaryService {

    @Autowired
    TabPbDeptSecretaryMapper deptSecretaryMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TabSysUserMapper userMapper;

    @Autowired
    private TabPbFamilyMapper pbFamilyMapper;

    @Autowired
    private TabPbPositivesMapper positivesMapper;

    //service层
    @Autowired
    private TabPbFamilyService familyService;

    @Override
    @PaddingBaseField
    public int insertSelective(TabPbDeptSecretary record) {
        if(record==null){
            throw new BusinessDataIncompleteException("请传入书记参数");
        }
        if(record.getUser()== null){
            throw new BusinessDataIncompleteException("请传入党员参数");
        }
        SysUser user = userMapper.selectUserByIdCardNo(record.getUser().getIdCardNo());
        //判断该人员信息是否存在
        if(user==null){
            userMapper.insertSelective(record.getUser());
            record.setUserId(record.getUser().getUserId().longValue());

        }else {
            record.setUserId(user.getUserId().longValue());
        }
        if(record.getFamilyList().size()>0){
            record.getFamilyList().forEach(v->v.setUserId(record.getUserId()));
        }
        if(record.getPositivesList().size()>0){
            record.getPositivesList().forEach(v->v.setUserId(record.getUserId()));
        }
        //保存书记
        int retVal = deptSecretaryMapper.insertSelective(record);
        //保存家庭成员
        if(!CollectionUtil.isEmpty(record.getFamilyList())){
            handleFamily(record.getFamilyList(),record.getUserId());
        }
        //保存党内职务
        if(!CollectionUtil.isEmpty(record.getPositivesList())){
            handlePositives(record.getPositivesList(),record.getUserId());
        }
        return retVal;
    }

    @Override
    public TabPbDeptSecretary selectByPrimaryKey(Long secretaryId) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(secretaryId);
        if(secretary != null){
            secretary.setRewardsDtoList(deptSecretaryMapper.punishmentRewards(secretary.getUser().getUserId().longValue()));
            secretary.setFamilyList(pbFamilyMapper.selectListPrimary(secretary.getUserId()));
        }
        return secretary;
    }

    @Override
    @PaddingBaseField(recursive = true,updateOnly = true)
    public int updateByPrimaryKeySelective(TabPbDeptSecretary record) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(record.getSecretaryId());
        if(secretary != null){
            int retVal = deptSecretaryMapper.updateByPrimaryKeySelective(record);
            if(record.getUser()!=null){
                retVal += sysUserMapper.updateById(record.getUser());
            }
            handleFamily(record.getFamilyList(),record.getUserId());
            handlePositives(record.getPositivesList(),record.getUserId());
            return retVal;
        }else{
            throw new BusinessDataIncompleteException("该书记不存在");
        }
    }
    //处理家庭成员
    private void handleFamily(List<TabPbFamily> familyList,Long userId){
        //根据党员id查出所属家庭成员
        List<TabPbFamily> pbFamilyList = pbFamilyMapper.selectListPrimary(userId);
        //数据库不存在数据直接添加
        if(!CollectionUtil.isEmpty(pbFamilyList)){
            familyList.forEach(v-> {
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(v);
                familyService.add(v);
            });
            return;
        }
        if(!CollectionUtil.isEqualCollection(pbFamilyList,familyList)){
            //数据库和前端不想等的需要删除
            List<TabPbFamily> removeFamily = pbFamilyList.stream().filter(family-> !familyList.contains(family.getRelationId())).collect(Collectors.toList());
            //前端和数据库不想等的需要添加
            List<TabPbFamily> addFamily = familyList.stream().filter(family -> !pbFamilyList.contains(family.getRelationId())).collect(Collectors.toList());
            //logicDelete
            removeFamily.forEach(v->familyService.updateByPrimaryKeySelective(v));
            //add
            addFamily.forEach(family-> {
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(family);
                familyService.add(family);
            });
        }
    }

    /**
     * 处理党内职务
     * @param positivesList 前端list数据
     * @param userId 党员id
     */
    private void handlePositives(List<TabPbPositives> positivesList,Long userId){
        TabPbPositives positives = new TabPbPositives();
        positives.setUserId(userId);
        List<TabPbPositives> pbPositivesList = positivesMapper.selectPositives(positives);
        //数据库不存在直接添加
        if(!CollectionUtil.isEmpty(pbPositivesList)){
            for (TabPbPositives positive : positivesList){
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(positive);
            }
            positivesMapper.batchAdd(positivesList);
            return;
        }
        if(!CollectionUtil.isEqualCollection(pbPositivesList,positivesList)){
            //数据库和前端不想等的需要删除
            List<TabPbPositives> removePositives = pbPositivesList.stream().filter(positive -> !positivesList.contains(positives.getPositiveId())).collect(Collectors.toList());
            //前端和数据库不想等的需要添加
            List<TabPbPositives> addPositives = positivesList.stream().filter(positive -> !pbPositivesList.contains(positive.getPositiveId())).collect(Collectors.toList());
            //逻辑删除
            positivesMapper.tombstone(removePositives);
            //批量添加
            positivesMapper.batchAdd(addPositives);
        }
    }

    @Override
    public int tombstone(Long secretaryId) {
        TabPbDeptSecretary record = new TabPbDeptSecretary();
        record.setUpdateTime(new Date());
        record.setUpdateUserid(UserContextHolder.getUserIdLong());
        record.setUpdateUsername(UserContextHolder.getUserName());
        record.setSecretaryId(secretaryId);
        return deptSecretaryMapper.tombstone(record);
    }

    @Override
    public List<TabPbDeptSecretary> selectList(TabPbDeptSecretary record, Page page) {
        PageHelper.startPage(page);
        return deptSecretaryMapper.selectList(record);
    }
}
