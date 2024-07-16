package io.github.dbstarll.algeria.boot.jpa.repository;

import io.github.dbstarll.algeria.boot.jpa.entity.UserGame;
import io.github.dbstarll.algeria.boot.jpa.entity.upk.UserGameUpk;
import io.github.dbstarll.algeria.boot.model.UserGameDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserGameRepository extends JpaRepository<UserGame, UserGameUpk> {
    /**
     * 按手机查询下载的游戏列表.
     *
     * @param phone    手机号码
     * @param vip      是否VIP游戏
     * @param pageable 分页参数
     * @return Page of UserGameDetail
     */
    @Query("select new io.github.dbstarll.algeria.boot.model.UserGameDetail(ug,g)"
            + " from UserGame ug left join Game g on g.id=ug.id.gameId"
            + " where g.id is not null and ug.id.phone=?1 and g.vip=?2")
    Page<UserGameDetail> findAllByPhoneAndVip(String phone, boolean vip, Pageable pageable);
}
