package io.github.dbstarll.algeria.boot.jpa.repository;

import io.github.dbstarll.algeria.boot.jpa.entity.UserGame;
import io.github.dbstarll.algeria.boot.jpa.entity.upk.UserGameUpk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserGameRepository extends JpaRepository<UserGame, UserGameUpk> {
}
