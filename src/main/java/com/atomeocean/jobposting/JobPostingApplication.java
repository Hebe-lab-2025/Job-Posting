package com.atomeocean.jobposting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口：
 * - 负责启动 Spring Boot 应用
 * - 扫描当前包及子包中的 @Component / @Service / @Repository / @RestController 等组件
 *
 * 在面试里你可以说：
 * This is a standard Spring Boot bootstrap class that wires up the
 * job posting backend (controllers, services, repositories, MongoDB config, etc.).
 */
@SpringBootApplication
public class JobPostingApplication {

    /**
     * main 方法启动内嵌的 Tomcat，并初始化 Spring 上下文。
     */
    public static void main(String[] args) {
        SpringApplication.run(JobPostingApplication.class, args);
    }
}

