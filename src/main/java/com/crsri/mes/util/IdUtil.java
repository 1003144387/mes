package com.crsri.mes.util;

/**
 * 〈一句话功能简述〉<br>
 * 〈生成唯一id的工具类〉
 *
 * @author zcj
 * @date 2018/8/15 11:13
 * @since 1.0.0
 */
public class IdUtil {

    public static String getId(){
        String a = String.valueOf(System.currentTimeMillis());
        String b = String.valueOf((int)((Math.random()*9+1)*1000));
        return a+b;
    }

}
