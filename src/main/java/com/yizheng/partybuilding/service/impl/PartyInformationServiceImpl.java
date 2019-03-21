package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.OrganizationPeopleStatistics;
import com.yizheng.partybuilding.entity.TabPbMemberReduceList;
import com.yizheng.partybuilding.entity.TabPbUserTag;

import com.yizheng.partybuilding.repository.*;
import com.yizheng.partybuilding.service.inf.ITabPbUserTagService;
import com.yizheng.partybuilding.service.inf.PartyInformationService;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import com.yizheng.partybuilding.system.util.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


/**
 * @author zhucan
 */
@Service
public class PartyInformationServiceImpl implements PartyInformationService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    ITabPbUserTagService tabPbUserTagService;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Autowired
    private SysUserCountDtoMapper countDtoMapper;

    /**
     * 补录党员基本信息
     *
     * @param sysUser
     * @return ReturnEntity
     */
    @Override
    @PaddingBaseField
    public int insert(SysUser sysUser) {
        if (null != tabSysUserMapper.selectUserByIdCardNo(sysUser.getIdCardNo())) {
            throw new BusinessDataCheckFailException("单位已经存在这人，无需补录！");
        }
        sysUser.setIdentityType(59423L);
        tabSysUserMapper.insertSelective(sysUser);
        return sysUser.getUserId();
    }

    /**
     * 修改党员基本信息
     *
     * @param sysUser
     * @return
     */
    @Override
    public int updateSysUser(SysUser sysUser) {
        if (null == sysUser.getUserId()) {
            throw new BusinessDataIncompleteException("请带上userId ");
        }
        List<TabPbUserTag> tags = sysUser.getTabPbUserTags();
        tabPbUserTagService.updateUserTagByTagType(tags);
        return tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    public static final int COMPLETEBASE = 4;

    /**
     * 根据userId查找党员
     *
     * @param userId
     * @return
     */
    @Override
    public SysUseDto getSysUserById(Integer userId) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(userId.longValue());
        SysUseDto dto = new SysUseDto();
        BeanUtils.copyProperties(sysUser, dto);
        dto.setDeptName(tabPbFlowOutMapper.selectDeptNameByDeptId(dto.getDeptId().longValue()));
        dto.setFilesManageUnitIdName(tabPbFlowOutMapper.selectUntilName(dto.getFilesManageUnitId()));
        dto.setCommunityName(tabPbFlowOutMapper.CommunityNameById(dto.getCommunityAddr()));
        return dto;
    }

    /**
     * 根据社区名字模糊查找社区
     *
     * @param
     * @return
     */
    @Override
    public List<CommunityDto> selectCommunityDto(CommunityDto dto) {
        return tabPbFlowOutMapper.selectCommuntiyDto(dto);
    }

    @Override
    public PageInfo<SysUser> historyPartyPage(Map<String, Object> params) {
        TabPbMemberReduceList reduceList = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbMemberReduceList.class);
        if(params.containsKey("page") && params.containsKey("limit")){
            PageHelper.startPage(Integer.parseInt((String)params.get("page")), Integer.parseInt((String)params.get("limit")));
        }
        return new PageInfo<>(reduceListMapper.historyPartyPage(reduceList));
    }

    /**
     * 任务面板 党员统计
     * @param deptId
     * @return
     */
    @Cacheable(value = "COUNT::PANEL::PEOPLE", key = "#deptId")
    @Override
    public SysUserCountDto getTaskPartyCount(Long deptId) {
        SysUserCountDto countDto = new SysUserCountDto();
        List<BaseDataAnalysisDto<Long>> dataList = countDtoMapper.selectCountUser(deptId);
        if(dataList.size() > 0 && dataList.size()<7){
            countDto.setDirectPartyUser(dataList.get(0).getDatas());
            countDto.setFlowUser(dataList.get(1).getDatas());
            countDto.setHardshipUser(dataList.get(2).getDatas());
            countDto.setLeadTeamMemberUser(dataList.get(3).getDatas());
            countDto.setPositiveUser(dataList.get(4).getDatas());
            countDto.setRetiredUser(dataList.get(5).getDatas());
            countDto.setDeptId(deptId);
        }
        return countDto;
    }

    @Cacheable(value = "COUNT::PARTY_LIST::PEOPLE_FROM_ORG", key = "#orgId")
    @Override
    public OrganizationPeopleStatistics selectPeopleCountingByOrgId(Long orgId) {
        return countDtoMapper.selectPeopleCountingByOrgId(orgId);
    }

    @Override
    public List<SysUser> partyIdentityVerification(String username, String idCardNo, String phone, Page page) {
        PageHelper.startPage(page);
        return tabSysUserMapper.partyIdentityVerification(username, idCardNo, phone);
    }

    /**
     * 查找党员基本信息
     * <p>
     * 2018年12月12日 添加党员过滤条件, 只查询 1刚入党、2转正的党员, @author chenshanlu
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<SysUser> selectPage(Map<String, Object> params) {
        params.entrySet().removeIf(entry -> entry.getValue() instanceof  String && StringUtils.isEmpty(((String) entry.getValue())));
//        if(!params.containsKey("page") && !params.containsKey("limit") && !params.containsKey("username") &&
//                !params.containsKey("idCardNo") &&!params.containsKey("deptId")){
//            return null;
//        }
        //PS:优化查询重写OrgRange
        if (params.containsKey("deptId")) {
            String deptId = String.valueOf(params.get("deptId"));
            String orgRange = String.valueOf(params.get("orgRange")); // 2 包含所有下级
            if(!"14307".equals(deptId)){
                //判断是否属于此节点
                if(!tabSysUserMapper.verification(UserContextHolder.getOrgId(),Long.parseLong(deptId))){
                    //不属于改变deptId的值
                    params.put("deptId",UserContextHolder.getOrgId());
                }
                params.put("orgRange", "2");
            }else if ("14307".equals(deptId) && "2".equals(orgRange)) {
                params.put("orgRange", "0");
            }
        }else{
            params.put("deptId",UserContextHolder.getOrgId());
            if(14307 == UserContextHolder.getOrgId()){
                params.put("orgRange", "0");
            }else{
                params.put("orgRange", "2");
            }
        }
        //姓名、用户名
        params.put("delFlag", CommonConstant.STATUS_NORMAL);
        if (params.containsKey("username")) {
            params.put("username",params.get("username"));
        }
        //年龄
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(params.containsKey("ageBegin")){
           LocalDate date = LocalDate.now().minusYears(Long.valueOf(String.valueOf(params.get("ageBegin"))));
           params.put("ageBegin",date.format(dateTimeFormatter));
        }
        if(params.containsKey("ageEnd")){
            LocalDate date = LocalDate.now().minusYears(Long.valueOf(String.valueOf(params.get("ageEnd"))));
            params.put("ageEnd",date.format(dateTimeFormatter));
        }
        //学历
        if(params.containsKey("education")){
            String education = params.get("education").toString();
            params.put("education",education.split(","));
        }
        //婚姻状况
        if(params.containsKey("maritalStatus")){
            params.put("maritalStatus",params.get("maritalStatus").toString().split(","));
        }
        //名族
        if(params.containsKey("nation")){
            params.put("nation",params.get("nation").toString().split(","));
        }
        //人员类别
        if(params.containsKey("identityType")){
            params.put("status",params.get("identityType").toString().split(","));
        }
        //单位类型
        if(params.containsKey("unitType")){
            params.put("unitType",params.get("unitType").toString().split(","));
        }
        if(params.containsKey("registryStatus")){
            String rs = params.get("registryStatus").toString();
            if(rs.contains("223")){
                rs += ",224";
            }
            params.put("status",rs.split(","));
        }else if(!params.containsKey("identityType")){
            params.put("status",new String[] {"223","224"});
        }
        //不存在分页key，就进行填充
        if(!params.containsKey("page") || !params.containsKey("limit")){
            params.put("page","1");
            params.put("limit","10");
        }
        if (StringUtils.isNotEmpty((String) params.get("page")) && StringUtils.isNotEmpty((String) params.get("limit"))) {
            PageHelper.startPage(Integer.parseInt((String) params.get("page")), Integer.parseInt((String) params.get("limit")));
            params.remove("page");
            params.remove("limit");
        }
        params.entrySet().removeIf(entry -> ObjectUtils.isEmpty(entry.getValue()));
        List<SysUser> sysUsers = tabSysUserMapper.selectPageByMap(params);
        calculationComplete(sysUsers);
        return new PageInfo<>(sysUsers);
    }

    public void calculationComplete(List<SysUser> userList){
        userList.forEach(user ->{
            int tool = 100;
            if(StringUtils.isEmpty(user.getUsername())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getGender())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getIdCardNo())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getEducation())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getNation())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getIdentityType())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getDeptId())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getJoinTime())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getRegularTime())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getJobPosition())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getCommunityAddr())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getPhone())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getFamilyAddress())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getRegistryStatus())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getAncestorPlace())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getIsTaiwaner())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getBirthday())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getMaritalStatus())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getWorkDate())){
                tool -= COMPLETEBASE;
            }
            /*if(ObjectUtils.isEmpty(user.getOptionsUnit())){
                tool -= COMPLETEBASE;
            }*/
            if(ObjectUtils.isEmpty(user.getFrontLine())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getStratum())){
                tool -= COMPLETEBASE;
            }
            if(ObjectUtils.isEmpty(user.getMigrant())){
                tool -= COMPLETEBASE;
            }
            user.setComplete(tool);
        });
    }
}
