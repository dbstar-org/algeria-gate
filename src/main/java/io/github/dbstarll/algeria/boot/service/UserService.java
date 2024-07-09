package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.model.service.Session;
import io.github.dbstarll.utils.net.api.ApiException;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    /**
     * 登录并返回AccessToken.
     *
     * @param phone 手机号码
     * @return AccessToken and Session
     * @throws IOException  in case of a problem or the connection was aborted
     * @throws ApiException in case of an api error
     */
    Map.Entry<UUID, Session> login(String phone) throws IOException, ApiException;

    /**
     * 验证AccessToken是否有效并返回session.
     *
     * @param token AccessToken
     * @param renew 是否刷新session有效期
     * @return Session
     */
    Session verify(UUID token, boolean renew);

    /**
     * 更新session中的用户信息.
     *
     * @param token AccessToken
     * @return Session
     * @throws IOException  in case of a problem or the connection was aborted
     * @throws ApiException in case of an api error
     */
    Session update(UUID token) throws IOException, ApiException;

    /**
     * 退出登录并返回session.
     *
     * @param token AccessToken
     * @return Session
     */
    Session logout(UUID token);

    /**
     * 检查是否已订阅彩铃.
     *
     * @param session Session
     * @param vip     是否VIP
     * @return 是否已订阅彩铃
     */
    boolean isSubscribe(Session session, boolean vip);
}
