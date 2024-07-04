package io.github.dbstarll.algeria.boot;

import io.github.dbstarll.utils.spring.boot.BootLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(modifyOnCreate = false)
public class Starter extends BootLauncher {
    /**
     * 启动类.
     *
     * @param args 命令行参数
     */
    public static void main(final String[] args) {
        new Starter().run("algeria", "algeria-gate", args);
    }
}
