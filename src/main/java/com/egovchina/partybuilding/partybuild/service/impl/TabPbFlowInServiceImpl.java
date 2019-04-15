package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabPbFlowInDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbFlowOutDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbFlowInService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * 流入党员实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TabPbFlowInServiceImpl implements TabPbFlowInService {

    @Autowired
    private TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbFlowOutMapper tabPbFlowOutMapper;



    /**
     * 流入党员列表查询
     * @param params
     * @return
     */
    @Override
    public PageInfo<TabPbFlowInDto> select(Map<String, Object> params, Page page) {
        PageHelper.startPage(page);
        var obj = params.get("rangeDeptId");
        if(isEmpty(obj) || "0".equals(obj)){
            params.put("rangeDeptId", UserContextHolder.currentUser().getDeptId());
        }
        List<TabPbFlowInDto> tabPbFlowInDtos = tabPbFlowInMapper.selectListByFlowInDto(params);
        return new PageInfo<>(tabPbFlowInDtos);
    }

    /**
     * 根据流入id删除流入党员信息
     * @param flowInId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long flowInId) {
        TabPbFlowIn tabPbFlowIn= tabPbFlowInMapper.selectByPrimaryKey(flowInId);
        if(tabPbFlowIn.getFlowInState()==59415||tabPbFlowIn.getFlowInState()==59413){
            throw new BusinessDataCheckFailException("此党员正在流动，删除会造成数据流失！");
        }
        tabPbFlowIn.setDelFlag("1");
        tabPbFlowInMapper.updateByFlowOutIdKeySelective(tabPbFlowIn);
        SysUser sysUser=tabSysUserMapper.selectByPrimaryKey(tabPbFlowIn.getUserId());
        //用户结束流动
        sysUser.setFlowStatus(41209L);
        return  tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 录入
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int insert(TabPbFlowInDto tabPbFlowInDto) {
        //流入表
        TabPbFlowIn tabPbFlowIn=tabPbFlowInMapper.selectByPrimaryKey(tabPbFlowInDto.getFlowInId());
        if(tabPbFlowIn.getFlowInState()==59415L){
            throw new BusinessDataCheckFailException("该党员已经录入！");
        }
        BeanUtils.copyProperties(tabPbFlowInDto,tabPbFlowIn);
        //设置状态已流入
        tabPbFlowIn.setFlowInState(59415L);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut=new TabPbFlowOut();
        tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
        //设置状态已流出
        tabPbFlowOut.setFlowOutState(59414L);
        //修改流入流出党组织联系人和电话  user表
        Long userId=tabSysUserMapper.SelectUserIdByIDcard(tabPbFlowInDto.getIdCardNo());
        SysUser sysUser=new SysUser();
        sysUser.setUserId(userId.intValue());
        //设置状态流动
        sysUser.setFlowStatus(41207L);
        BeanUtils.copyProperties(tabPbFlowInDto,sysUser);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 修改
     * @param tabPbFlowInDto
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateTabPbFlowInDto(TabPbFlowInDto tabPbFlowInDto) {
        //不让修改流入党组织
        tabPbFlowInDto.setOrgId(null);
        tabPbFlowInDto.setFlowToOrgName(null);
        Long userId=tabSysUserMapper.SelectUserIdByIDcard(tabPbFlowInDto.getIdCardNo());
        SysUser sysUser=new SysUser();
        sysUser.setUserId(userId.intValue());
        BeanUtil.copyPropertiesIgnoreNull(tabPbFlowInDto, sysUser);
        TabPbFlowIn tabPbFlowIn=tabPbFlowInMapper.selectByPrimaryKey(tabPbFlowInDto.getFlowInId());
        TabPbFlowOutDto tabPbFlowOutDto=new TabPbFlowOutDto();
        BeanUtil.copyPropertiesIgnoreNull(tabPbFlowInDto,tabPbFlowOutDto);
        tabPbFlowOutDto.setFlowOutId(tabPbFlowIn.getFlowOutId());
        tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutDto);
        //修改流入流出党组织联系人和电话
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        return tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowInDto);
    }


    /**
     * 結束流動
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int updateEndFlow(TabPbFlowInDto tabPbFlowInDto) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(tabPbFlowInDto.getUserId());
        BeanUtils.copyProperties(tabPbFlowInDto,sysUser);
        //结束流动
        sysUser.setFlowStatus(41209L);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //返回
        tabPbFlowInDto.setFlowInState(59416L);
        tabPbFlowInDto.setDelFlag("1");
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowInDto);
        TabPbFlowOut tabPbFlowOut=tabPbFlowOutMapper.selectByPrimaryKey(tabPbFlowInDto.getFlowOutId());
        //返回
        tabPbFlowOut.setFlowOutState(59416L);
        //设置返回日期
        tabPbFlowOut.setReturnDate(tabPbFlowInDto.getReturnDate());

        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 根据流入id获取流入党员DTO信息
     * @param flowInId
     * @return
     */
    @Override
    public TabPbFlowInDto selectFlowInById(Long flowInId) {
        return tabPbFlowInMapper.selectFlowInByFlowId(flowInId);
    }


}