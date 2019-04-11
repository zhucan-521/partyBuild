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
import com.yizheng.partybuilding.repository.*;
import com.yizheng.partybuilding.service.inf.ITabPbDeptSecretaryService;
import com.yizheng.partybuilding.service.inf.ITabPbPositivesService;
import com.yizheng.partybuilding.service.inf.TabPbFamilyService;
import com.yizheng.partybuilding.system.entity.SysDict;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import lombok.NonNull;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        if(deptSecretaryMapper.selectByIdCardNo(record.getUser().getIdCardNo(),record.getDeptId()+"")>=1){
            throw new BusinessDataIncompleteException("该组织已存在此人员信息");
        }
        SysUser user = userMapper.selectUserByIdCardNo(record.getUser().getIdCardNo());
        //最大排序码
        Long orderNum = deptSecretaryMapper.maxOrderNum(record.getDeptId());
        //判断该人员信息是否存在
        if(user==null){
            record.getUser().setOrderNum(orderNum+1);
            userMapper.insertSelective(record.getUser());
            record.setUserId(record.getUser().getUserId().longValue());
        }else {
            //是否修改排序码
            if(user.getOrderNum()==null || "".equals(user.getOrderNum())){
                record.getUser().setOrderNum(orderNum+1);
            }
            //是否修改头像
            if(record.getUser().getAvatar()!=null && !"".equals(record.getUser().getAvatar())){
                userMapper.editAvatar(record.getUser().getAvatar(),user.getUserId()+"");
            }
            record.setUserId(user.getUserId().longValue());
        }
        //判断书记有没有添加家庭成员
        if(!CollectionUtil.isEmpty(record.getFamilyList())){
            record.getFamilyList().forEach(v->v.setUserId(record.getUserId()));
            //保存家庭成员
            handleFamily(record.getFamilyList(),record.getUserId());
        }
        //判断书记有没有添加职务
        if(!CollectionUtil.isEmpty(record.getPositivesList())){
            record.getPositivesList().forEach(v->{
                if(v.getOrderNum() == null || "".equals(v.getOrderNum())){
                    throw new BusinessDataIncompleteException("职务没传排序码");
                }
                v.setUserId(record.getUserId());
            });
            //保存党内职务
            handlePositives(record.getPositivesList(),record.getUserId());
            //找到职位最大的排序码
            Long orderNumPositives = record.getPositivesList().stream().mapToLong((x)->x.getOrderNum()).summaryStatistics().getMax();
            record.setOrderNum(orderNumPositives);
        }
        //保存书记
        int retVal = deptSecretaryMapper.insertSelective(record);
        return retVal;
    }

    @Override
    public TabPbDeptSecretary selectByPrimaryKey(Long secretaryId) {
        TabPbDeptSecretary secretary = new TabPbDeptSecretary();
        secretary.setSecretaryId(secretaryId);
        secretary = deptSecretaryMapper.selectByPrimaryKey(secretary);
        if(secretary != null){
            if(deptSecretaryMapper.punishmentRewards(secretary.getUserId()).size()>0 ||deptSecretaryMapper.punishmentRewards(secretary.getUserId())!=null){
                secretary.setRewardsDtoList(deptSecretaryMapper.punishmentRewards(secretary.getUserId()));
            }
            secretary.setFamilyList(pbFamilyMapper.selectListPrimary(secretary.getUserId()));
            TabPbPositives positives = new TabPbPositives();
            positives.setUserId(secretary.getUserId());
            positives.setPositiveType(59421);
            secretary.setPositivesList(positivesMapper.selectList(positives));
        }
        return secretary;
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateByPrimaryKeySelective(TabPbDeptSecretary record) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(record);
        if(secretary != null){
            if(record.getUser()!=null){
                sysUserMapper.updateById(record.getUser());
            }
            if(!CollectionUtil.isEmpty(record.getFamilyList())){
                handleFamily(record.getFamilyList(),record.getUser().getUserId().longValue());
            }else{
                pbFamilyMapper.tombstoneUser(record.getUser().getUserId().longValue());
            }
            if(!CollectionUtil.isEmpty(record.getPositivesList())){
                for(TabPbPositives positive : record.getPositivesList()){
                    if(positive.getOrderNum()==null || "".equals(positive.getOrderNum())){
                        throw new BusinessDataIncompleteException("职务没传排序码");
                    }
                }
                handlePositives(record.getPositivesList(),record.getUserId());
                Long orderNumPositives = record.getPositivesList().stream().mapToLong((x)->x.getOrderNum()).summaryStatistics().getMax();
                record.setOrderNum(orderNumPositives);
            }else{
                positivesMapper.tombstoneUser(record.getUserId());
                record.setOrderNum(500L);
            }
            int retVal = deptSecretaryMapper.updateByPrimaryKeySelective(record);
            return retVal;
        }else{
            throw new BusinessDataIncompleteException("该书记不存在");
        }
    }

    /**
     * 处理家庭成员数据
     * @param familyList
     * @param userId
     */
    private void handleFamily(List<TabPbFamily> familyList,Long userId){
        //根据党员id查出所属家庭成员
        List<TabPbFamily> pbFamilyList = pbFamilyMapper.selectListPrimary(userId);
        //数据库不存在数据直接添加
        if(CollectionUtil.isEmpty(pbFamilyList)){
            //add
            addFamily(familyList);
            return;
        }
        if(!CollectionUtil.isEqualCollection(pbFamilyList,familyList)){
            //数据库和前端不想等的需要删除
            List<TabPbFamily> removeFamily = pbFamilyList.stream().filter(family-> !familyList.contains(family.getRelationId())).collect(Collectors.toList());
            //前端和数据库不想等的需要添加
            List<TabPbFamily> addFamily = familyList.stream().filter(family -> !pbFamilyList.contains(family.getRelationId())).collect(Collectors.toList());
            //相等的进行修改
            List<TabPbFamily> editFamily = familyList.stream().filter(family -> pbFamilyList.contains(family.getRelationId())).collect(Collectors.toList());
            //修改
            editFamily.forEach(v->familyService.updateByPrimaryKeySelective(v));
            //logicDelete
            removeFamily.forEach(v->familyService.deleteByPrimaryKey(v.getRelationId()));
            //add
            addFamily(addFamily);
        }
    }

    /**
     * 保存家庭成员
     * @param addFamily
     */
    private void addFamily(@NonNull List<TabPbFamily> addFamily){
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
        if(CollectionUtil.isEmpty(pbPositivesList)){
            for (TabPbPositives positive : positivesList){
                if(positive.getOrderNum()==null || "".equals(positive.getOrderNum())){
                    throw new BusinessDataIncompleteException("职务没传排序码");
                }
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
            //相等的进行修改
            List<TabPbPositives> editPositives = positivesList.stream().filter(positive -> pbPositivesList.contains(positive.getPositiveId())).collect(Collectors.toList());
            //修改
            editPositives.forEach(v->positivesMapper.updateByPrimaryKeySelective(v));
            //批量逻辑删除
            positivesMapper.tombstone(removePositives);
            //批量添加
            for (TabPbPositives positive : addPositives){
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(positive);
                positive.setPositiveType(59421);
            }
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
        if(record!=null){
            if(!userMapper.verification(UserContextHolder.getOrgId(),record.getRangeDeptId())){
                //不属于改变orgId的值
                record.setRangeDeptId(UserContextHolder.getOrgId());
                record.setOrgRange("2");
            }
        }
        PageHelper.startPage(page);
        var list = deptSecretaryMapper.selectList(record);
        return list;
    }

    @Override
    public int updateOrderNum(Long oldId, Long oldNum, Long newId, Long newNum) {
        int retVal = deptSecretaryMapper.updateOrderNum(newNum,oldId);
        retVal += deptSecretaryMapper.updateOrderNum(oldNum,newId);
        return retVal;
    }


}
