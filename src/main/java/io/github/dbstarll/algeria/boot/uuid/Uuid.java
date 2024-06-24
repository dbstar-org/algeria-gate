package io.github.dbstarll.algeria.boot.uuid;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.exception.InvalidUuidException;
import com.github.f4b6a3.uuid.util.UuidUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author dbstar
 */
public final class Uuid {
    public static final int AGE_INVALID_UUID = -2;
    public static final int AGE_ILLEGAL_ARGUMENT = -3;

    private Uuid() {
        // 工具类禁止实例化
    }

    public static UUID generate() {
        return UuidCreator.getTimeOrderedEpochPlus1();
    }

    /**
     * 从string还原UUID.
     *
     * @param string string
     * @return UUID
     */
    public static UUID fromString(final String string) {
        return UuidCreator.fromBytes(Base64.decodeBase64(string));
    }

    /**
     * 将UUID转换为string.
     *
     * @param value UUID
     * @return string of UUID
     */
    public static String toString(final UUID value) {
        return Base64.encodeBase64URLSafeString(UuidCreator.toBytes(value));
    }

    /**
     * Returns the instant from a time-based, time-ordered or DCE Security UUID.
     *
     * @param uuid a UUID
     * @return {@link Instant}
     * @throws IllegalArgumentException if the input is not a time-based,
     *                                  time-ordered or DCE Security UUID.
     */
    public static Instant getInstant(final UUID uuid) {
        return UuidUtil.getInstant(uuid);
    }

    /**
     * 计算UUID包含的时间戳与结束时间之间间隔的天数(自然日).
     *
     * @param uuid a time-based, time-ordered or DCE Security UUID String
     * @param end  结束时间
     * @return 间隔的天数
     */
    public static int obtainAge(final String uuid, final long end) {
        try {
            return obtainAge(fromString(uuid), end);
        } catch (InvalidUuidException e) {
            return AGE_INVALID_UUID;
        }
    }

    /**
     * 计算UUID包含的时间戳与结束时间之间间隔的天数(自然日).
     *
     * @param uuid a time-based, time-ordered or DCE Security UUID
     * @param end  结束时间
     * @return 间隔的天数
     */
    public static int obtainAge(final UUID uuid, final long end) {
        try {
            return obtainAge(getInstant(uuid), end);
        } catch (IllegalArgumentException e) {
            return AGE_ILLEGAL_ARGUMENT;
        }
    }

    /**
     * 计算开始时间与结束时间之间间隔的天数(自然日).
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 间隔的天数
     */
    public static int obtainAge(final Instant start, final long end) {
        return day(end) - day(start.toEpochMilli());
    }

    /**
     * day of milliseconds.
     *
     * @param milliseconds the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
     * @return day
     */
    private static int day(final long milliseconds) {
        return (int) ((milliseconds + TimeZone.getDefault().getRawOffset()) / DateUtils.MILLIS_PER_DAY);
    }
}
