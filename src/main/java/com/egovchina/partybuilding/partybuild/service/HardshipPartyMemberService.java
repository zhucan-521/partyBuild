package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * desc: 困难党员-服务接口
 * Created by FanYanGen on 2019/4/20 17:21
 */
public interface HardshipPartyMemberService {

    /**
     * desc: 添加党员信息
     *
     * @param hardshipPartyMemberDTO 困难党员数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 21:08
     **/
    int insertHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO);

    /**
     * desc: 根据困难ID逻辑删除
     *
     * @param hardshipId 困难ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 21:01
     **/
    int deleteByHardshipId(Long hardshipId);

    /**
     * desc:  根据困难ID查询详情
     *
     * @param hardshipId 困难ID
     * @return TabPbHardship
     * @author FanYanGen
     * @date 2019/4/22 20:59
     **/
    HardshipPartyVO findHardshipPartyVOByHardshipId(Long hardshipId);

    /**
     * desc: 更新困难党员信息
     *
     * @param hardshipPartyMemberDTO 困难党员数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/22 20:38
     **/
    int updateHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO);

    /**
     * desc: 根据userId查询详情
     *
     * @param userId 用户ID
     * @return TabPbHardship 对象
     * @author FanYanGen
     * @date 2019/4/22 17:23
     **/
    List<HardshipPartyVO> findHardshipPartyVOByUserId(Long userId);

    /**
     * desc: 根据条件查询困难党员列表
     *
     * @param hardshipQueryBean 查询条件实体
     * @param page              分页实体
     * @return 分页信息
     * @author FanYanGen
     * @date 2019/4/22 20:34
     **/
    PageInfo<HardshipPartyVO> findHardshipPartyVOWithConditions(HardshipQueryBean hardshipQueryBean, Page page);

    /**
     * desc: 获取困难党员慰问情况列表
     *
     * @param userId
     * @return HardshipPartyVO集合
     * @author zhucan
     * @date 2019/5/14 14:35
     */
    List<HardshipPartyVO> findHardshipPartyConsolation(Long userId);

}
