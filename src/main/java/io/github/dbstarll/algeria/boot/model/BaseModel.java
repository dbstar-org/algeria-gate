package io.github.dbstarll.algeria.boot.model;

import java.io.Serializable;
import java.util.StringJoiner;

public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = -6494617221570899976L;

    @Override
    public final String toString() {
        return addToStringEntry(new StringJoiner(", ", getClass().getSimpleName() + "[", "]")).toString();
    }

    /**
     * 为toString添加成员属性信息.
     *
     * @param joiner StringJoiner from super
     * @return StringJoiner
     */
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return joiner;
    }
}
