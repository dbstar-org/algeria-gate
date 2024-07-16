package io.github.dbstarll.algeria.boot.jpa.entity;

import io.github.dbstarll.algeria.boot.jpa.entity.base.BaseEmbeddedIdentifyEntity;
import io.github.dbstarll.algeria.boot.jpa.entity.upk.UserGameUpk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(appliesTo = "ag_user_game", comment = "用户游戏表")
@Getter
@Setter
@NoArgsConstructor
public final class UserGame extends BaseEmbeddedIdentifyEntity<UserGameUpk> {
    private static final long serialVersionUID = 7820642091569387805L;

    @Comment("下载次数")
    @Column(nullable = false)
    private int downloadCount;

    /**
     * 构造UserGame.
     *
     * @param id UserGameUpk
     */
    public UserGame(final UserGameUpk id) {
        super(id);
    }
}
