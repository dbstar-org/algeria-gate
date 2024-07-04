package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@RequiredArgsConstructor
class ToneServiceImpl implements ToneService {
    private final List<ToneInfo> tones = new ArrayList<>();
    private final ReentrantReadWriteLock toneLock = new ReentrantReadWriteLock();

    private final RbtApi rbtApi;

    @Override
    public int update() throws IOException, ApiException {
        final List<ToneInfo> toneInfos = rbtApi.tone().query("1").getToneInfos();
        toneLock.writeLock().lock();
        try {
            tones.clear();
            tones.addAll(toneInfos);
            return tones.size();
        } finally {
            toneLock.writeLock().unlock();
        }
    }

    private List<ToneInfo> list() {
        toneLock.readLock().lock();
        try {
            return new ArrayList<>(tones);
        } finally {
            toneLock.readLock().unlock();
        }
    }

    @Override
    public Page<ToneInfo> list(final Pageable pageable) {
        final List<ToneInfo> list = list();
        final int fromIndex = Math.min(list.size(), (int) pageable.getOffset());
        final int toIndex = Math.min(list.size(), fromIndex + pageable.getPageSize());
        return PageableExecutionUtils.getPage(list.subList(fromIndex, toIndex), pageable, list::size);
    }
}
