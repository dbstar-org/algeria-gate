package io.github.dbstarll.algeria.boot.model.service;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import lombok.Getter;

import java.time.Duration;
import java.util.StringJoiner;

@Getter
public abstract class TimeData extends BaseModel {
    private static final long serialVersionUID = -8328691395982585249L;

    private long time = System.currentTimeMillis();

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("time=" + getTime());
    }

    /**
     * 检查是否过期.
     *
     * @param duration 有效期
     * @return 是否过期
     */
    public boolean expire(final Duration duration) {
        return System.currentTimeMillis() - time >= duration.toMillis();
    }

    /**
     * 刷新并延长有效期.
     */
    public void renew() {
        this.time = System.currentTimeMillis();
    }
}
