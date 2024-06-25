package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.utils.net.api.ApiException;

import java.io.IOException;
import java.util.List;

public interface ToneService {
    int update() throws IOException, ApiException;

    List<ToneInfo> list();
}
