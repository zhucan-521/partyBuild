package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbTrainingDto;
import com.egovchina.partybuilding.partybuild.service.TabPbTrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author: zhucan
 * 党员培训控制层
 */
@Api(tags = "党员-党员培训情况 朱灿")
@RestController
@RequestMapping("/TabPbTraining/")
public class TabPbTrainingController {

        @Autowired
        TabPbTrainingService tabPbTrainingService;

        @ApiOperation(value = "添加党员培训情况，返回培训主键", notes = "必须有userId", httpMethod = "POST")
        @PostMapping("/insert")
        public Long insert(@RequestBody TabPbTrainingDto tabPbTrainingDto){
            tabPbTrainingService.insert(tabPbTrainingDto);
            return tabPbTrainingDto.getTraningId();
        }

        @ApiOperation(value = "根据某党员id列表显示某党员培训情况", notes = "根据党员id列表显示党员培训情况", httpMethod = "GET")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "userId", value = "党员ID", paramType = "query"),
        })
        @GetMapping("/selectAll")
        public List<TabPbTrainingDto> select(@ApiIgnore TabPbTrainingDto tabPbTrainingDto){
           return tabPbTrainingService.select(tabPbTrainingDto);
        }

        @ApiOperation(value = "根据培训主键删除党员培训情况", notes = "必须要有党员培训主键", httpMethod = "DELETE")
        @DeleteMapping ("/delet")
        public ReturnEntity delet(Long TraningId) {
            return ReturnUtil.buildReturn(tabPbTrainingService.delete(TraningId));
        }

        @ApiOperation(value = "根据培训主键修改党员培训情况", notes = "必须要有党员培训主键", httpMethod = "PUT")
        @PutMapping("/Update")
        public ReturnEntity Update(@RequestBody TabPbTrainingDto tabPbTrainingDto) {
            return ReturnUtil.buildReturn(tabPbTrainingService.update(tabPbTrainingDto));
        }

        @ApiOperation(value = "根据培训主键单个查询党员培训情况", httpMethod = "GET")
        @GetMapping ("/selectOne")
        public TabPbTrainingDto selectOne(Long TraningId) {
            return tabPbTrainingService.selectTabPbTrainingDtoById(TraningId);
        }

}
