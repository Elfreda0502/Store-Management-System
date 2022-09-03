package com.game.common.utils.uuid;

import java.util.concurrent.atomic.AtomicInteger;
import com.game.common.utils.DateUtils;
import com.game.common.utils.StringUtils;

/**
 * @author Yu Yue sequence generation class
 */
public class Seq
{

    public static final String commSeqType = "COMMON";


    public static final String uploadSeqType = "UPLOAD";


    private static AtomicInteger commSeq = new AtomicInteger(1);


    private static AtomicInteger uploadSeq = new AtomicInteger(1);


    private static String machineCode = "A";

    /**
     * Get the universal serial number
     *
     * @return sequence value
     */
    public static String getId()
    {
        return getId(commSeqType);
    }

    /**
     * default 16-bit serial number yyMMddHHmmss + one machine ID + 3-length cyclically increasing string
     *
     * @return sequence value
     */
    public static String getId(String type)
    {
        AtomicInteger atomicInt = commSeq;
        if (uploadSeqType.equals(type))
        {
            atomicInt = uploadSeq;
        }
        return getId(atomicInt, 3);
    }

    /**
     * Universal interface serial number yyMMddHHmmss + one machine ID + length cyclically increasing string
     *
     * @param atomicInt sequence number
     * @param length value length
     * @return sequence value
     */
    public static String getId(AtomicInteger atomicInt, int length)
    {
        String result = DateUtils.dateTimeNow();
        result += machineCode;
        result += getSeq(atomicInt, length);
        return result;
    }

    /**
     * Sequence loop incrementing string [1, 10 to the (length) power), left-padded with 0 length digits
     *
     * @return sequence value
     */
    private synchronized static String getSeq(AtomicInteger atomicInt, int length)
    {

        int value = atomicInt.getAndIncrement();


        int maxSeq = (int) Math.pow(10, length);
        if (atomicInt.get() >= maxSeq)
        {
            atomicInt.set(1);
        }

        return StringUtils.padl(value, length);
    }
}
