package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.entity.ClientUser;
import com.yizheng.partybuilding.service.inf.IClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(tags = "后台开发示例模块")
@RestController
@RequestMapping("/client")
public class ClientUserController {
    @Autowired
    private IClientUserService userService;

    @Value("${com.word.generatorPath}")
    private String generatorPath;

    @ApiOperation(value = "获取当前用户示例", httpMethod = "GET" , notes = "朱宇")
    @GetMapping("/hello")
    public String hello() {
        String str = " hello " + UserContextHolder.getUserName();
        return str;
    }

    @ApiOperation(value = "统一抛异常示例", httpMethod = "GET" , notes = "朱宇")
    @GetMapping("/getException")
    public String getException() {
        //手写一个异常，供全局异常处理器捕获，并返回通用错误信息
        String str = null;
        try {
            // 重点，异常处理
            //1.如果不用try catch捕获，则全局异常处理器会捕获
            //2.如果捕获后，不抛出，则全局异常处理器不能捕获
            //3.如果想抛出异常，则使用 throw new BusinessException("null异常.....");,全局异常处理器就能捕获
            //if(str.equals("12")){

            //}
            Thread.sleep(5000); //睡5秒，网关hystrix超时3秒，会触发熔断、降低
        } catch (Exception e) {
            throw new BusinessException("null异常.....");
        }

        return "get exception";
    }

    @ApiOperation(value = "查询对象示例", httpMethod = "GET" , notes = "朱宇")
    @GetMapping("/findByUsername")
    public ClientUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value = "查询对象示例", httpMethod = "GET", notes = "朱宇")
    @GetMapping("/findByUsername/{username}")
    public ClientUser userDel(@ApiParam(value = "用户名", required = true) @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value = "更新", httpMethod = "POST" , notes = "朱宇")
    @PostMapping("/update")
    public Boolean update(@RequestBody ClientUser clientUser){
        return userService.update(clientUser) > 0 ? true : false;
    }

    /**
     * 翻页，获取用户信息
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation(value = "翻页查询示例", httpMethod = "GET" , notes = "朱宇")
    @GetMapping("/getPageList")
    public PageInfo<ClientUser> getPageList(int pageSize, int pageNum) {
        return userService.getPageList(pageSize, pageNum);
    }

}
