package io.github.dbstarll.algeria.boot.jpa.entity;

import io.github.dbstarll.algeria.boot.jpa.entity.base.BaseUuidIdentifyEntity;
import io.github.dbstarll.algeria.boot.json.Json;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(appliesTo = "ag_game", comment = "游戏表")
public final class Game extends BaseUuidIdentifyEntity {
    private static final long serialVersionUID = -5357638803073086279L;

    private static final int LENGTH_OF_INTRO = 4096;

    @Comment("游戏名")
    @Column(nullable = false, updatable = false)
    private String name;

    @Comment("游戏类型")
    @Column(nullable = false, updatable = false)
    private String type;

    @Comment("游戏简介")
    @Column(nullable = false, updatable = false, length = LENGTH_OF_INTRO)
    private String intro;

    @Comment("小图标：148 × 148")
    @Column(nullable = false, updatable = false)
    private String iconSmall;

    @Comment("大图标：190 × 190")
    @Column(nullable = false, updatable = false)
    private String iconBig;

    @Comment("游戏截图")
    @Type(type = Json.TYPE)
    @Column(columnDefinition = "json")
    private List<String> screenshots;
}
