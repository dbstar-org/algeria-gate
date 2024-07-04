package io.github.dbstarll.algeria.boot.jpa.repository;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface GameRepository extends JpaRepository<Game, UUID> {
}
