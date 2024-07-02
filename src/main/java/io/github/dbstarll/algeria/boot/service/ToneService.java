package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.utils.net.api.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ToneService {
    int update() throws IOException, ApiException;

    Page<ToneInfo> list(Pageable pageable);
}
