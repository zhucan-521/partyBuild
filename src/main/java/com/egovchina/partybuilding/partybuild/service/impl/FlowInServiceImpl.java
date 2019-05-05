package com.egovchina.partybuilding.partybuild.service.impl;
import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.FlowInMemberDTO;
import com.egovchina.partybuilding.partybuild.service.FlowInService;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 流入党员实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowInServiceImpl implements FlowInService {

    @Autowired
    private TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbFlowOutMapper tabPbFlowOutMapper;

    /**
     * 流入党员列表查询
     * @param
     * @return
     */
    @Override
    public PageInfo<FlowInMemberVO> getFlowInMemberList(FlowInMemberQueryBean flowInMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<FlowInMemberVO> tabPbFlowInDtos = tabPbFlowInMapper.selectListByFlowInVo(flowInMemberQueryBean);
        PageInfo<FlowInMemberVO> pageInfo=new PageInfo(tabPbFlowInDtos);
        return pageInfo;
    }

    /**
     * 根据流入id删除流入党员信息
     * @param flowInId
     * @return
     */
    @Override
    public int deleteFlowInMember(Long flowInId) {
        TabPbFlowIn tabPbFlowIn=tabPbFlowInMapper.selectByPrimaryKey(flowInId);
        int flag=0;
        if(tabPbFlowIn.getFlowInState()==59413){
            tabPbFlowIn.setDelFlag("1");
            tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
            SysUser sysUser=tabSysUserMapper.selectByPrimaryKey(tabPbFlowIn.getUserId());
            //用户结束流动
            sysUser.setFlowStatus(41209L);
            flag=tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
            if(flag>0){
                TabPbFlowOut tabPbFlowOut=new TabPbFlowOut();
                tabPbFlowIn.setDelFlag("1");
                tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbFlowOut);
            }
        }else{
            throw new BusinessDataCheckFailException("只有带报道的流动党员才可以删除！");
        }
        return flag;
    }

    /**
     * 录入
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int acceptFlowInMember(FlowInMemberDTO flowInMemberDto) {
        //流入表
        TabPbFlowIn tabPbFlowIn=tabPbFlowInMapper.selectByPrimaryKey(flowInMemberDto.getFlowInId());
        if(tabPbFlowIn.getFlowInState()==59415L){
            throw new BusinessDataCheckFailException("该党员已经录入！");
        }
        BeanUtils.copyProperties(flowInMemberDto,tabPbFlowIn);
        //设置状态已流入
        tabPbFlowIn.setFlowInState(59415L);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut=new TabPbFlowOut();
        tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
        //设置状态已流出
        tabPbFlowOut.setFlowOutState(59414L);
        //修改流入流出党组织联系人和电话  user表
        Long userId=tabSysUserMapper.SelectUserIdByIDcard(flowInMemberDto.getIdCardNo());
        SysUser sysUser=new SysUser();
        sysUser.setUserId(userId.intValue());
        //设置状态流动
        sysUser.setFlowStatus(41207L);
        BeanUtils.copyProperties(flowInMemberDto,sysUser);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 修改
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateFlowInDto(FlowInMemberDTO flowInMemberDto) {
        //不让修改流入党组织
        flowInMemberDto.setOrgId(null);
        flowInMemberDto.setFlowToOrgName(null);
        Long userId=tabSysUserMapper.SelectUserIdByIDcard(flowInMemberDto.getIdCardNo());
        SysUser sysUser=new SysUser();
        sysUser.setUserId(userId.intValue());
        BeanUtil.copyPropertiesIgnoreNull(flowInMemberDto, sysUser);
        TabPbFlowIn tabPbFlowIn=tabPbFlowInMapper.selectByPrimaryKey(flowInMemberDto.getFlowInId());
        TabPbFlowOut tabPbFlowOutDto=new TabPbFlowOut();
        BeanUtil.copyPropertiesIgnoreNull(flowInMemberDto,tabPbFlowOutDto);
        tabPbFlowOutDto.setFlowOutId(tabPbFlowIn.getFlowOutId());
        tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutDto);
        //修改流入流出党组织联系人和电话
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbFlowIn tabPbFlowInUpdate=new TabPbFlowIn();
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowInUpdate);
        BeanUtil.copyPropertiesIgnoreNull(flowInMemberDto,tabPbFlowInUpdate);
        return tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowInUpdate);
    }

    /**
     * 結束流動
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int returnFlowInMember(FlowInMemberDTO flowInMemberDto) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(flowInMemberDto.getUserId());
        BeanUtils.copyProperties(flowInMemberDto,sysUser);
        //结束流动
        sysUser.setFlowStatus(41209L);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //返回
        flowInMemberDto.setFlowInState(59416L);
        TabPbFlowIn tabPbFlowIn=new TabPbFlowIn();
        BeanUtil.copyPropertiesIgnoreNull(flowInMemberDto,tabPbFlowIn);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut=tabPbFlowOutMapper.selectByPrimaryKey(flowInMemberDto.getFlowOutId());
        //返回
        tabPbFlowOut.setFlowOutState(59416L);
        //设置返回日期
        tabPbFlowOut.setReturnDate(flowInMemberDto.getReturnDate());
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 根据流入id获取流入党员DTO信息
     * @param flowInId
     * @return
     */
    @Override
    public FlowInMemberVO getFlowInMeberVoById(Long flowInId) {
        return tabPbFlowInMapper.selectFlowInVoByFlowId(flowInId);
    }


}
