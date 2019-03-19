package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.UserLoginDto;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.NoticeType;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.EchartsDto;
import com.yizheng.partybuilding.entity.*;
import com.yizheng.partybuilding.feign.MessageServiceController;
import com.yizheng.partybuilding.repository.*;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.ITabPbEduTrainService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.commons.util.AttachmentType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: huang
 * Date: 2018/12/10
 */
@Service("eduTrainService")
@Transactional(rollbackFor = Exception.class)
public class TabPbEduTrainServiceImpl implements ITabPbEduTrainService {

    @Autowired
    private TabPbEduTrainMapper trainMapper;

    @Autowired
    private TabPbEduTrainObjMapper trainObjMapper;

    @Autowired
    private TabPbEduTrainCourseMapper trainCourseMapper;

    @Autowired
    private ITabPbAttachmentService attachmentService;

    @Autowired
    private TabPbEduTrainDynamicMapper trainDynamicMapper;

    @Autowired
    private TabPbEduTrainCommentMapper trainCommentMapper;

    @Autowired
    private MessageServiceController messageServiceController;

    @Override
    public int insert(TabPbEduTrain train) {
        int flag = trainMapper.insertSelective(train);
        if (flag > 0) {
            List<TabPbEduTrainObj> trainObjList = train.getTrainObjList();
            if (!ObjectUtils.isEmpty(trainObjList)) {
                trainObjList.forEach(obj -> obj.setTrainId(train.getTrainId()));
                trainObjMapper.insertByBatch(trainObjList);
            }
            List<TabPbEduTrainCourse> positionCourses = train.getCourses();
            if (!ObjectUtils.isEmpty(positionCourses)) {
                positionCourses.forEach(pc -> pc.setTrainId(train.getTrainId()));
                trainCourseMapper.insertByBatch(positionCourses);
            }
            attachmentService.intelligentOperation(train.getAttachments(), train.getTrainId(), AttachmentType.TRAIN);
        }
        return flag;
    }

    @Override
    public int updateById(TabPbEduTrain train) {
        if (train == null || ObjectUtils.isEmpty(train.getTrainId())) {
            return 0;
        }
        Long trainId = train.getTrainId();
        //更新train本体
        train.setUpdateTime(new Date());
        UserLoginDto sysUser = UserContextHolder.currentUser();
        if (sysUser != null) {
            train.setUpdateUserid(sysUser.getUserId().longValue());
            train.setUpdateUsername(sysUser.getRealname());
        }
        int retVal = trainMapper.updateByPrimaryKeySelective(train);
        if (retVal > 0) {
            //更新train对应课程表
            List<TabPbEduTrainCourse> courses = train.getCourses();
            if (!ObjectUtils.isEmpty(courses)) {
                courses.forEach(course -> course.setTrainId(trainId));
                trainCourseMapper.deleteByTrainId(trainId);
                trainCourseMapper.insertByBatch(courses);
            }
            //更新train人员对应表！！还是判断一下部分插入吧，全部插入感觉会有效率问题啊。
            List<TabPbEduTrainObj> trainObjS = train.getTrainObjList();
            if (!ObjectUtils.isEmpty(trainObjS)) {
                trainObjS.forEach(obj -> obj.setTrainId(trainId));
                trainObjMapper.deleteByTrainId(trainId);
                trainObjMapper.insertByBatch(trainObjS);
            }
            //更新附件
            attachmentService.intelligentOperation(train.getAttachments(), train.getTrainId(), AttachmentType.TRAIN);
        }
        return retVal;
    }

    @Override
    public int deleteById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return 0;
        }
        TabPbEduTrain train = new TabPbEduTrain();
        train.setTrainId(id.longValue()).setDelFlag(CommonConstant.STATUS_DEL);
        return trainMapper.updateByPrimaryKeySelective(train);
    }

    @Override
    public TabPbEduTrain selectById(Integer id) {
        return trainMapper.bigSelectByKey(id.longValue());
    }

    @Override
    public PageInfo<TabPbEduTrain> selectList(Map<String, Object> params) {
        TabPbEduTrain train = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbEduTrain.class);
        if (!StringUtils.isEmpty(train.getName())) {
            train.setName("%" + train.getName() + "%");
        }
        PageHelper.startPage(Integer.parseInt((String) params.get("page")), Integer.parseInt((String) params.get("limit")));
        return new PageInfo<>(trainMapper.selectiveList(train));
    }

    private static Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    @Override
    public int editTrainDynamic(TabPbEduTrainDynamic trainDynamic) {
        TabPbEduTrainDynamic dynamic = new TabPbEduTrainDynamic();
        UserLoginDto sysUser = UserContextHolder.currentUser();
        if (sysUser != null && !StringUtils.isEmpty(trainDynamic.getDynamicId()) && !StringUtils.isEmpty(trainDynamic.getState())) {
            dynamic.setDynamicId(trainDynamic.getDynamicId());
            dynamic.setState(trainDynamic.getState());
            dynamic.setVerifyTime(new Date());
            dynamic.setVerifyUserid(sysUser.getUserId().longValue());
            dynamic.setCreateUsername(sysUser.getRealname());
            int retVal = trainDynamicMapper.updateByPrimaryKeySelective(dynamic);
            if (retVal > 0 && trainDynamic.getAttachments() != null && trainDynamic.getAttachments().size() > 0) {
                attachmentService.intelligentOperation(trainDynamic.getAttachments(), trainDynamic.getDynamicId(), AttachmentType.TRAINDYNAMIC);
            }
            return retVal;
        }
        return 0;
    }

    @Override
    public Map<String, List<EchartsDto>> getStatistics() {
        Map<String, List<EchartsDto>> statistics = new HashMap<>(2);
        statistics.put("trainObj", this.getTrainObjStatistics());
        statistics.put("trainYears", this.getTrainYearsStatistics());
        return statistics;
    }

    @Override
    public PageInfo<TabPbEduTrainDynamic> selectDynamicList(Map<String, Object> params) {
        TabPbEduTrainDynamic dynamic = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbEduTrainDynamic.class);
        PageHelper.startPage(Integer.parseInt((String) params.get("page")), Integer.parseInt((String) params.get("limit")));
        return new PageInfo<>(trainDynamicMapper.selectiveList(dynamic));
    }

    @Override
    public boolean judgeRelease(Long trainId) {
        UserLoginDto sysUser = UserContextHolder.currentUser();
        if (sysUser != null && trainId != 0) {
            TabPbEduTrainObj trainObj = new TabPbEduTrainObj();
            trainObj.setTrainId(trainId);
            trainObj.setUserId(sysUser.getUserId().longValue());
            return trainObjMapper.selectList(trainObj).size() > 0;
        }
        return Boolean.FALSE;
    }

    @Override
    public TabPbEduTrainDynamic getTrainDynamicById(Integer id) {
        return trainDynamicMapper.selectByPrimaryKey(id.longValue());
    }

    public static boolean isInteger(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    public List<EchartsDto> getTrainObjStatistics() {
        return trainMapper.getTrainObjStatistics();
    }

    public List<EchartsDto> getTrainYearsStatistics() {
        return trainMapper.getTrainYearsStatistics();
    }

    @Override
    public int insertTrainDynamic(TabPbEduTrainDynamic trainDynamic) {
        UserLoginDto sysUser = UserContextHolder.currentUser();
        if (sysUser != null && !StringUtils.isEmpty(trainDynamic.getTrainId()) && judgeRelease(trainDynamic.getTrainId())) {
            trainDynamic.setUserId(sysUser.getUserId().longValue());
            trainDynamic.setUsername(sysUser.getRealname());
            trainDynamic.setCreateTime(new Date());
        } else {
            return 0;
        }
        int retVal = trainDynamicMapper.insertSelective(trainDynamic);
        if (retVal > 0) {
            attachmentService.intelligentOperation(trainDynamic.getAttachments(), trainDynamic.getDynamicId(), AttachmentType.TRAINDYNAMIC);
        }
        return retVal;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file, Long trainId) throws Exception {
        if (ObjectUtils.isEmpty(trainId)) {
            throw new BusinessException("操作不正确，无法关联重点培训");
        }
        List<TabPbEduTrainObj> trainObjList = new ArrayList<>();
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
        TabPbEduTrainObj trainObj;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            trainObj = new TabPbEduTrainObj();

            if (row.getCell(0).getCellType() != 1) {
                throw new BusinessException("导入失败(第" + (r + 1) + "行,姓名请设为文本格式)");
            }
            String name = row.getCell(0).getStringCellValue();
            if (name == null || name.isEmpty()) {
                throw new BusinessException("导入失败(第" + (r + 1) + "行,姓名未填写)");
            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String idCard = row.getCell(1).getStringCellValue();
            if (idCard == null || idCard.isEmpty()) {
                throw new BusinessException("导入失败(第" + (r + 1) + "行,身份证未填写)");
            }

            String positive = row.getCell(2).getStringCellValue();

            String gender = row.getCell(3).getStringCellValue();

            String eduStr = row.getCell(4).getStringCellValue();
            if (eduStr.length() > 2 && isInteger(eduStr.substring(0, 2))) {
                Long education = Long.valueOf(eduStr.substring(0, 2));
                trainObj.setEducation(education);
            } else {
                throw new BusinessException("导入失败(第" + (r + 1) + "行,学历识别错误)");
            }
            trainObj.setUserName(name);
            trainObj.setIdCard(idCard);
            trainObj.setPositive(positive);
            trainObj.setGender(gender);
            trainObjList.add(trainObj);
        }
        for (TabPbEduTrainObj obj : trainObjList) {
            obj.setTrainId(trainId);
            List<TabPbEduTrainObj> objs = trainObjMapper.selectList(obj);
            if (objs == null || objs.size() == 0) {
                trainObjMapper.insert(obj);
            } else {
                TabPbEduTrainObj oldObj = objs.get(0);
                oldObj.setUserName(obj.getUserName());
                oldObj.setIdCard(obj.getIdCard());
                oldObj.setPositive(obj.getPositive());
                oldObj.setGender(obj.getGender());
                oldObj.setEducation(obj.getEducation());
                trainObjMapper.updateByPrimaryKeySelective(oldObj);
            }
        }
        return true;
    }

    @Override
    public boolean evaluation(TabPbEduTrainComment setResult) {
        TabPbEduTrainCourse course = trainCourseMapper.selectByPrimaryKey(setResult.getCourseId());
        Integer userId = UserContextHolder.getUserId();
        setResult.setUserId(userId.longValue());
        List<TabPbEduTrainObj> trainObjs = trainObjMapper
                .selectList(new TabPbEduTrainObj().setTrainId(course.getTrainId()).setUserId(userId.longValue()));
        List<TabPbEduTrainComment> comments = trainCommentMapper.selectList(setResult);
        if (trainObjs.size() > 0 && comments.size() < 1) {
            trainCommentMapper.insert(setResult.setCreateTime(new Date()));
            return true;
        }
        return false;
    }

    @Override
    public boolean isComment(Long courseId) {
        TabPbEduTrainCourse course = trainCourseMapper.selectByPrimaryKey(courseId);
        Integer userId = UserContextHolder.getUserId();
        List<TabPbEduTrainObj> trainObjs = trainObjMapper
                .selectList(new TabPbEduTrainObj().setTrainId(course.getTrainId()).setUserId(userId.longValue()));
        List<TabPbEduTrainComment> comments = trainCommentMapper
                .selectList(new TabPbEduTrainComment().setUserId(userId.longValue()).setCourseId(courseId));
        return trainObjs.size() > 0 && comments.size() < 1;
    }

    @Override
    public boolean setAdmin(Long trainObjId) {
        TabPbEduTrainObj trainObj = trainObjMapper.selectByPrimaryKey(trainObjId);
        trainObjMapper.setAdeminFalse(trainObj.getTrainId());
        return trainObjMapper.updateByPrimaryKeySelective(new TabPbEduTrainObj().setAdminFlag(true).setId(trainObjId)) > 1;
    }

    @Override
    public boolean sendNotification(Long trainId) {
        List<TabPbEduTrainObj> trainObjs = trainObjMapper.selectList(new TabPbEduTrainObj().setTrainId(trainId));
        List<TabPbNoticeReceive> receives = new ArrayList<>();
        trainObjs.forEach(trainObj -> {
            if (!ObjectUtils.isEmpty(trainObj.getUserId())) {
                receives.add(new TabPbNoticeReceive().setUserId(trainObj.getUserId()));
            }
        });
        TabPbNotice notice = new TabPbNotice();
        //完善消息主体。
        notice.setNoticeType(NoticeType.EDUCATION);
        notice.setTabPbNoticeReceives(receives);
        messageServiceController.noticeAdd(notice);
        return false;
    }

    @Override
    public boolean report(Long trainObjId) {
        return trainObjMapper.updateByPrimaryKeySelective(new TabPbEduTrainObj().setReportFlag(true).setId(trainObjId)) > 1;
    }

    /**
     * 获取统计数据测试
     *
     * @return
     */
    @Override
    public List<TabPbEduTrain> getStatisticsTest() {
        List<TabPbEduTrain> list = trainMapper.getStatisticsTest();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List<EchartsDto> listdetail = trainMapper.getStatistceDetailTest(list.get(i).getYears());
                if (listdetail.size() > 0) {
                    //for (int j = 0; j < listdetail.size(); j++) {
                        list.get(i).setTest(listdetail);
                    //}
                }
            }
        }
        return list;
    }
}
