package com.egovchina.partybuilding.partybuild.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositiveRegistMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbPositiveRegistService;
import com.egovchina.partybuilding.partybuild.service.TabPbUserTagService;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.system.mapper.SysDeptMapper;
import com.egovchina.partybuilding.partybuild.system.util.UserTagType;
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
public class TabPbPositiveRegistServiceImpl extends ServiceImpl<TabPbPositiveRegistMapper, TabPbPositiveRegist> implements TabPbPositiveRegistService {

    @Autowired
    private TabPbPositiveRegistMapper positiveRegistMapper;

    @Autowired
    private TabSysUserMapper sysUserMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private TabPbUserTagService tabPbUserTagService;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 保存报到信息
     * @param positiveRegistList
     */
    @Override
    @Transactional
    @PaddingBaseField(recursive = true)
    public int add(TabPbPositiveRegist positiveRegistList) {
        TabPbPositiveRegist regist = positiveRegistMapper.findById(positiveRegistList.getUserId());
        if(regist != null){
            throw new BusinessDataNotFoundException("该党员已经报道:"+regist.getSysDeptList().get(0).getName()+" 在不返回或者不删除该党员报道记录是不能在继续报道新组织");
        }
        //0为未报到,,1为已报到
        positiveRegistList.setRevokeTag(new Byte("1"));
        //修改党员信息
        newUser(positiveRegistList.getUserId().intValue(),true,positiveRegistList.getDeptId());
        int retVal = positiveRegistMapper.addPositiveRegist(positiveRegistList);
        if(retVal>0){
            iTabPbAttachmentService.intelligentOperation(positiveRegistList.getAttachments(), positiveRegistList.getPositiveRegistId(), AttachmentType.POSITIVE);
        }
        return retVal;
    }

    /**
     * 查询list数据(分页)
     * @param userName
     * @param revokeTag
     * @param data
     * @param page
     * @return
     */
    @Override
    public List<TabPbPositiveRegist> selectListPage( String userName, Byte revokeTag, String data,Long rangeDeptId,Long orgRange, Page page) {
        PageHelper.startPage(page);
        var list = positiveRegistMapper.selectListPage(new TabPbPositiveRegist().setUserName(userName)
        .setRevokeTag(revokeTag).setRegistDate(data).setRangeDeptId(rangeDeptId).setOrgRange(orgRange));
        return list;
    }

    /**
     * 根据userId判断是否需要删除
     * @param userId
     */
    @Override
    public int delectRegistStatus(Long userId){
        TabPbPositiveRegist regist = positiveRegistMapper.findById(userId);
        if(regist != null){
            return delete(regist.getPositiveRegistId());
        }
        return 0;
    }

    @Override
    public TabPbPositiveRegist editFindById(Long positiveRegistId) {
        return positiveRegistMapper.editFindById(positiveRegistId);
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int editPositive(Long positiveRegistId, Byte revokeTag) {
        int retVal = 0;
        TabPbPositiveRegist regist = positiveRegistMapper.selectById(positiveRegistId);
        if(regist!=null){
            retVal = positiveRegistMapper.editPositive(new TabPbPositiveRegist().setPositiveRegistId(positiveRegistId)
                    .setRevokeTag(revokeTag).setRevokeDate(new Date()));
        }
        //修改党员信息
        if(revokeTag == 2){
            newUser(regist.getUserId().intValue(),false,regist.getDeptId());
        }else {
            newUser(regist.getUserId().intValue(),true,regist.getDeptId());
        }
        return retVal;
    }

    @Override
    public int delete(Long positiveRegistId){
        TabPbPositiveRegist regist = positiveRegistMapper.selectById(positiveRegistId);
        if(regist!=null){
            newUser(regist.getUserId().intValue(),false,regist.getDeptId());
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(regist);
            return positiveRegistMapper.deleteRegist(regist);
        }
        return 0;
    }

    /**
     * 生成user对象，然后根据报到党员的主键id修改
     */
    private void newUser(Integer userId,Boolean flag,Long deptId){
        if(userId ==null){
            throw new BusinessDataIncompleteException("请选择报到党员");
        }
        SysDept dept = deptMapper.selectById(deptId);
        if(dept!=null){
            SysUser user = new SysUser();
            user.setUserId(userId);
            if(flag){
                user.setReportOrgId(dept.getDeptId());
                user.setReportOrgName(dept.getName());
                user.setReportOrgPhone(dept.getContactNumber());
                user.setReportOrgContactor(dept.getContactor());
                //添加标签
                tabPbUserTagService.addUserTag(userId.longValue(), UserTagType.REPORT);
            }else{
                //删除标签
                tabPbUserTagService.delete(userId.longValue(), UserTagType.REPORT);
            }
            sysUserMapper.editUserRegister(user);

        }else {
            throw new BusinessDataNotFoundException("组织不存在");
        }
    }
}
