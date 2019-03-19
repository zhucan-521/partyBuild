package com.yizheng.partybuilding.controller;

import com.yizheng.partybuilding.entity.ClientUser;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.partybuilding.service.inf.IClientUserService;
import com.yizheng.commons.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/report")
public class ReportExcelController {

    @Autowired private IClientUserService userService;

    /**
     * 导出报表
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取要导出的数据
        List<ClientUser> list = userService.listAll();
        String[] title = { "用户名", "密码", "令牌"};                      //excel行标题
        String fileName = "用户信息表"+ System.currentTimeMillis() +".xls";//excel文件名
        String sheetName = "用户信息表";                                    //sheet名

        String [][]content = null;//行内容
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            ClientUser user = list.get(i);
            content[i][0] = user.getUsername();
            content[i][1] = user.getPassword();
            content[i][2] = user.getAccessToken();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            ExcelUtil.setResponseStream(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            new BusinessException("导出失败，原因：" + e.getMessage());
        }
    }
}
