package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;

import java.io.IOException;
import java.util.zip.ZipFile;

public interface GameService {
    /**
     * 从zip文件来创建游戏.
     *
     * @param zipFile zip压缩文件
     * @return 新建的游戏.
     */
    Game create(ZipFile zipFile) throws IOException;
}
