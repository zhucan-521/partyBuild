package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.AdministrativeDivisionDTO;
import com.egovchina.partybuilding.partybuild.entity.AdministrativeDivisionQueryBean;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionTree;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:34
 */
public interface AdministrativeDivisionService {

    /**
     * Description: 新增
     *
     * @param administrativeDivisionDTO 行政区划DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    int save(AdministrativeDivisionDTO administrativeDivisionDTO);

    /**
     * Description: 根据id修改
     *
     * @param administrativeDivisionDTO 行政区划DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    int updateById(AdministrativeDivisionDTO administrativeDivisionDTO);

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 行政区划id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 行政区划id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    AdministrativeDivisionVO selectById(Long id);


    /**
     * Description: 查询列表
     *
     * @param administrativeDivisionQueryBean 查询实体
     * @param page                            分页
     * @return List<AdministrativeDivisionVO>
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    List<AdministrativeDivisionVO> selectList(AdministrativeDivisionQueryBean administrativeDivisionQueryBean, Page page);

    /**
     * 数据导入
     *
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    /**
     * 获取行政区划树列表
     *
     * @return List<AdministrativeDivisionTree>
     * @auther WuYunJie
     * @date 2019/5/27 9:51
     */
    List<AdministrativeDivisionTree> selectListByParentId();
}

