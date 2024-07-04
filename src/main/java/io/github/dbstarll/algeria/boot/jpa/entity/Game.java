package io.github.dbstarll.algeria.boot.jpa.entity;

import io.github.dbstarll.algeria.boot.jpa.entity.base.BaseUuidIdentifyEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

@Entity
@Table(appliesTo = "ag_game", comment = "游戏表")
public final class Game extends BaseUuidIdentifyEntity {
    private static final long serialVersionUID = -5357638803073086279L;
}
