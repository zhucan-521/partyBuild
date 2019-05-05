package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegistMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegistMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositiveRegistMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PositiveRegistService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegistMemberVO;
import com.github.pagehelper.PageHelper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/11/29
 */
@Service
@Transactional
public class PositiveRegistServiceImpl implements PositiveRegistService {

    @Autowired
    private TabPbPositiveRegistMapper tabPbPositiveRegistMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 保存报到信息
     * @param positiveRegistMemberDto
     */
    @Override
    @Transactional
    @PaddingBaseField(recursive = true)
    public int addRegistMemberDTO(PositiveRegistMemberDTO positiveRegistMemberDto) {
        TabPbPositiveRegist regist = tabPbPositiveRegistMapper.findById(positiveRegistMemberDto.getUserId());
        if(regist != null){
            throw new BusinessDataNotFoundException("该党员已经报道，在不返回或者不删除该党员报道记录是不能在继续报道新组织");
        }
        //0为未报到,,1为已报到
        positiveRegistMemberDto.setRevokeTag(new Byte("1"));
        //修改党员信息
        newUser(positiveRegistMemberDto.getUserId(), true, positiveRegistMemberDto.getDeptId());
        TabPbPositiveRegist tabPbPositiveRegist=new TabPbPositiveRegist();
        BeanUtil.copyPropertiesIgnoreNull(positiveRegistMemberDto,tabPbPositiveRegist);
        int retVal = tabPbPositiveRegistMapper.addPositiveRegist(tabPbPositiveRegist);
        Long hostId=tabPbPositiveRegist.getPositiveRegistId();
        if(retVal>0){
            iTabPbAttachmentService.intelligentOperation(positiveRegistMemberDto.getAttachments(),hostId, AttachmentType.POSITIVE);
        }

        return retVal;
    }

    /**
     * 查询list数据(分页)
     */
    @Override
    public List<PositiveRegistMemberVO> selectRegistMemberVOList(PositiveRegistMemberQueryBean positiveRegistMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        var list = tabPbPositiveRegistMapper.selectListVoPage(positiveRegistMemberQueryBean);
        return list;
    }

    /**
     * 根据userId判断是否需要删除
     * @param userId
     */
    @Override
    public int delectRegistStatus(Long userId){
        TabPbPositiveRegist regist = tabPbPositiveRegistMapper.findById(userId);
        if(regist != null){
            return delete(regist.getPositiveRegistId());
        }
        return 0;
    }

    @Override
    public PositiveRegistMemberVO getReportMembersInfo(Long positiveRegistId) {
        PositiveRegistMemberVO positiveRegistMemberVO = tabPbPositiveRegistMapper.findPositiveRegistMemberById(positiveRegistId);
        positiveRegistMemberVO.setAttachments(iTabPbAttachmentService.listByHostId( positiveRegistId,AttachmentType.POSITIVE));
        return positiveRegistMemberVO;
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int changeStatus(Long positiveRegistId, Byte revokeTag) {
        int retVal = 0;
        TabPbPositiveRegist regist = tabPbPositiveRegistMapper.selectById(positiveRegistId);
        if(regist!=null){
            retVal = tabPbPositiveRegistMapper.editPositive(new TabPbPositiveRegist().setPositiveRegistId(positiveRegistId)
                    .setRevokeTag(revokeTag).setRevokeDate(new Date()));
        }
        //修改党员信息
        if(revokeTag == 2){
            newUser(regist.getUserId(), false, regist.getDeptId());
        }else {
            newUser(regist.getUserId(), true, regist.getDeptId());
        }
        return retVal;
    }

    @Override
    public int delete(Long positiveRegistId){
        TabPbPositiveRegist regist = tabPbPositiveRegistMapper.selectById(positiveRegistId);
        if(regist!=null){
            newUser(regist.getUserId(), false, regist.getDeptId());
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(regist);
            return tabPbPositiveRegistMapper.deleteRegist(regist);
        }
        return 0;
    }

    /**
     * 生成user对象，然后根据报到党员的主键id修改
     */
    private void newUser(Long userId, Boolean flag, Long deptId) {
        if(userId ==null){
            throw new BusinessDataIncompleteException("请选择报到党员");
        }
        SysDept dept = tabSysDeptMapper.selectAloneByPrimaryKey(deptId);
        if(dept!=null){
            SysUser user = new SysUser();
            user.setUserId(userId);
            if(flag){
                user.setReportOrgId(dept.getDeptId());
                user.setReportOrgName(dept.getName());
                user.setReportOrgPhone(dept.getContactNumber());
                user.setReportOrgContactor(dept.getContactor());
                //添加标签
                userTagService.addUserTag(userId.longValue(), UserTagType.REPORT);
            }else{
                //删除标签
                userTagService.delete(userId.longValue(), UserTagType.REPORT);
            }
            tabSysUserMapper.editUserRegister(user);

        }else {
            throw new BusinessDataNotFoundException("组织不存在");
        }
    }
}
