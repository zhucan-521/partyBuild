package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgClassifyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.OrgClassifyService;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分类定等service实现类
 *
 * @Author Zhang Fan
 **/
@Transactional(rollbackFor = Exception.class)
@Service("tabPbOrgClassifyService")
public class OrgClassifyServiceImpl implements OrgClassifyService {

    @Autowired
    private TabPbOrgClassifyMapper orgClassifyMapper;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Override
    public int deleteByPrimaryKey(Long orgClassifyId) {
        return orgClassifyMapper.deleteByPrimaryKey(orgClassifyId);
    }

    @PaddingBaseField
    @Override
    public int insert(TabPbOrgClassify record) {
        return orgClassifyMapper.insert(record);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbOrgClassify record) {
        int retVal = orgClassifyMapper.insertSelective(record);
        if (retVal > 0) {
            retVal += pushModification(record.getDeptId().intValue(), record.getOrgLevel(), record.getLevelDate());
        }
        return retVal;
    }

    /**
     * 推送修改
     *
     * @param deptId    组织ID
     * @param orgLevel  定等级别
     * @param levelDate 定等日期
     * @return
     */
    private int pushModification(Integer deptId, Long orgLevel, Date levelDate) {
        SysDept sysDept = deptMapper.selectByPrimaryKey(deptId.longValue());
        sysDept.setDeptId(deptId);
        sysDept.setOrgLevel(orgLevel);
        sysDept.setLevelDate(levelDate);
        return deptMapper.updateByPrimaryKey(sysDept);
    }

    @Override
    public TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId) {
        return orgClassifyMapper.selectByPrimaryKey(orgClassifyId);
    }

    @PaddingBaseField
    @Override
    public int updateByPrimaryKeySelective(TabPbOrgClassify record) {
        int retVal = orgClassifyMapper.updateByPrimaryKeySelective(record);
        retVal += pushModification(record.getDeptId().intValue(), record.getOrgLevel(), record.getLevelDate());
        return retVal;
    }

    @PaddingBaseField
    @Override
    public int updateByPrimaryKey(TabPbOrgClassify record) {
        return orgClassifyMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TabPbOrgClassify> selectByDeptId(Long deptId) {
        return orgClassifyMapper.selectByDeptId(deptId);
    }

    @Override
    public PageInfo<OrgClassifyVO> findOrgClassifyVOWithConditions(Page page, OrgRange orgRange, String orgLevel) {
        PageHelper.startPage(page);
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("orgLevel", orgLevel);
        conditions.put("delFlag", "0");
        return new PageInfo<>(orgClassifyMapper.selectWithConditions(conditions));
    }

    @Override
    public int deleteOrgClassify(Long orgClassifyId) {
        TabPbOrgClassify orgClassify = new TabPbOrgClassify();
        orgClassify.setDelFlag(CommonConstant.STATUS_DEL);
        orgClassify.setOrgClassifyId(orgClassifyId);
        return orgClassifyMapper.updateByPrimaryKeySelective(convertEntityForUpdate(orgClassify));
    }

    @Override
    public int insertOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        return orgClassifyMapper.insertSelective(convertEntityForInsert(orgClassifyDTO));
    }

    @Override
    public int updateOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        return orgClassifyMapper.updateByPrimaryKeySelective(convertEntityForUpdate(orgClassifyDTO));
    }

    @Override
    public OrgClassifyVO findOrgClassifyVOByOrgClassifyId(Long orgClassifyId) {
        return convertVo(orgClassifyMapper.selectByPrimaryKey(orgClassifyId));
    }

    /**
     * desc: 将前端传入的参数注入到分类定等对象中 返回其实体
     *
     * @param o 参数实体
     * @return TabPbOrgClassify对象
     * @author FanYanGen
     * @date 2019/4/22 16:53
     **/
    private TabPbOrgClassify convertEntityForInsert(Object o) {
        TabPbOrgClassify orgClassify = new TabPbOrgClassify();
        if (null != o) {
            BeanUtils.copyProperties(o, orgClassify);
        }
        PaddingBaseFieldUtil.paddingBaseFiled(orgClassify);
        return orgClassify;
    }

    private TabPbOrgClassify convertEntityForUpdate(Object o) {
        TabPbOrgClassify orgClassify = new TabPbOrgClassify();
        if (null != o) {
            BeanUtils.copyProperties(o, orgClassify);
        }
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(orgClassify);
        return orgClassify;
    }

    /**
     * desc: 将查询返回数据注入到VO中 返回其VO
     *
     * @param o 参数实体
     * @return TabPbAbroad对象
     * @author FanYanGen
     * @date 2019/4/22 17:23
     **/
    private OrgClassifyVO convertVo(Object o) {
        OrgClassifyVO vo = new OrgClassifyVO();
        if (null != o) {
            BeanUtils.copyProperties(o, vo);
        }
        return vo;
    }

    /**
     * desc: 判断
     *
     * @param orgClassifyDTO dto
     * @return void
     * @author FanYanGen
     * @date 2019/4/25 9:54
     **/
    private void verification(OrgClassifyDTO orgClassifyDTO) {
        if (!deptMapper.isExist(orgClassifyDTO.getDeptId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
    }

}


