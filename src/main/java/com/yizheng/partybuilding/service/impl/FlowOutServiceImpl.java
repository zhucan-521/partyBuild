package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbFlowOutDto;
import com.yizheng.partybuilding.entity.TabPbFlowIn;
import com.yizheng.partybuilding.entity.TabPbFlowOut;
import lombok.var;
import com.yizheng.partybuilding.repository.TabPbFlowInMapper;
import com.yizheng.partybuilding.repository.TabPbFlowOutMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.FlowOutService;
import com.yizheng.partybuilding.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class  FlowOutServiceImpl implements FlowOutService {

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    TabPbFlowInMapper tabPbFlowInMapper;


    /**
     * 流出党员列表条件查询
     * @param dto
     * @return
     */
    @Override
    public PageInfo<TabPbFlowOutDto> selectActiveTabPbFlowOutDto(TabPbFlowOutDto dto , Page page) {
        PageHelper.startPage(page);
        List<TabPbFlowOutDto> list=tabPbFlowOutMapper.selectActiveTabPbFlowOutDto(dto);
        PageInfo<TabPbFlowOutDto> pageInfo=new PageInfo(list);
        return pageInfo;
    }


    /**
     * 登记流出党员信息
     * @param tabPbFlowOutDto
     * @return
     */
    @Override
    @PaddingBaseField
    public int insert(TabPbFlowOutDto tabPbFlowOutDto) {
        //修改用户表信息
        SysUser sysUser=tabSysUserMapper.selectUserByIdCardNo(tabPbFlowOutDto.getIdCardNo());
        if(null==sysUser){
            throw new BusinessDataNotFoundException("不存在该党员！");
        }
        Long userId=sysUser.getUserId().longValue();
        if(null !=tabPbFlowOutMapper.checkTabPbFlowOutExixtByUserId(userId)){
            throw new BusinessDataCheckFailException("该党员已经处于流出状态，无法继续流出！");
        }
        BeanUtils.copyProperties(tabPbFlowOutDto,sysUser);
        tabPbFlowOutDto.setUserId(userId);
        //流入党组织
        sysUser.setFlowToOrgId(tabPbFlowOutDto.getFlowOutPlace());
        sysUser.setFlowToOrgName(tabPbFlowOutMapper.selectDeptNameByDeptId(tabPbFlowOutDto.getFlowOutPlace()));
        //流出党组织
        sysUser.setFlowFromOrgId(tabPbFlowOutDto.getOrgId()) ;
        sysUser.setFlowFromOrgName(tabPbFlowOutMapper.selectDeptNameByDeptId(tabPbFlowOutDto.getOrgId()));
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //插入流出表 设置状态为待报道
        tabPbFlowOutDto.setFlowOutState(59413L);
        tabPbFlowOutMapper.insertSelective(tabPbFlowOutDto);
       //插入流入表
        var tabPbFlowIn=new TabPbFlowIn()
                   .setFlowOutId(tabPbFlowOutDto.getFlowOutId())
                   .setFlowInState(59413L)//设置状态为待报道
                   .setUserId(userId) //设置流入人
                   .setOldOrgnizeId(tabPbFlowOutDto.getOrgId()) //设置原党组织
                   .setOrgId(tabPbFlowOutDto.getFlowOutPlace()) //设置流入组织
                   .setOldPlace(tabPbFlowOutDto.getFlowToUnitName())  //设置原地
                   .setFlowInType(tabPbFlowOutDto.getFlowOutType()) //设置流出类型
                   .setFlowInRange(tabPbFlowOutDto.getOutIndustry()) //设置流动范围
                   .setFlowInReason(tabPbFlowOutDto.getFlowOutReason().toString()) //设置流动原因
                   .setOldOrgnizeCode(tabPbFlowOutDto.getFlowToOrgnizeCode());  //设置流动证
        return  tabPbFlowInMapper.insertSelective(tabPbFlowIn);
    }

    /**
     * 修改流出党员dto
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int update(TabPbFlowOutDto tabPbFlowOutDto) {
        //修改用户表
        SysUser sysUser=tabSysUserMapper.selectUserByIdCardNo(tabPbFlowOutDto.getIdCardNo());
        BeanUtils.copyProperties(tabPbFlowOutDto,sysUser);
        //流入党组织
        sysUser.setFlowToOrgId(tabPbFlowOutDto.getFlowOutPlace());
        sysUser.setFlowToOrgName(tabPbFlowOutMapper.selectDeptNameByDeptId(tabPbFlowOutDto.getFlowOutPlace()));
        //流出党组织
        sysUser.setFlowFromOrgId(tabPbFlowOutDto.getOrgId()) ;
        sysUser.setFlowFromOrgName(tabPbFlowOutMapper.selectDeptNameByDeptId(tabPbFlowOutDto.getOrgId()));
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //修改流入表
        var tabPbFlowIn=new TabPbFlowIn()
                    .setFlowOutId(tabPbFlowOutDto.getFlowOutId())
                    .setUserId(sysUser.getUserId().longValue())
                    .setOldOrgnizeId(tabPbFlowOutDto.getOrgId())
                    .setOrgId(tabPbFlowOutDto.getFlowOutPlace())
                    .setOldPlace(tabPbFlowOutDto.getFlowToUnitName())
                    .setFlowInType(tabPbFlowOutDto.getFlowOutType())
                    .setFlowInRange(tabPbFlowOutDto.getOutIndustry())
                    .setFlowInReason(tabPbFlowOutDto.getFlowOutReason().toString())
                    .setOldOrgnizeCode(tabPbFlowOutDto.getFlowToOrgnizeCode());
        tabPbFlowInMapper.updateByFlowOutIdKeySelective(tabPbFlowIn);
        //修改流出表
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutDto);
    }


    /**
     *单个查询
     * @param id
     * @return
     */
    @Override
    public TabPbFlowOutDto findOne(Long id) {
        return tabPbFlowOutMapper.findTabPbFlowOutDtoById(id);
    }


    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @PaddingBaseField
    public int delete(Long id) {
        TabPbFlowOut tabPbFlowOut=tabPbFlowOutMapper.selectByPrimaryKey(id);
        if(tabPbFlowOut.getFlowOutState()==59414){
            throw new BusinessDataCheckFailException("次党员正在流动，删除会造成数据流失！");
        }
        tabPbFlowOut.setDelFlag("1");
        tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
        SysUser sysUser=tabSysUserMapper.selectByPrimaryKey(tabPbFlowOut.getUserId());
        //用户结束流动
        sysUser.setFlowStatus(41209L);
        return tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


}
