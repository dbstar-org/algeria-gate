package io.github.dbstarll.algeria.boot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.jpa.repository.GameRepository;
import io.github.dbstarll.algeria.boot.service.GameService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Predicates;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@RequiredArgsConstructor
class GameServiceImpl implements GameService {
    private static final String ENTRY_DETAIL_JSON = "detail.json";

    private final ObjectMapper mapper;
    private final GameRepository gameRepository;

    @Override
    public Game create(final ZipFile zipFile) throws IOException {
        final Map<String, ZipEntry> entries = zipFile.stream().filter(Predicates.negate(ZipEntry::isDirectory))
                .filter(e -> !e.getName().startsWith("__MACOSX"))
                .collect(Collectors.toMap(e -> StringUtils.substringAfterLast(e.getName(), "/"), e -> e));
        return gameRepository.save(verify(parseFromDetail(zipFile, entries), entries));
    }

    private Game parseFromDetail(final ZipFile zipFile, final Map<String, ZipEntry> entries) throws IOException {
        final ZipEntry detail = Optional.ofNullable(entries.get(ENTRY_DETAIL_JSON))
                .orElseThrow(() -> new UnsupportedOperationException(ENTRY_DETAIL_JSON + "not found"));
        try (InputStream in = zipFile.getInputStream(detail)) {
            return mapper.readValue(in, Game.class);
        }
    }

    private Game verify(final Game game, final Map<String, ZipEntry> entries) {
        game.setSize(verifyEntry(entries, game.getBin(), "安装包").getSize());
        verifyEntry(entries, game.getIconBig(), "大图标");
        verifyEntry(entries, game.getIconSmall(), "小图标");
        Optional.ofNullable(game.getScreenshots()).ifPresent(screenshots ->
                screenshots.forEach(s -> verifyEntry(entries, s, "游戏截图")));
        return game;
    }

    private ZipEntry verifyEntry(final Map<String, ZipEntry> entries, final String item, final String title) {
        return Optional.ofNullable(entries.get(item)).filter(e -> e.getSize() > 0)
                .orElseThrow(() -> new UnsupportedOperationException(title + " not found: " + item));
    }
}
