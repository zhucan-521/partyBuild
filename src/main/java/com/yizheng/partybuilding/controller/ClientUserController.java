package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.IdWorker;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.util.WordTextParam;
import com.yizheng.commons.util.WordUtil;
import com.yizheng.partybuilding.entity.ClientUser;
import com.yizheng.partybuilding.service.inf.IClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
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

    @ApiOperation(value = "获取当前用户示例", httpMethod = "GET")
    @GetMapping("/hello")
    public String hello() {
        String str = " hello " + UserContextHolder.getUserName();
        return str;
    }

    @ApiOperation(value = "统一抛异常示例", httpMethod = "GET")
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

    @ApiOperation(value = "查询对象示例", httpMethod = "GET")
    @GetMapping("/findByUsername")
    public ClientUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value = "查询对象示例", notes = "查询对象示例", httpMethod = "GET")
    @GetMapping("/findByUsername/{username}")
    public ClientUser userDel(@ApiParam(value = "用户名", required = true) @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
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
    @ApiOperation(value = "翻页查询示例", httpMethod = "GET")
    @GetMapping("/getPageList")
    public PageInfo<ClientUser> getPageList(int pageSize, int pageNum) {
        return userService.getPageList(pageSize, pageNum);
    }

    @ApiOperation(value = "导出word示例", httpMethod = "POST")
    @PostMapping("/exportDoc")
    public String exportDoc(){
        String content ="    额尔古纳河在1689年的《中俄尼布楚条约》中成为中国和俄罗斯的界河，额尔古纳河上游称海拉尔河，源于大兴安岭西侧，西流至阿该巴图山脚， 折而北行始称额尔古纳河。额尔古纳河在黑龙江省漠河县以西的内蒙古自治区额尔古纳右旗的恩和哈达附近与流经俄罗斯境内的石勒喀河汇合后始称黑龙江。沿额尔古纳河沿岸地区土地肥沃，森林茂密，水草丰美， 鱼类品种很多，动植物资源丰富，宜农宜木，是人类理想的天堂。";
        ParagraphAlignment align = ParagraphAlignment.CENTER;
        String fontFamily = "仿宋";
        int fontSize = 13;
        String targetPath =  generatorPath + IdWorker.getDateId() + ".docx";
        System.out.println("targetPath : " + targetPath);
        try {
            WordTextParam textParam = new WordTextParam(content , align , fontFamily , fontSize);
            WordUtil.exportTextDoc(textParam , targetPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return targetPath;
    }
}
