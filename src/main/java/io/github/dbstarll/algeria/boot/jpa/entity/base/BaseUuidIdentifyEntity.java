package io.github.dbstarll.algeria.boot.jpa.entity.base;

import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author dbstar
 */
@MappedSuperclass
public abstract class BaseUuidIdentifyEntity extends BaseTimeEntity {
    private static final long serialVersionUID = -5520578534627195147L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = Uuid.GENERATOR)
    @Column(nullable = false, updatable = false, length = Uuid.LENGTH_OF_UUID_KEY)
    @Type(type = Uuid.TYPE)
    @Comment("ID")
    private UUID id;

    /**
     * 获得主键ID.
     *
     * @return 主键ID
     */
    public final UUID getId() {
        return id;
    }
}
