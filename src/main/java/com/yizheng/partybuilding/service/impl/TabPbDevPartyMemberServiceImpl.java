package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.exception.BusinessDataInvalidException;
import com.yizheng.commons.util.IdWorker;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.*;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbDevPartyMember;
import com.yizheng.partybuilding.entity.TabPbDevPartyMemberDate;

import com.yizheng.partybuilding.repository.TabPbDevPartyMemberDateMapper;
import com.yizheng.partybuilding.repository.TabPbDevPartyMemberMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.ITabPbDevPartyMemberService;
import com.yizheng.partybuilding.system.entity.SysUser;

import lombok.var;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static com.yizheng.commons.util.AttachmentFileTypeUtil.getFileType;
import static java.util.Collections.EMPTY_LIST;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 党员发展步骤服务
 *
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/28
 */
@Service
@Transactional
public class TabPbDevPartyMemberServiceImpl implements ITabPbDevPartyMemberService {

    @Autowired
    private TabPbDevPartyMemberMapper devDao;

    @Autowired
    private TabSysUserMapper partyDao;

    @Autowired
    private TabPbDevPartyMemberDateMapper devDateDao;

    @Autowired
    private ITabPbAttachmentService attacService;

    // 党员发展 type = DYFZ id=58932L
    public final static long DYFZ = 58932L;

    @Override
    public TabPbDevPartyMember getByUserId(Long userId) {
        if (isEmpty(userId)) {
            throw new BusinessDataIncompleteException("用户id为空");
        }
        return this.devDao.selectByUserId(userId);
    }

    @Override
    public TabPbDevPartyMember getByPrimaryKey(Long primaryKey) {
        if (isEmpty(primaryKey)) {
            throw new BusinessDataIncompleteException("主键id为空");
        }
        var temp = this.devDao.selectByPrimaryKey(primaryKey);
        return temp;
    }

    /**
     * 更新发展步骤
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public int update(TabPbDevPartyMemberDto member) {
        if (isEmpty(member) || isEmpty(member.getDpId()) || isEmpty(member.getStatus())) {
            throw new BusinessDataCheckFailException("dpId, status 不可为空");
        }
        var temp = new TabPbDevPartyMember();
        copyProperties(member, temp);
        return this.devDao.updateByPrimaryKeySelective(temp);
    }

    @Override
    @Transactional
    public int delete(Long primaryKey) {
        return devDao.updateByPrimaryKeySelective(new TabPbDevPartyMember().setDpId(primaryKey).setDelFlag("1"));
    }

    @Override
    public PageInfo<TabPbDevPartyMember> list(Long dpId, Long userId, Long status, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.devDao.list(new TabPbDevPartyMember().setDpId(dpId).setUserId(userId).setStatus(status)));
    }

    @Override
    public CheckPartyDto check(Long addType, String userName, String idCardNo, Short isLost) {

        var check = new CheckPartyDto();
        if (isEmpty(userName) || isEmpty(idCardNo) || isEmpty(addType)) {
            throw new BusinessDataCheckFailException("参数错误");
        }

        switch (addType.intValue()) {
            case 1: {
                var user = this.partyDao.selectUserByIdCardNo(idCardNo);
                if (user == null || !user.getRealname().equals(userName)) {
                    check.setMessage("该党员不存在!");
                } else if (!(user.getRegistryStatus() == 7L)) {
                    var tmp = this.devDao.selectByUserId(user.getUserId().longValue());
                    if (tmp == null) {
                        check.setMessage("该党员非发展党员, 不可补录");
                    } else {
                        check.setMessage("该党员已经被补录过");
                    }
                } else {
                    var tmp = this.devDao.selectByUserId(user.getUserId().longValue());
                    if (tmp == null) {
                        check.setMessage("该党员发展环节不存在");
                    } else if (tmp.getStatus() < 50) {
                        check.setMessage("该党员发展环节未达到补录要求(至少需要第五环节第1步骤)");
                    } else {
                        check.setUserId(user.getUserId().longValue());
                        check.setDeptId(tmp.getDeptId());
                        return check.setResult(true);
                    }
                }
                return check.setResult(false);
            }
            default:
                throw new BusinessDataInvalidException("后面的功能待定");
        }
    }

    /**
     * 入党申请, 同时新增党员发展环节
     * 将党籍状态设置为7 表示发展中的状态,
     *
     * @param user
     */
    @Override
    @Transactional
    @PaddingBaseField
    public TabPbDevPartyMember partyApply(SysUser user) {
        if (isEmpty(user.getDeptId())) {
            throw new BusinessDataCheckFailException("请指定deptId");
        }

        // 发展中的状态
        final long REGISTER_STATUS = 7L;
        var tmp = this.partyDao.selectUserByIdCardNo(user.getIdCardNo());
        if (isEmpty(tmp)) {
            user.setUserId(null);
            user.setRegistryStatus(REGISTER_STATUS);
            this.partyDao.insertSelective(user);
        } else {
            user.setUserId(tmp.getUserId());
            this.partyDao.updateByPrimaryKeySelective(tmp.setRegistryStatus(REGISTER_STATUS));
        }

        var dev = this.devDao.selectByUserId(user.getUserId().longValue());
        if (dev != null) {
            return dev;
        } else {
            var temp = new TabPbDevPartyMember()
                    .setCreateTime(new Date())
                    .setStatus(11L)
                    .setUserId(user.getUserId().longValue())
                    .setCreateUserid(UserContextHolder.currentUser().getUserId().longValue())
                    .setCreateUsername(UserContextHolder.currentUser().getRealname());
            this.createHostId(temp);
            this.devDao.insertSelective(temp);
            return temp;
        }
    }

    /**
     * 分页显示发展党员的信息
     *
     * @param page   分页信息
     * @return
     */
    @Override
    public PageInfo<DevPartyUserDto> getDevPartyList(PartyApplyConditionsDto conditions, Page page) {
        PageHelper.startPage(page);
        var list = this.devDao.selectDevParty(conditions);
        return new PageInfo<>(list);
    }

    /**
     * 保存党员发展步骤时间
     *
     * @param dates 发展步骤时间对象
     * @return
     */
    @Override
    @PaddingBaseField(recursive = true)
    @Transactional
    public int saveDevDate(List<TabPbDevPartyMemberDate> dates) {
        int count = 0;
        for (int i = 0; i < dates.size(); i++) {
            var tmp = dates.get(i);
            if (isEmpty(tmp.getHostId()) || isEmpty(tmp.getDevDate())) {
                throw new BusinessDataCheckFailException("hostId 或 devDate不可为空");
            }
            count += this.devDateDao.insertSelective(tmp);
        }
        return count;
    }

    /**
     * 获取发展步骤时间id
     *
     * @param hostId 步骤id
     * @return
     */
    public List<TabPbDevPartyMemberDate> getDevDate(Long hostId) {
        return this.devDateDao.selectByHostId(hostId);
    }

    /**
     * 删除
     *
     * @param timeId
     * @return
     */
    @Override
    @Transactional
    public int deleteDevDate(Long timeId) {
        var tmp = new TabPbDevPartyMemberDate().setDelFlag("1").setTimeId(timeId);
        return this.devDateDao.updateByPrimaryKeySelective(tmp);
    }

    /**
     * 更新
     *
     * @param date
     * @return
     */
    @Override
    @Transactional
    public int updateDevDate(TabPbDevPartyMemberDate date) {
        if (isEmpty(date.getTimeId()) || isEmpty(date.getDevDate())) {
            throw new BusinessDataCheckFailException("参数错误");
        }
        return this.devDateDao.updateByPrimaryKeySelective(date.setDelFlag(null));
    }

    /**
     * 添加附件
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public void attach(PartyDevAttachDto dto) {
        if (isEmpty(dto) || isEmpty(dto.getDpId()) || isEmpty(dto.getStatus())) {
            throw new BusinessDataCheckFailException("dpId, status 不可为null");
        }

        var dev = this.devDao.selectByPrimaryKey(dto.getDpId());
        if (isEmpty(dev)) {
            throw new BusinessDataCheckFailException("发展步骤不存在");
        }

        var hostId = this.getHostId(dev, "hostId" + dto.getStatus());
        if (hostId == -1) {
            throw new BusinessDataCheckFailException("devId 错误, 不存在");
        }

        var list = canSave(dto, this.attacService.listByHostId(hostId, DYFZ));
        this.attacService.addList(list, hostId);
    }

    private List<TabPbAttachment> canSave(PartyDevAttachDto dto, List<TabPbAttachment> old) {
        // 先删掉所有的, 再保存新的,
        old.forEach(v -> this.attacService.deleteById(v.getAttachmentId()));

        if (!isEmpty(dto.getAttach()) && !dto.getAttach().isEmpty()) {
            List<TabPbAttachment> canSave = new ArrayList<>();
            dto.getAttach().forEach(v -> {
                var attach = new TabPbAttachment();
                attach.setAttachmentInstance(v);
                this.attach(attach);
                canSave.add(attach);
            });
            return canSave;
        } else if (!isEmpty(dto.getExtendAttach()) && !dto.getExtendAttach().isEmpty()) {
            dto.getExtendAttach().forEach(v -> v.setAttachmentId(null));
            dto.getExtendAttach().forEach(this::attach);
            return dto.getExtendAttach();
        }
        return EMPTY_LIST;
    }


    /**
     * 查询附件
     *
     * @param dpId
     * @return
     */
    @Override
    public PartyDevAttachListDto getAttach(Long dpId, Boolean isExtend) {
        var dev = this.devDao.selectByPrimaryKey(dpId);
        if (isEmpty(dev)) {
            throw new BusinessDataIncompleteException("发展步骤不存在");
        }
        var dto = new PartyDevAttachListDto();
        dto.setAttachmentType(DYFZ);
        dto.setStatus(dev.getStatus());
        dto.setUserId(dev.getUserId());
        dto.setDpId(dev.getDpId());
        BeanWrapper dtoWrapper = new BeanWrapperImpl(dto);
        var source = dev.getClass().getDeclaredFields();
        for (var f : source) {
            // 查询这个hostxx下的附件列表
            if (f.getName().matches("hostId\\d{2}")) {
                var hostId = getHostId(dev, f.getName());
                var list = this.attacService.listByHostId(hostId, DYFZ);

                // 创建步骤项
                var item = this.createAttarchItem(list, hostId, isExtend);
                dtoWrapper.setPropertyValue(f.getName(), item);
            }
        }
        return dto;
    }

    @Override
    @Transactional
    public int updateUserInfo(SysUser user) {
        if (isEmpty(user.getUserId())) {
            throw new BusinessDataCheckFailException("请指定userId");
        }
        return this.partyDao.updateByPrimaryKeySelective(user);
    }


    /**
     * 生成附件id
     *
     * @param member
     */
    private void createHostId(TabPbDevPartyMember member) {
        member.setHostId11(IdWorker.getLongId())
                .setHostId12(IdWorker.getLongId())

                .setHostId21(IdWorker.getLongId())
                .setHostId22(IdWorker.getLongId())
                .setHostId23(IdWorker.getLongId())
                .setHostId24(IdWorker.getLongId())

                .setHostId31(IdWorker.getLongId())
                .setHostId32(IdWorker.getLongId())
                .setHostId33(IdWorker.getLongId())
                .setHostId34(IdWorker.getLongId())
                .setHostId35(IdWorker.getLongId())

                .setHostId41(IdWorker.getLongId())
                .setHostId42(IdWorker.getLongId())
                .setHostId43(IdWorker.getLongId())
                .setHostId44(IdWorker.getLongId())
                .setHostId45(IdWorker.getLongId())
                .setHostId46(IdWorker.getLongId())
                .setHostId47(IdWorker.getLongId())

                .setHostId51(IdWorker.getLongId())
                .setHostId52(IdWorker.getLongId())
                .setHostId53(IdWorker.getLongId())
                .setHostId54(IdWorker.getLongId())
                .setHostId55(IdWorker.getLongId())
                .setHostId56(IdWorker.getLongId())
                .setHostId57(IdWorker.getLongId());

    }

    @PaddingBaseField
    private TabPbAttachment attach(TabPbAttachment attachment) {
        attachment.setAttachmentType(DYFZ);
        attachment.setAttachmentFileType(getFileType(attachment.getAttachmentInstance()));
        return attachment;
    }

    /**
     * 通过指定步骤查询步骤中的关联的id
     *
     * @param member
     * @param hostFieldName
     * @return
     */
    private Long getHostId(TabPbDevPartyMember member, String hostFieldName) {
        try {
            var fields = member.getClass().getDeclaredFields();
            for (var field : fields) {
                if (field.getName().equals(hostFieldName)) {
                    field.setAccessible(true);
                    return (Long) field.get(member);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 创建附件项
     *
     * @param attachments
     * @param hostId
     * @return
     */
    private Map<String, Object> createAttarchItem(List<TabPbAttachment> attachments, Long hostId, Boolean isExtend) {
        var map = new HashMap<String, Object>(2);
        map.put("hostId", hostId);
        if (!(isEmpty(isExtend) || isExtend)) {
            var list = new ArrayList<Object>(attachments.size());
            map.put("attach", list);
            attachments.forEach(v -> {
                var attach = new HashMap<String, Object>(2);
                attach.put("attachmentInstance", v.getAttachmentInstance());
                attach.put("attachmentId", v.getAttachmentId());
                list.add(attach);
            });
        } else {
            map.put("attach", attachments);
        }
        return map;
    }

}
