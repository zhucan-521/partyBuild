package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.DevPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyDevAttachDTO;
import com.egovchina.partybuilding.partybuild.entity.DevPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyApplyQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDevPartyMember;
import com.egovchina.partybuilding.partybuild.entity.TabPbDevPartyMemberDate;
import com.egovchina.partybuilding.partybuild.repository.TabPbDevPartyMemberDateMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbDevPartyMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.DevPartyMemberService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.vo.CheckDevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberDateVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * desc: 党员发展步骤-服务接口实现
 * Created by FanYanGen on 2019/4/23 10:35
 */
@Transactional(rollbackFor = Exception.class)
@Service("devPartyMemberService")
public class DevPartyMemberServiceImpl implements DevPartyMemberService {

    @Autowired
    private TabPbDevPartyMemberMapper devPartyMemberMapper;

    @Autowired
    private TabPbDevPartyMemberDateMapper devPartyMemberDateMapper;

    @Autowired
    private TabSysUserMapper sysUserMapper;

    private final static long DYFZ = 58932L;

    @Override
    public DevPartyMemberVO findDevPartyMemberVOByUserId(Long userId) {
        return convertVo(devPartyMemberMapper.selectByUserId(userId));
    }

    @Override
    public DevPartyMemberVO findDevPartyMemberVOByDeptId(Long deptId) {
        return convertVo(devPartyMemberMapper.selectByPrimaryKey(deptId));
    }

    @Override
    public int updateDevStep(Long dpId, Long status) {
        return devPartyMemberMapper.updateDevStep(new DevPartyMemberDTO(dpId, status));
    }

    @Override
    public int deleteByDeptId(Long deptId) {
        return devPartyMemberMapper.deleteByDeptId(deptId);
    }

    @Override
    public PageInfo<DevPartyMemberVO> findDevPartyMemberVOByConditions(Long dpId, Long userId, Long status, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(devPartyMemberMapper.selectList(new DevPartyMemberDTO(dpId, status, userId)));
    }

    @Override
    public CheckDevPartyMemberVO checkIsParty(DevPartyMemberQueryBean devPartyMemberQueryBean) {
        final Long registerStatus = 7L;
        final Integer status = 50;
        CheckDevPartyMemberVO checkVo = new CheckDevPartyMemberVO();
        if (1 == devPartyMemberQueryBean.getAddType()) {
            SysUser user = devPartyMemberMapper.selectUserByIdCardNo(devPartyMemberQueryBean.getIdCardNo());
            if (null == user || !user.getRealname().equals(devPartyMemberQueryBean.getUserName())) {
                checkVo.setMessage("该党员不存在！");
            } else if (!(user.getRegistryStatus().equals(registerStatus))) {
                TabPbDevPartyMember devPartyMember = devPartyMemberMapper.selectByUserId(user.getUserId().longValue());
                if (null == devPartyMember) {
                    checkVo.setMessage("该党员非发展党员, 不可补录！");
                } else {
                    checkVo.setMessage("该党员已经被补录过！");
                }
            } else {
                TabPbDevPartyMember devPartyMember = devPartyMemberMapper.selectByUserId(user.getUserId().longValue());
                if (null == devPartyMember) {
                    checkVo.setMessage("该党员发展环节不存在！");
                } else if (devPartyMember.getStatus() < status) {
                    checkVo.setMessage("该党员发展环节未达到补录要求(至少需要达到第5环节第1步骤)");
                } else {
                    checkVo.setUserId(user.getUserId().longValue());
                    checkVo.setDeptId(devPartyMember.getDeptId());
                    checkVo.setResult(true);
                }
            }
            checkVo.setResult(false);
        }
        return checkVo;
    }

    @Override
    public PageInfo<DevPartyVO> findDevPartyVOByConditions(PartyApplyQueryBean partyApplyQueryBean, Page page) {
        final Long orgRange = 2L;
        if (null == partyApplyQueryBean) {
            if (!sysUserMapper.verification(UserContextHolder.getOrgId(), partyApplyQueryBean.getOrgId())) {
                partyApplyQueryBean.setOrgId(UserContextHolder.getOrgId());
                partyApplyQueryBean.setOrgRange(orgRange);
            }
        }
        PageHelper.startPage(page);
        return new PageInfo<>(devPartyMemberMapper.selectDevParty(partyApplyQueryBean));
    }

    @Override
    public List<DevPartyMemberDateVO> findDevPartyMemberDateVOByHostId(Long hostId) {
        return devPartyMemberDateMapper.selectByHostId(hostId);
    }

    @Override
    public int deleteDevDate(Long timeId) {
        return devPartyMemberDateMapper.updateByPrimaryKeySelective(new TabPbDevPartyMemberDate().setDelFlag("1").setTimeId(timeId));
    }

    @Override
    public int updateDevDate(DevPartyMemberDTO devPartyMemberDTO) {
        return devPartyMemberDateMapper.updateByPrimaryKeySelective(convertTabPbDevPartyMemberDate(devPartyMemberDTO));
    }

    @Override
    public int insertAttach(PartyDevAttachDTO partyDevAttachDTO) {
        return devPartyMemberMapper.insertSelective(convertTabPbDevPartyMember(partyDevAttachDTO));
    }

    @Override
    public DevPartyMemberVO findAttach(Long dpId, Boolean isExtend) {
        TabPbDevPartyMember devPartyMember = devPartyMemberMapper.selectByPrimaryKey(dpId);
        if (null == devPartyMember) {
            throw new BusinessDataIncompleteException("发展步骤不存在");
        }
        DevPartyMemberVO devPartyMemberVO = new DevPartyMemberVO();
        devPartyMemberVO.setAttachmentType(DYFZ);
        devPartyMemberVO.setStatus(devPartyMember.getStatus());
        devPartyMemberVO.setUserId(devPartyMember.getUserId());
        devPartyMemberVO.setDpId(devPartyMember.getDpId());
        return devPartyMemberVO;
    }

    /**
     * desc: 将前端传入的参数注入到发展党员对象中 然后返回其实体
     *
     * @param o 参数实体
     * @return TabPbDevPartyMember对象
     * @author FanYanGen
     * @date 2019/4/23 11:01
     **/
    private TabPbDevPartyMember convertTabPbDevPartyMember(Object o) {
        TabPbDevPartyMember devPartyMember = new TabPbDevPartyMember();
        if (null != o) {
            BeanUtils.copyProperties(o, devPartyMember);
        }
        PaddingBaseFieldUtil.paddingBaseFiled(devPartyMember);
        return devPartyMember;
    }

    private TabPbDevPartyMemberDate convertTabPbDevPartyMemberDate(Object o) {
        TabPbDevPartyMemberDate pbDevPartyMemberDate = new TabPbDevPartyMemberDate();
        if (null != o) {
            BeanUtils.copyProperties(o, pbDevPartyMemberDate);
        }
        PaddingBaseFieldUtil.paddingBaseFiled(pbDevPartyMemberDate);
        return pbDevPartyMemberDate;
    }

    /**
     * desc: 将查询返回数据注入到VO中 返回其VO
     *
     * @param o 参数实体
     * @return TabPbDevPartyMember对象
     * @author FanYanGen
     * @date 2019/4/22 17:23
     **/
    private DevPartyMemberVO convertVo(Object o) {
        DevPartyMemberVO vo = new DevPartyMemberVO();
        if (null != o) {
            BeanUtils.copyProperties(o, vo);
        }
        return vo;
    }

    /**
     * 通过指定步骤查询步骤中的关联的id
     *
     * @param member        TabPbDevPartyMember
     * @param hostFieldName 附件名称
     */
    private Long getHostId(DevPartyMemberVO member, String hostFieldName) {
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

}
