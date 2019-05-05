package com.egovchina.partybuilding.partybuild.util;

public interface CommonConstant {

    /**
     * 删除
     */
    String STATUS_DEL = "1";
    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 有效
     */
    String STATUS_EBL = "1";
    /**
     * 无效
     */
    String STATUS_NOEBL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 跳转
     */
    String JUMP = "1";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 前端工程名
     */
    String FRONT_END_PROJECT = "pigx-ui";

    /**
     * 后端工程名
     */
    String BACK_END_PROJECT = "pigx";

    /**
     * jwt签名
     */
    String SIGN_KEY = "PIG";

    /**
     * 字典根目录id
     */
    Integer ROOT_DICT = 0;

    /**
     * 排序字段
     */
    String SQL_SORT = "sort";
}
