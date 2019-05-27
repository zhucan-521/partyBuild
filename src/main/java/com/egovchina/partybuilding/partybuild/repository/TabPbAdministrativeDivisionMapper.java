package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.AdministrativeDivisionQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbAdministrativeDivision;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:Mapper类
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:06
 */
@Repository
public interface TabPbAdministrativeDivisionMapper {

    /**
     * Description: 新增
     *
     * @param tabPbAdministrativeDivision
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:06
     */
    int insert(TabPbAdministrativeDivision tabPbAdministrativeDivision);

    /**
     * Description: 根据id修改
     *
     * @param tabPbAdministrativeDivision
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:06
     */
    int updateById(TabPbAdministrativeDivision tabPbAdministrativeDivision);

    /**
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:06
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/25 14:46:06
     */
    AdministrativeDivisionVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param administrativeDivisionQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/25 14:46:06
     */
    List<AdministrativeDivisionVO> list(AdministrativeDivisionQueryBean administrativeDivisionQueryBean);
}
