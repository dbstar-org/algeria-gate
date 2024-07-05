package io.github.dbstarll.algeria.boot.jpa.repository;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface GameRepository extends JpaRepository<Game, UUID> {
    /**
     * 分页查询游戏列表.
     *
     * @param vip      是否VIP游戏
     * @param pageable 分页参数
     * @return 游戏列表
     */
    Page<Game> findAllByVip(boolean vip, Pageable pageable);
}
