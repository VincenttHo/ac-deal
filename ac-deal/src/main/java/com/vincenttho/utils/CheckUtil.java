package com.vincenttho.utils;

/**
 * @className:com.vincenttho.utils.CheckUtil
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/8     VincentHo       v1.0.0        create
 */
public class CheckUtil {

    public static void notNull(Object obj, String msg) {
        if(obj == null) {
            throw new RuntimeException(msg);
        }
    }

    public static void notEmpty(String str, String msg) {
        if(str == null || "".equals(str)) {
            throw new RuntimeException(msg);
        }
    }

}