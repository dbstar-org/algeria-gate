package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.utils.net.api.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ToneService {
    /**
     * 更新彩铃列表.
     *
     * @return 本次更新的铃音列表条数.
     * @throws IOException  in case of a problem or the connection was aborted
     * @throws ApiException in case of an api error
     */
    int update() throws IOException, ApiException;

    /**
     * 分页查询铃音列表.
     *
     * @param pageable 分页参数
     * @return Page of ToneInfo
     */
    Page<ToneInfo> list(Pageable pageable);
}
