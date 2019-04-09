package com.ysl.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class.getName());
    private static final String CODE = "UTF-8";
    
	private static final String DEFAULT_UNIT = "万英镑";

    /**
     * 把数据格式化为两位小数
     *
     * @param obj
     * @return
     */
    public static String format(BigDecimal obj) {
        if (obj == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String res = df.format(obj);
        if ("-0.00".equals(res)) {
            return "0.00";
        }
        return df.format(obj);
    }
    
    public static String format(String str){
    	BigDecimal obj = new BigDecimal(str);
    	return format(obj);
    }

    /**
     * int 转成 byte数组
     *
     * @param num
     * @return
     */
    public static byte[] intToByteArray(int num) {
        byte[] byteArr = new byte[4];
        // 最低位
        byteArr[0] = (byte) (num & 0xff);
        // 次低位
        byteArr[1] = (byte) ((num >> 8) & 0xff);
        // 次高位
        byteArr[2] = (byte) ((num >> 16) & 0xff);
        // 最高位,无符号右移
        byteArr[3] = (byte) (num >>> 24);
        return byteArr;
    }

    /**
     * byte数组转成int
     *
     * @param arr
     * @return
     */
    public static int byteArrayToInt(byte[] arr) {
        return (arr[0] & 0xff) | ((arr[1] << 8) & 0xff00) | ((arr[2] << 24) >>> 8) | (arr[3] << 24);
    }

    public static boolean isClassNull(Object obj) {
        boolean state = true;
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getName() == "serialVersionUID" || f.get(obj) == null || "".equals(f.get(obj))) {
                } else {
                    state = false;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return state;
    }

    private Util() {
    }
}
