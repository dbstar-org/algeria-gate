package io.github.dbstarll.algeria.boot.component;

import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
class ToneUpdateListener implements ApplicationListener<ApplicationReadyEvent> {
    private final ToneService toneService;

    @Override
    public void onApplicationEvent(@NonNull final ApplicationReadyEvent event) {
        try {
            toneService.update();
        } catch (ApiException | IOException e) {
            log.error("刷新彩铃目录失败", e);
        }
    }
}
