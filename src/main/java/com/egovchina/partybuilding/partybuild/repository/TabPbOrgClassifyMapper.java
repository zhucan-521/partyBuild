package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.ClassifyQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    /**
     * desc: 检查本次定等日期是否与上次定等日期相隔一年
     *
     * @param orgClassifyId 主键ID
     * @param deptId        组织ID
     * @param levelDate     定等日期
     * @return boolean
     * @author FanYanGen
     * @date 2019/5/7 16:01
     **/
    boolean checkIsMoreThanAYear(Long orgClassifyId, Long deptId, Date levelDate);

}