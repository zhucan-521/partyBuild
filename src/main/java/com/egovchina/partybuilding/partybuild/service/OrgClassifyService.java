package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.ClassifyQueryBean;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import com.github.pagehelper.PageInfo;

/**
 * desc: 分类定等-服务接口
 * Created by FanYanGen on 2019/4/22 15:58
 */
public interface OrgClassifyService {

    /**
     * desc: 根据组合条件查询分类定等列表
     *
     * @param classifyQueryBean 查询条件
     * @param page              分页实体
     * @return PageInfo<OrgClassifyVO>
     * @author FanYanGen
     * @date 2019/4/25 9:17
     **/
    PageInfo<OrgClassifyVO> findOrgClassifyVOWithConditions(ClassifyQueryBean classifyQueryBean, Page page);

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
