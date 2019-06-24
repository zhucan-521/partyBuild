package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.AdministrativeDivisionDTO;
import com.egovchina.partybuilding.partybuild.entity.AdministrativeDivisionQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbAdministrativeDivision;
import com.egovchina.partybuilding.partybuild.repository.TabPbAdministrativeDivisionMapper;
import com.egovchina.partybuilding.partybuild.service.AdministrativeDivisionService;
import com.egovchina.partybuilding.partybuild.util.TreeUtil;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionTree;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionVO;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:34
 */
@Service
public class AdministrativeDivisionServiceImpl implements AdministrativeDivisionService {

    @Autowired
    private TabPbAdministrativeDivisionMapper tabPbAdministrativeDivisionMapper;

    /**
     * Description: 新增
     *
     * @param administrativeDivisionDTO 行政区划DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    @Override
    public int save(AdministrativeDivisionDTO administrativeDivisionDTO) {
        TabPbAdministrativeDivision tabPbAdministrativeDivision =
                generateTargetCopyPropertiesAndPaddingBaseField(administrativeDivisionDTO, TabPbAdministrativeDivision.class, false);
        paddingBaseFiled(tabPbAdministrativeDivision);
        return tabPbAdministrativeDivisionMapper.insert(tabPbAdministrativeDivision);
    }

    /**
     * Description: 根据id修改
     *
     * @param administrativeDivisionDTO 行政区划DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    @Override
    public int updateById(AdministrativeDivisionDTO administrativeDivisionDTO) {
        TabPbAdministrativeDivision tabPbAdministrativeDivision =
                generateTargetCopyPropertiesAndPaddingBaseField(administrativeDivisionDTO, TabPbAdministrativeDivision.class, true);
        paddingUpdateRelatedBaseFiled(tabPbAdministrativeDivision);
        return tabPbAdministrativeDivisionMapper.updateById(tabPbAdministrativeDivision);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 行政区划id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    @Override
    public int deleteById(Long id) {
        TabPbAdministrativeDivision tabPbAdministrativeDivision = new TabPbAdministrativeDivision();
        tabPbAdministrativeDivision.setAdministrativeDivisionId(id);
        tabPbAdministrativeDivision.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbAdministrativeDivision);
        return tabPbAdministrativeDivisionMapper.updateById(tabPbAdministrativeDivision);
    }

    /**
     * Description: 根据id查找
     *
     * @param id 行政区划id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    @Override
    public AdministrativeDivisionVO selectById(Long id) {
        return tabPbAdministrativeDivisionMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param administrativeDivisionQueryBean 查询实体
     * @param page                            分页
     * @return List<AdministrativeDivisionVO>
     * @author WuYunJie
     * @date 2019/05/25 14:46:34
     */
    @Override
    public List<AdministrativeDivisionVO> selectList(AdministrativeDivisionQueryBean administrativeDivisionQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbAdministrativeDivisionMapper.list(administrativeDivisionQueryBean);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        List<TabPbAdministrativeDivision> trainObjList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new BusinessException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            return false;
        }
        TabPbAdministrativeDivision trainObj;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            trainObj = new TabPbAdministrativeDivision();

            if (row.getCell(0).getCellType() != 1) {
                throw new BusinessException("导入失败(第" + (r + 1) + "行,姓名请设为文本格式)");
            }
            String code = row.getCell(0).getStringCellValue();

            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);


            String name = row.getCell(3).getStringCellValue();

            trainObj.setAdministrativeDivisionCode(code);
            trainObj.setAdministrativeDivisionName(name);
            if (0 == Long.parseLong(code.substring(2))) {
                trainObj.setLevel(59670L);
            } else {
                if (0 == Long.parseLong(code.substring(4))) {
                    trainObj.setLevel(59671L);
                } else {
                    if (0 == Long.parseLong(code.substring(6))) {
                        trainObj.setLevel(59672L);
                    } else {
                        if (0 == Long.parseLong(code.substring(9))) {
                            trainObj.setLevel(59673L);
                        } else {
                            trainObj.setLevel(59674L);
                        }
                    }
                }
            }
            paddingBaseFiled(trainObj);
            tabPbAdministrativeDivisionMapper.insert(trainObj);
            trainObjList.add(trainObj);
        }
        Long parentId0 = 0L;
        Long parentId1 = 0L;
        Long parentId2 = 0L;
        Long parentId3 = 0L;
        Long parentId4 = 0L;
        List<TabPbAdministrativeDivision> updateObjList = new ArrayList<>();
        Iterator<TabPbAdministrativeDivision> iterator = trainObjList.iterator();
        while (iterator.hasNext()) {
            TabPbAdministrativeDivision obj = iterator.next();
            if (0 == Long.parseLong(obj.getAdministrativeDivisionCode().substring(2))) {
                obj.setParentId(parentId0);
                updateObjList.add(obj);
                parentId1 = obj.getAdministrativeDivisionId();
            } else {
                if (0 == Long.parseLong(obj.getAdministrativeDivisionCode().substring(4))) {
                    obj.setParentId(parentId1);
                    updateObjList.add(obj);
                    parentId2 = obj.getAdministrativeDivisionId();
                } else {
                    if (0 == Long.parseLong(obj.getAdministrativeDivisionCode().substring(6))) {
                        obj.setParentId(parentId2);
                        parentId3 = obj.getAdministrativeDivisionId();
                        updateObjList.add(obj);
                    } else {
                        if (0 == Long.parseLong(obj.getAdministrativeDivisionCode().substring(9))) {
                            obj.setParentId(parentId3);
                            parentId4 = obj.getAdministrativeDivisionId();
                            updateObjList.add(obj);
                        } else {
                            obj.setParentId(parentId4);
                            updateObjList.add(obj);
                        }
                    }
                }
            }
        }
        for (TabPbAdministrativeDivision update : updateObjList) {
            paddingUpdateRelatedBaseFiled(update);
            tabPbAdministrativeDivisionMapper.updateById(update);
        }
        return true;
    }

    @Override
    public List<AdministrativeDivisionTree> selectListByParentId() {
        //构建树
        return getAdministrativeDivisionTree(
                tabPbAdministrativeDivisionMapper.selectTreeList(), 0L);
    }

    /**
     * 构建行政区划树
     *
     * @param administrativeDivisionVOS 行政区划VO
     * @param root                      根节点
     * @return List<AdministrativeDivisionTree>
     */
    private List<AdministrativeDivisionTree> getAdministrativeDivisionTree(List<AdministrativeDivisionVO> administrativeDivisionVOS, Long root) {
        List<AdministrativeDivisionTree> trees = administrativeDivisionVOS
                .stream()
                .filter(administrativeDivisionVO -> !administrativeDivisionVO.getParentId().equals(administrativeDivisionVO.getAdministrativeDivisionId()))
                .map(administrativeDivisionVO -> {
                    AdministrativeDivisionTree node = new AdministrativeDivisionTree();
                    node.setId(administrativeDivisionVO.getAdministrativeDivisionId());
                    node.setParentId(administrativeDivisionVO.getParentId());
                    node.setAdministrativeDivisionName(administrativeDivisionVO.getAdministrativeDivisionName());
                    node.setLatitude(administrativeDivisionVO.getLatitude());
                    node.setLongitude(administrativeDivisionVO.getLongitude());
                    node.setPartyMassesId(administrativeDivisionVO.getPartyMassesId());
                    node.setPartyMassesName(administrativeDivisionVO.getPartyMassesName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(trees, root);
    }
}

