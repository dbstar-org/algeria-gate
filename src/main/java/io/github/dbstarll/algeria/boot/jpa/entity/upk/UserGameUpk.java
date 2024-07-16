package io.github.dbstarll.algeria.boot.jpa.entity.upk;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class UserGameUpk extends BaseModel {
    private static final long serialVersionUID = -7698641322315252441L;
    private static final int LENGTH_OF_MOBILE = 50;

    @Comment("用户手机号")
    @Column(nullable = false, updatable = false, length = LENGTH_OF_MOBILE)
    private String phone;

    @Column(nullable = false, updatable = false, length = Uuid.LENGTH_OF_UUID_KEY)
    @Type(type = Uuid.TYPE)
    @Comment("账号ID")
    private UUID gameId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserGameUpk)) {
            return false;
        }
        final UserGameUpk labelUpk = (UserGameUpk) o;
        return new EqualsBuilder()
                .append(getPhone(), labelUpk.getPhone())
                .append(getGameId(), labelUpk.getGameId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPhone())
                .append(getGameId())
                .toHashCode();
    }
}
