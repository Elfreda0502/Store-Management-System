package com.game.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Precise floating point arithmetic
 *
 * @author Yu Yue
 */
public class Arith
{

    
    private static final int DEF_DIV_SCALE = 10;

    
    private Arith()
    {
    }

    /**
     * Provides exact addition operations.
     * @param v1 summand
     * @param v2 addend
     * @return the sum of the two parameters
     */
    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Provides accurate subtraction operations.
     * @param v1 the minuend
     * @param v2 Subtraction
     * @return the difference between the two parameters
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Provides accurate multiplication.
     * @param v1 multiplicand
     * @param v2 multiplier
     * @return the product of the two parameters
     */
    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Provides a (relatively) accurate division operation, accurate to
     * 10 decimal places after the decimal point, the following figures are rounded up.
     * @param v1 dividend
     * @param v2 divisor
     * @return the quotient of the two parameters
     */
    public static double div(double v1, double v2)
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * Provides (relatively) exact division operations. When there is an inexhaustible division, the scale parameter refers to
     * Fixed precision, subsequent numbers are rounded off.
     * @param v1 dividend
     * @param v2 divisor
     * @param scale indicates that it needs to be accurate to a few decimal places.
     * @return the quotient of the two parameters
     */
    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Provides accurate decimal rounding.
     * @param v the number to be rounded
     * @param scale several digits after the decimal point
     * @return rounded result
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
