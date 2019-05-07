package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.GoAbroadDTO;
import com.egovchina.partybuilding.partybuild.dto.ReturnAbroadDTO;
import com.egovchina.partybuilding.partybuild.entity.AbroadQueryBean;
import com.egovchina.partybuilding.partybuild.vo.AbroadVO;
import com.egovchina.partybuilding.partybuild.vo.BackAbroadDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.GoAbroadDetailsVO;
import com.github.pagehelper.PageInfo;

/**
 * desc: 出国出境-服务接口
 * Created by FanYanGen on 2019/4/20 15:57
 */
public interface AbroadService {

    /**
     * desc: 新增出国记录
     *
     * @param goAbroadDTO 回国信息dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 17:00
     **/
    int insertGoAbroad(GoAbroadDTO goAbroadDTO);

    /**
     * desc: 根据条件分页查询出国出境记录
     *
     * @param abroadQueryBean 条件查询实体
     * @param page            分页实体
     * @return list
     * @author FanYanGen
     * @date 2019/4/22 17:01
     **/
    PageInfo<AbroadVO> findAbroadVOWithConditions(AbroadQueryBean abroadQueryBean, Page page);

    /**
     * desc: 删除出国出境信息
     *
     * @param abroadId 出国出境ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 17:02
     **/
    int deleteAbroad(Long abroadId);

    /**
     * desc: 更新出国记录
     *
     * @param goAbroadDTO 出国信息dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 17:03
     **/
    int updateGoAbroad(GoAbroadDTO goAbroadDTO);

    /**
     * desc: 更新（添加）回国记录
     *
     * @param returnAbroadDTO 回国信息dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 17:03
     **/
    int updateReturnAbroad(ReturnAbroadDTO returnAbroadDTO);

    /**
     * desc: 根据主键ID查询出国详情
     *
     * @param abroadId 出国出境ID
     * @return AbroadVO
     * @author FanYanGen
     * @date 2019/4/24 15:36
     **/
    GoAbroadDetailsVO findGoAbroadDetailsVOByAbroadId(Long abroadId);

    /**
     * desc: 根据主键ID查询回国详情
     *
     * @param abroadId 出国出境ID
     * @return AbroadVO
     * @author FanYanGen
     * @date 2019/4/24 15:36
     **/
    BackAbroadDetailsVO findBackAbroadDetailsVOByAbroadId(Long abroadId);

}
