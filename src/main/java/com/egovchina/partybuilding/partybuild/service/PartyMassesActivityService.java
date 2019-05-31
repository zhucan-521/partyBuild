package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityDTO;
import com.egovchina.partybuilding.partybuild.dto.SignInDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityVO;
import com.egovchina.partybuilding.partybuild.vo.SignInToListVO;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
public interface PartyMassesActivityService {

    /**
     * Description: 新增
     *
     * @param partyMassesActivityDTO 党群活动DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int save(PartyMassesActivityDTO partyMassesActivityDTO);

    /**
     * Description: 根据id修改
     *
     * @param partyMassesActivityDTO 党群活动DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int updateById(PartyMassesActivityDTO partyMassesActivityDTO);

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群活动id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 党群活动id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    PartyMassesActivityVO selectById(Long id);


    /**
     * Description: 查询列表
     *
     * @param partyMassesActivityQueryBean 党群活动查询实体
     * @param page                         分页
     * @return 结果集
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    List<PartyMassesActivityVO> selectList(PartyMassesActivityQueryBean partyMassesActivityQueryBean, Page page);

    /**
     * 签到接口加人/二维码签到
     *
     * @param partyMassesActivityId 党群活动id
     * @param idCardNo              身份证
     * @return int
     */
    int insertSignInWithPeople(Long partyMassesActivityId, String idCardNo);

    /**
     * 党群签到变更
     *
     * @param signInDTO 党群签到DTO
     * @return int
     * @auther WuYunJie
     * @date 2019/5/22 15:47
     */
    int updateSignIn(SignInDTO signInDTO);

    /**
     * 查询签到情况列表
     *
     * @param partyMassesActivityId 党群活动id
     * @param signType              签到状态
     * @param page                  分页
     * @param realName              名字
     * @return SignInToListVO
     */
    List<SignInToListVO> selectSignInVOListByCondition(Long partyMassesActivityId, Long signType, Page page, String realName);

    /**
     * 查询报名情况列表
     *
     * @param partyMassesActivityId 党群活动id
     * @param signType              报名状态
     * @param page                  分页
     * @param realName              名字
     * @return SignInToListVO
     */
    List<SignInToListVO> selectSignUpVOListByCondition(Long partyMassesActivityId, Long signType, Page page, String realName);
}

