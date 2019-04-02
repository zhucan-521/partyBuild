package com.yizheng.partybuilding.service.impl;


        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import com.yizheng.commons.config.PaddingBaseField;
        import com.yizheng.commons.domain.Page;

        import com.yizheng.commons.exception.BusinessDataCheckFailException;
        import com.yizheng.partybuilding.dto.TabSpecialWorkerResultDto;
        import com.yizheng.partybuilding.entity.TabPbSpcialWorker;

        import com.yizheng.partybuilding.repository.TabPbSpcialWorkerMapper;
        import com.yizheng.partybuilding.repository.TabSysDeptMapper;
        import com.yizheng.partybuilding.repository.TabSysUserMapper;
        import com.yizheng.partybuilding.service.inf.TabSpecialWorkerService;

        import com.yizheng.partybuilding.system.entity.SysUserRole;
        import com.yizheng.partybuilding.system.mapper.SysUserRoleMapper;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TabSpecialWorkerServiceImpl implements TabSpecialWorkerService {

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

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
    public int insert(TabSpecialWorkerResultDto tabSpecialWorkerResultDto) {
       if(null==tabSysDeptMapper.selectByPrimaryKey(tabSpecialWorkerResultDto.getManageOrgId()) || null==tabSysDeptMapper.selectByPrimaryKey(tabSpecialWorkerResultDto.getDeptId())){
           throw new BusinessDataCheckFailException("根据传入的deptId或者mangerOrgId查找不到对应的组织部门");
       }
        Long userId = tabSysUserMapper.selectUserByIdCardNo(tabSpecialWorkerResultDto.getIdCardNo()).getUserId().longValue();
        tabSpecialWorkerResultDto.setUserId(userId);
        //设置专干角色为44，并且插入sys_user_role
        SysUserRole role = tabPbSpcialWorkerMapper.selectSysUserRoleByUserId(userId);
        if (null == role) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId.intValue());
            sysUserRole.setRoleId(44);
            sysUserRoleMapper.insert(sysUserRole);
        }
        int flag=0;
        if(tabPbSpcialWorkerMapper.checkSpecialWhetherTOLeave(userId).size()>0){
            throw new BusinessDataCheckFailException("该专干正处于任职状态，请离职后在任职");
        }else{
            flag=tabPbSpcialWorkerMapper.insertSelective(tabSpecialWorkerResultDto);
        }
//        if(null!=tabPbSpcialWorkerMapper.checkSpecialWorkerOndeptId(tabSpecialWorkerResultDto)){
//            //如果已存在专干表 修改专干表
//            tabSpecialWorkerResultDto.setSpecialWorkerId(tabPbSpcialWorkerMapper.selectSpecialWorkerIdByIdcard(tabSpecialWorkerResultDto));
//            flag=tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(tabSpecialWorkerResultDto);
//        }else{
//            //插入专干表
//            flag=tabPbSpcialWorkerMapper.insertSelective(tabSpecialWorkerResultDto);
//        }
        return  flag;
    }

    /**
     * 删除
     *
     * @param specialWorkerId
     */
    @PaddingBaseField
    @Override
    public int deleteBySpecialWorkerId(Long specialWorkerId) {
        TabPbSpcialWorker worker = tabPbSpcialWorkerMapper.selectByPrimaryKey(specialWorkerId);
        worker.setDelFlag("1");
        worker.setEblFlag("0");
        int retVal = tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(worker);
        //删除user_role表里面的专干信息
        if (retVal > 0) {
            tabPbSpcialWorkerMapper.deleteTabSpecialWorkerByUserId(worker.getUserId());
        }
        return retVal;
    }

    /**
     * 查询出所有的专干党员信息
     *
     * @return
     */
    @Override
    public PageInfo<TabSpecialWorkerResultDto> selectAllTabSpecialWorkerDto(Page page, TabSpecialWorkerResultDto dto) {
        PageHelper.startPage(page);
        List<TabSpecialWorkerResultDto> list = tabPbSpcialWorkerMapper.selectAllTabSpecialWorkerDto(dto);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 修改
     *
     * @param tabSpecialWorkerResultDto
     * @return
     */
    @PaddingBaseField(updateOnly = true)
    @Override
    public int updateBySpecialWorkerId(TabSpecialWorkerResultDto tabSpecialWorkerResultDto) {
        if(null==tabSysDeptMapper.selectByPrimaryKey(tabSpecialWorkerResultDto.getManageOrgId()) || null==tabSysDeptMapper.selectByPrimaryKey(tabSpecialWorkerResultDto.getDeptId())){
            throw new BusinessDataCheckFailException("根据传入的deptId或者mangerOrgId查找不到对应的组织部门");
        }
        if(tabSpecialWorkerResultDto.getSpecialWorkerId()==null){
            throw new BusinessDataCheckFailException("缺少专干主键");
        }
        return tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(tabSpecialWorkerResultDto);
    }


    /**
     * 详情查询
     * @param specialWorkerId
     * @return
     */
    @Override
    public TabSpecialWorkerResultDto selectOneById(Long specialWorkerId) {
        return tabPbSpcialWorkerMapper.selectOneById(specialWorkerId);
    }


}
