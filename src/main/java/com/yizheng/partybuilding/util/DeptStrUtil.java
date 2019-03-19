package com.yizheng.partybuilding.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 组织全路径字符串操作帮助类
 * zhuyu 2018-12-01
 */
public class DeptStrUtil {


    public static void main(String[] args) {
        String startStr = clearFormat("12733,10634,10533,10386,10380");
        startStr = getReverseStr(startStr);
        Long type = 59002L;
        if (type == 59002) {
            //基层党委内部转接  - 同一区下 - 上级审批即可
            String endStr = clearFormat("12733,10634,10323,10498,10497");
            String path = getJiCengPath(startStr, endStr);
            System.out.println("path:" + path);
        } else {
            //市级需要审批 - 不同区下
            String endStr = clearFormat("12733,9061,9022,10012");
            String path = startStr + endStr;
            System.out.println("path:" + path);

            List<String> s = getPathList(path);
            System.out.println("path:" + JSON.toJSONString(s));
        }
    }

    //获取基层党委内部转接审批路径
    public static String getJiCengPath(String startStr, String endStr) {
        List<String> sl = getList(startStr);
        List<String> el = getList(endStr);
        sl.retainAll(el); //交集
        for (String s : sl) {
            //移除交集中的元素
            startStr = removeStr(startStr, s + ",");
            endStr = removeStr(endStr, s + ",");
        }
        String path = startStr + endStr;
        return path;
    }

    //移除指定字符串
    public static String removeStr(String targetStr, String removeStr) {
        return targetStr.replace(removeStr, "");
    }

    /**
     * 清理全路径格式
     * 如果最前面是0，则删除
     * 移除最后一个组织节点，因为党支部不进行审批
     *
     * @param str
     * @return
     */
    public static String clearFormat(String str) {
        //if (str.startsWith("0,")) {
        //    str = str.replace("0,", "");
        //}
        int lastLen = str.lastIndexOf(',');
        str = str.substring(0, lastLen);
        return str;
    }

    //把全路径转成list
    public static List<String> getList(String str) {
        List<String> list = new ArrayList<String>();
        String[] strs = str.split(",");
        for (String s : strs) {
            if (!StringUtils.isEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    //全路径字符串反转
    public static String getReverseStr(String str) {
        String result = "";
        List<String> list = getList(str);
        Collections.reverse(list);
        for (String word : list) {
            result += word + ",";
        }
        return result;
    }

    //获取路径list
    public static List<String> getPathList(String str) {
        List<String> list = new ArrayList<String>();
        List<String> list1 = getList(str);
        for (String word : list1) {
            if (!list.contains(word)) {
                list.add(word);
            }
        }
        return list;
    }
}
