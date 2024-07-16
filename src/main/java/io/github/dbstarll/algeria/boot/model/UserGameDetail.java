package io.github.dbstarll.algeria.boot.model;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.jpa.entity.UserGame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class UserGameDetail extends BaseModel {
    private static final long serialVersionUID = -4499109453520504559L;

    private final UserGame userGame;
    private final Game game;
}
