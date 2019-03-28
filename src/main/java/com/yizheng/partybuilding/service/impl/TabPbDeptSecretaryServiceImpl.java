package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.util.UserContextHolder;
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
import lombok.NonNull;
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
    public int insertSelective(@NonNull TabPbDeptSecretary record) {
        if(record.getUser()== null){
            throw new BusinessDataIncompleteException("请传入党员参数");
        }
        if(record.getDeptId()==null){
            throw new BusinessDataIncompleteException("请传入组织Id");
        }
        SysUser user = userMapper.selectUserByIdCardNo(record.getUser().getIdCardNo());
        //判断该人员信息是否存在
        if(user==null){
            userMapper.insertSelective(record.getUser());
            record.setUserId(record.getUser().getUserId().longValue());
        }else {
            record.setUserId(user.getUserId().longValue());
        }
        if(!CollectionUtil.isEmpty(record.getFamilyList())){
            record.getFamilyList().forEach(v->v.setUserId(record.getUserId()));
            //保存家庭成员
            handleFamily(record.getFamilyList(),record.getUserId());
        }
        if(!CollectionUtil.isEmpty(record.getPositivesList())){
            record.getPositivesList().forEach(v->v.setUserId(record.getUserId()));
            //保存党内职务
            handlePositives(record.getPositivesList(),record.getUserId());
        }
//        //保存书记
//        if(0L == record.getWhetherSecretary()){
//            record.setOrderNum(1L);
//        }else{
//            Long orderNum = deptSecretaryMapper.maxOrderNum(record.getDeptId());
//            if(orderNum>=1){
//                orderNum += orderNum;
//            }else {
//                orderNum = 2L;
//            }
//            record.setOrderNum(orderNum);
//        }
        int retVal = deptSecretaryMapper.insertSelective(record);
        return retVal;
    }

    @Override
    public TabPbDeptSecretary selectByPrimaryKey(Long secretaryId) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(secretaryId);
        if(secretary != null){
            secretary.setRewardsDtoList(deptSecretaryMapper.punishmentRewards(secretary.getUserId()));
            secretary.setFamilyList(pbFamilyMapper.selectListPrimary(secretary.getUserId()));
            TabPbPositives positives = new TabPbPositives();
            positives.setUserId(secretary.getUserId());
            positives.setPositiveType(59421);
            secretary.setPositivesList(positivesMapper.selectPositives(positives));
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
                if(v.getIdCardNo()!=null && v.getIdCardNo().length()==18){
                    if(Integer.parseInt(v.getIdCardNo().substring(16,17))%2==1){
                        //女
                        v.setGender(96L);
                    }else{
                        //男
                        v.setGender(95L);
                    }
                }
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
                if(family.getIdCardNo()!=null && family.getIdCardNo().length()==18){
                    if(Integer.parseInt(family.getIdCardNo().substring(16,17))%2==1){
                        //女
                        family.setGender(96L);
                    }else{
                        //男
                        family.setGender(95L);
                    }
                }
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
        positives.setPositiveType(59421);
        List<TabPbPositives> pbPositivesList = positivesMapper.selectPositives(positives);
        //数据库不存在直接添加
        if(!CollectionUtil.isEmpty(pbPositivesList)){
            for (TabPbPositives positive : positivesList){
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(positive);
                positive.setPositiveType(59421);
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
        if(record!=null){
            if(!userMapper.verification(UserContextHolder.getOrgId(),record.getDeptId())){
                //不属于改变orgId的值
                record.setDeptId(UserContextHolder.getOrgId());
                record.setOrgRange(2L);
            }
        }
        return deptSecretaryMapper.selectList(record);
    }
}
