package io.github.dbstarll.algeria.boot.jpa.entity.base;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @param <I> EmbeddedId的类型
 * @author dbstar
 */
@MappedSuperclass
public abstract class BaseEmbeddedIdentifyEntity<I extends Serializable> extends BaseTimeEntity {
    private static final long serialVersionUID = -5876349637893559841L;

    @EmbeddedId
    private I id;

    protected BaseEmbeddedIdentifyEntity() {
    }

    protected BaseEmbeddedIdentifyEntity(final I id) {
        this.id = notNull(id, "id not set");
    }

    /**
     * 获得主键ID.
     *
     * @return 主键ID
     */
    public final I getId() {
        return id;
    }
}
