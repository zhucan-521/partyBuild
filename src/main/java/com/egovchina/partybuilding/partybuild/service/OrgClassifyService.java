package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * desc: 分类定等-服务接口
 * Created by FanYanGen on 2019/4/22 15:58
 */
public interface OrgClassifyService {

    int deleteByPrimaryKey(Long orgClassifyId);

    int insert(TabPbOrgClassify record);

    int insertSelective(TabPbOrgClassify record);

    TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId);

    int updateByPrimaryKeySelective(TabPbOrgClassify record);

    int updateByPrimaryKey(TabPbOrgClassify record);

    /**
     * 根据组织ID查询定等数据
     *
     * @param deptId 组织ID
     * @return
     */
    List<TabPbOrgClassify> selectByDeptId(Long deptId);

    /**
     * desc: 根据组合条件查询分类定等列表
     *
     * @param page     分页实体
     * @param orgRange 组织条件集合
     * @param orgLevel 定等级别
     * @return PageInfo<OrgClassifyVO>
     * @author FanYanGen
     * @date 2019/4/25 9:17
     **/
    PageInfo<OrgClassifyVO> findOrgClassifyVOWithConditions(Page page, OrgRange orgRange, String orgLevel);

    /**
     * desc: 逻辑删除分类定等级
     *
     * @param orgClassifyId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/25 9:27
     **/
    int deleteOrgClassify(Long orgClassifyId);

    /**
     * desc: 新增分类定等
     *
     * @param orgClassifyDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/25 9:28
     **/
    int insertOrgClassify(OrgClassifyDTO orgClassifyDTO);

    /**
     * desc: 修改分类定等
     *
     * @param orgClassifyDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/25 9:29
     **/
    int updateOrgClassify(OrgClassifyDTO orgClassifyDTO);

    /**
     * desc: 查询详情
     *
     * @param orgClassifyId 主键ID
     * @return OrgClassifyVO
     * @author FanYanGen
     * @date 2019/4/25 9:29
     **/
    OrgClassifyVO findOrgClassifyVOByOrgClassifyId(Long orgClassifyId);

}
