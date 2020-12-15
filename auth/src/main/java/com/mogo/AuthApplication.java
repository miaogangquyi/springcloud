package com.mogo;

/**
 * @Author: miaogang
 * @Date: 2020年12月14日
 * @Description: 权限
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @author miaogang
 */
@SpringBootApplication
@Slf4j
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuthApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("Auth 启动成功");
        log.info("Auth 地址\tHttp://127.0.0.1:{}",env.getProperty("server.port"));
    }
}

