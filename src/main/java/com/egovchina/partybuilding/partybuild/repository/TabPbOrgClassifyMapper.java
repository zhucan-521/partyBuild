package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.ClassifyQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbOrgClassifyMapper {

    int deleteByPrimaryKey(Long orgClassifyId);

    int insert(TabPbOrgClassify record);

    int insertSelective(TabPbOrgClassify record);

    TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId);

    int updateByPrimaryKeySelective(TabPbOrgClassify record);

    int updateByPrimaryKey(TabPbOrgClassify record);

    List<TabPbOrgClassify> selectByDeptId(Long deptId);

    int logicDeleteById(Long orgClassifyId);

    List<OrgClassifyVO> selectWithConditions(ClassifyQueryBean classifyQueryBean);

}