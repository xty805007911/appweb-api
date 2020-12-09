package com.ctsi.util;

/**
 * @ClassName : VaildateNPTUtils
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-09 19:18
 */
public class CheckNPTUtils {

    public static void checkCurrentPage(Integer page) {
        if(page == null || page <= 0) {
            page = 1;
        }
    }
}
