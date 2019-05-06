package com.egovchina.partybuilding.partybuild.service.impl;


import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.dto.SpecialWorkerDTO;
import com.egovchina.partybuilding.partybuild.entity.SpecialWorkerQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbSpcialWorker;
import com.egovchina.partybuilding.partybuild.feign.SystemServiceFeignClient;
import com.egovchina.partybuilding.partybuild.repository.TabPbSpcialWorkerMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.SpecialWorkerService;
import com.egovchina.partybuilding.partybuild.vo.SpecialWorkerVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

@Service
@Transactional(rollbackFor = Exception.class)
public class SpecialWorkerServiceImpl implements SpecialWorkerService {

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    SystemServiceFeignClient systemServiceFeignClient;

    @Autowired
    TabPbSpcialWorkerMapper tabPbSpcialWorkerMapper;

    @Autowired
    TabSysDeptMapper tabSysDeptMapper;

    /**
     * 专干新增
     * roleId为44
     */
    @PaddingBaseField
    @Override
    public int insert(SpecialWorkerDTO specialWorkerDto) {
       if(tabSysDeptMapper.selectByPrimaryKey(specialWorkerDto.getManageOrgId()) == null &&
               tabSysDeptMapper.selectByPrimaryKey(specialWorkerDto.getDeptId()) == null ){
           throw new BusinessDataCheckFailException("根据传入的deptId或者mangerOrgId查找不到对应的组织部门");
       }
        Long userId = tabSysUserMapper.selectUserByIdCardNo(specialWorkerDto.getIdCardNo()).getUserId().longValue();
        specialWorkerDto.setUserId(userId);
        //设置专干角色为44，并且插入sys_user_role

        ReturnEntity<Boolean> returnEntity = systemServiceFeignClient.checkUserHasSpecifiedRole(44L, userId);
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        boolean exists = returnEntity.respBody(Boolean.class);
        if (!exists) {
            systemServiceFeignClient.insertUserRole(44L, userId);
        }
        int count = 0;
        if(tabPbSpcialWorkerMapper.checkSpecialWhetherTOLeave(userId)){
            throw new BusinessDataCheckFailException("该专干正处于任职状态，请离职后在任职");
        }else{
            TabPbSpcialWorker tabPbSpcialWorker =
                    generateTargetCopyPropertiesAndPaddingBaseField(
                            specialWorkerDto, TabPbSpcialWorker.class, false);
            count=tabPbSpcialWorkerMapper.insertSelective(tabPbSpcialWorker);
        }
        return  count;
    }

    /**
     * 删除
     *
     * @param specialWorkerId
     */
    @PaddingBaseField
    @Override
    public int deleteBySpecialWorkerId(Long specialWorkerId) {
        TabPbSpcialWorker spcialWorker = tabPbSpcialWorkerMapper.selectByPrimaryKey(specialWorkerId);
        if(spcialWorker == null){
            throw new BusinessDataCheckFailException("专干不存在");
        }
        spcialWorker.setDelFlag("1");
        spcialWorker.setEblFlag("0");
        paddingUpdateRelatedBaseFiled(spcialWorker);
        int count = tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(spcialWorker);
        //删除user_role表里面的专干信息
        if (count > 0) {
            tabPbSpcialWorkerMapper.deleteTabSpecialWorkerByUserId(spcialWorker.getUserId());
        }
        return count;
    }

    /**
     * 查询出所有的专干党员信息
     *
     * @return
     */
    @Override
    public PageInfo<SpecialWorkerVO> getSpecialWorkerList(Page page, SpecialWorkerQueryBean specialWorkerQueryBean) {
        PageHelper.startPage(page);
        List<SpecialWorkerVO> list = tabPbSpcialWorkerMapper.selectSpecialWorkerVOList(specialWorkerQueryBean);
        return new PageInfo<>(list);
    }

    /**
     * 修改
     *
     * @param specialWorkerDto
     * @return
     */
    @Override
    public int updateBySpecialWorkerId(SpecialWorkerDTO specialWorkerDto) {
        if(null==tabSysDeptMapper.selectByPrimaryKey(specialWorkerDto.getManageOrgId()) ||
                null==tabSysDeptMapper.selectByPrimaryKey(specialWorkerDto.getDeptId())){
            throw new BusinessDataCheckFailException("根据传入的deptId或者mangerOrgId查找不到对应的组织部门");
        }
        if(specialWorkerDto.getSpecialWorkerId()==null){
            throw new BusinessDataCheckFailException("缺少专干主键");
        }
        TabPbSpcialWorker tabPbSpcialWorker =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        specialWorkerDto, TabPbSpcialWorker.class, true);
        return tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(tabPbSpcialWorker);
    }


    /**
     * 详情查询
     * @param specialWorkerId
     * @return
     */
    @Override
    public SpecialWorkerVO selectSpecialWorkerById(Long specialWorkerId) {
        return tabPbSpcialWorkerMapper.selectSpecialWorkerVOById(specialWorkerId);
    }


}
