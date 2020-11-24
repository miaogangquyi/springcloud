package com.mogo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

/**
 * @author miaogang
 */

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("启动成功");
        log.info("System地址\tHttp://127.0.0.1:{}",env.getProperty("server.port"));
    }


}
