package io.github.dbstarll.algeria.boot.jpa.entity.base;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 3938938193260280358L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("创建时间")
    private long createTime;

    @LastModifiedDate
    @Column(insertable = false)
    @Comment("最后修改时间")
    private Long updateTime;

    /**
     * 获得创建时间.
     *
     * @return 创建时间
     */
    public final long getCreateTime() {
        return createTime;
    }

    /**
     * 获得最后修改时间.
     *
     * @return 最后修改时间
     */
    public final Long getUpdateTime() {
        return updateTime;
    }
}
