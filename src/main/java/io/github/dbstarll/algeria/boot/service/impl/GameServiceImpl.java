package io.github.dbstarll.algeria.boot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.jpa.repository.GameRepository;
import io.github.dbstarll.algeria.boot.service.GameService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.lang.wrapper.EntryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Predicates;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private static final String ROOT_IMAGE = "images";

    private final ObjectMapper mapper;
    private final GameRepository gameRepository;
    private final AlgeriaGateProperties algeriaGateProperties;

    @Override
    public Map.Entry<Game, File> create(final ZipFile zipFile, final boolean vip) throws IOException {
        final Map<String, ZipEntry> entries = zipFile.stream().filter(Predicates.negate(ZipEntry::isDirectory))
                .filter(e -> !e.getName().startsWith("__MACOSX"))
                .collect(Collectors.toMap(e -> StringUtils.substringAfterLast(e.getName(), "/"), e -> e));
        final Game game = gameRepository.save(verify(parseFromDetail(zipFile, entries), entries, vip));
        return EntryWrapper.wrap(game, save(game, zipFile, entries));
    }

    @Override
    public File file(final Game game) {
        final File gameRoot = new File(algeriaGateProperties.getGameRoot(), Uuid.toString(game.getId()));
        return new File(gameRoot, game.getBin());
    }

    private Game parseFromDetail(final ZipFile zipFile, final Map<String, ZipEntry> entries) throws IOException {
        final ZipEntry detail = Optional.ofNullable(entries.get(ENTRY_DETAIL_JSON))
                .orElseThrow(() -> new UnsupportedOperationException(ENTRY_DETAIL_JSON + "not found"));
        try (InputStream in = zipFile.getInputStream(detail)) {
            return mapper.readValue(in, Game.class);
        }
    }

    private Game verify(final Game game, final Map<String, ZipEntry> entries, final boolean vip) {
        game.setSize(verifyEntry(entries, game.getBin(), "安装包").getSize());
        verifyEntry(entries, game.getIconBig(), "大图标");
        verifyEntry(entries, game.getIconSmall(), "小图标");
        Optional.ofNullable(game.getScreenshots())
                .orElseThrow(() -> new UnsupportedOperationException("游戏截图 not found"))
                .forEach(s -> verifyEntry(entries, s, "游戏截图"));
        game.setVip(vip);
        return game;
    }

    private ZipEntry verifyEntry(final Map<String, ZipEntry> entries, final String item, final String title) {
        return Optional.ofNullable(entries.get(item))
                .orElseThrow(() -> new UnsupportedOperationException(title + " not found: " + item));
    }

    private File save(final Game game, final ZipFile zipFile, final Map<String, ZipEntry> entries) throws IOException {
        final File gameRoot = new File(algeriaGateProperties.getGameRoot(), Uuid.toString(game.getId()));
        final File imageRoot = new File(gameRoot, ROOT_IMAGE);
        if (imageRoot.mkdirs()) {
            save(gameRoot, zipFile, entries, game.getBin());
            save(imageRoot, zipFile, entries, game.getIconBig());
            save(imageRoot, zipFile, entries, game.getIconSmall());
            for (String screenshot : game.getScreenshots()) {
                save(imageRoot, zipFile, entries, screenshot);
            }
        }
        return gameRoot;
    }

    private void save(final File gameRoot, final ZipFile zipFile, final Map<String, ZipEntry> entries,
                      final String item) throws IOException {
        try (InputStream in = zipFile.getInputStream(entries.get(item))) {
            FileUtils.copyToFile(in, new File(gameRoot, item));
        }
    }
}
