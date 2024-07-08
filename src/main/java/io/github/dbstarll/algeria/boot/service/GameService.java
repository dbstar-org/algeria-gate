package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipFile;

public interface GameService {
    /**
     * 从zip文件来创建游戏.
     *
     * @param zipFile zip压缩文件
     * @return 新建的游戏.
     */
    Map.Entry<Game, File> create(ZipFile zipFile) throws IOException;

    /**
     * 获得游戏介质文件流.
     *
     * @param game 游戏
     * @return 游戏介质文件流
     */
    InputStream file(Game game) throws IOException;
}
