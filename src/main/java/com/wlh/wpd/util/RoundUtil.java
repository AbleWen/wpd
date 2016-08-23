package com.wlh.wpd.util;

import java.math.BigDecimal;

/**
 * 对浮点数据进行取精度
 */
public class RoundUtil
{
    /**
     * 对double数据进行取精度
     */
    public static double roundDouble(double value, int scale, int roundingMode)
    {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(scale, roundingMode);
        double result = bigDecimal.doubleValue();

        return result;
    }

    /**
     * 对double数据进行取精度
     */
    public static float roundFloat(float value, int scale, int roundingMode)
    {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(scale, roundingMode);
        float result = bigDecimal.floatValue();

        return result;
    }
}
