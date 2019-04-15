package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.EchartsDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTrain;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTrainComment;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTrainDynamic;
import com.egovchina.partybuilding.partybuild.service.ITabPbEduTrainService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/12/10
 */
@Deprecated
@Api(tags = "党员教育-培训模块")
@RestController
@RequestMapping("/eduTrain")
public class TabPbEduTrainController {

    @Autowired
    ITabPbEduTrainService eduTrainService;

    @Deprecated
    @ApiOperation(value = "获取统计", notes = "获取统计", httpMethod = "GET")
    @GetMapping("/getStatistics")
    public Map<String, List<EchartsDto>> getStatistics(){
        return eduTrainService.getStatistics();
    }

    @Deprecated
    @ApiOperation(value = "添加培训信息", notes = "培训信息新增", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addTrain(@ApiParam(value = "培训信息")@Valid @RequestBody TabPbEduTrain train) {
        int insert = eduTrainService.insert(train);
        return ReturnUtil.buildReturn(insert);
    }

    @Deprecated
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除培训", notes = "删除培训")
    public ReturnEntity deleteTrain(@ApiParam(value = "培训id",required=true)@PathVariable Integer id) {
        int retVal = eduTrainService.deleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "修改培训", notes = "修改培训", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity editTrain(@ApiParam(value = "培训信息")@Valid @RequestBody TabPbEduTrain train) {
        int retVal = eduTrainService.updateById(train);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "通过ID查询培训信息", notes = "通过ID查询培训信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public TabPbEduTrain getTrainById(@ApiParam(value = "培训ID",required=true)@PathVariable Integer id) {
        return eduTrainService.selectById(id);
    }

    @Deprecated
    @ApiOperation(value = "分页查询培训信息", notes = "分页查询培训信息", httpMethod = "GET")
    @GetMapping("/selectList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required=true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query",required=true),
            @ApiImplicitParam(name = "trainObj", value = "培训对象",dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "region", value = "区域",dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "name", value = "培训名称", paramType = "query")
    })
    public PageInfo<TabPbEduTrain> selectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return eduTrainService.selectList(params);
    }

    @Deprecated
    @ApiOperation(value = "添加培训动态", notes = "培训信息新增", httpMethod = "POST")
    @PostMapping("addTrainDynamic")
    public ReturnEntity addTrainDynamic(@ApiParam(value = "培训信息")@Valid @RequestBody TabPbEduTrainDynamic trainDynamic) {
        int retVal = eduTrainService.insertTrainDynamic(trainDynamic);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "修改培训动态（目前只有审核可以修改动态，审核请务必带上state）", notes = "修改培训动态", httpMethod = "PUT")
    @PutMapping("editTrainDynamic")
    public ReturnEntity editTrainDynamic(@ApiParam(value = "培训动态")@Valid @RequestBody TabPbEduTrainDynamic trainDynamic) {
        int retVal = eduTrainService.editTrainDynamic(trainDynamic);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "分页查询培训动态信息", notes = "分页查询培训动态信息", httpMethod = "GET")
    @GetMapping("/selectDynamicList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required=true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query",required=true),
            @ApiImplicitParam(name = "trainId", value = "重点培训id", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "审核状态", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "发布动态的党员id", paramType = "query")
    })
    public PageInfo<TabPbEduTrainDynamic> selectDynamicList(@ApiIgnore @RequestParam Map<String, Object> params){
        return eduTrainService.selectDynamicList(params);
    }

    @Deprecated
    @ApiOperation(value = "判断当前登陆用户是否允许对指定培训发布动态", notes = "分页查询培训动态信息", httpMethod = "GET")
    @GetMapping("/judgeRelease")
    public ReturnEntity judgeRelease(@ApiParam(value = "培训ID",required=true)@RequestParam Long trainId){
        boolean flag = eduTrainService.judgeRelease(trainId);
        return flag ? ReturnUtil.success() : ReturnUtil.fail();
    }

    @Deprecated
    @ApiOperation(value = "通过ID查询培训动态信息", notes = "通过ID查询培训动态信息", httpMethod = "GET")
    @GetMapping("/dynamic/{id}")
    public TabPbEduTrainDynamic getTrainDynamicById(@ApiParam(value = "培训ID",required=true)@PathVariable Integer id) {
        return eduTrainService.getTrainDynamicById(id);
    }

    @Deprecated
    @ApiOperation(value = "人员导入", notes = "限制上传 .xls", httpMethod = "POST")
    @PostMapping("/upload")
    public boolean upload(@ApiParam(value="重点培训人员基础信息",required=true) MultipartFile file,
                          @RequestParam(required = false) @ApiParam(value = "重点培训中的id")Long trainId) throws Exception {
        String fileName = file.getOriginalFilename();
        return  eduTrainService.batchImport(fileName, file,trainId);
    }

    @Deprecated
    @ApiOperation(value = "评价课程", notes = "评价课程", httpMethod = "GET")
    @GetMapping("/evaluation")
    public boolean evaluation(@RequestParam(required = false) @ApiParam(value = "重点培训中的课程id")Long courseId,
                              @RequestParam(required = false) @ApiParam(value = "评论等级-最多五颗星,1-5")Integer level,
                              @RequestParam(required = false) @ApiParam(value = "评论结果：好、较好、一般、差")String result){
        return eduTrainService.evaluation(new TabPbEduTrainComment().setCourseId(courseId).setLevel(level).setResult(result));
    }

    @Deprecated
    @ApiOperation(value = "班级管理员进行报道",notes = "班级管理员进行报道",httpMethod = "GET")
    @GetMapping("/report")
    public boolean report(@RequestParam(required = false) @ApiParam(value = "培训人员id")Long trainObjId){
        return eduTrainService.report(trainObjId);
    }

    @Deprecated
    @ApiOperation(value = "判断当前登陆用户是否可以评价课程", notes = "评价课程", httpMethod = "GET")
    @GetMapping("/isComment")
    public boolean isComment(@RequestParam(required = false) @ApiParam(value = "重点培训中的课程id")Long courseId){
        return eduTrainService.isComment(courseId);
    }

    @Deprecated
    @ApiOperation(value = "设置班级管理员", notes = "如果已经又管理员了 会顶替掉之前的", httpMethod = "GET")
    @GetMapping("/setAdmin")
    public boolean setAdmin(@RequestParam(required = false) @ApiParam(value = "培训人员id")Long trainObjId){
        return eduTrainService.setAdmin(trainObjId);
    }

    @Deprecated
    @ApiOperation(value = "发送通知")
    @GetMapping("/sendNotification")
    public boolean sendNotification(@RequestParam(required = false) @ApiParam(value = "培训id")Long trainId){
        return eduTrainService.sendNotification(trainId);
    }

    @Deprecated
    @ApiOperation(value = "获取统计测试", notes = "获取统计测试", httpMethod = "GET")
    @GetMapping("/getStatisticsTest")
    public List<TabPbEduTrain> getStatisticsTest(){
        return eduTrainService.getStatisticsTest();
    }
}
