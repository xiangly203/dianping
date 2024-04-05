package org.example.dianping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.pulsar.annotation.EnablePulsar;

@EnableCaching
@EnablePulsar
@SpringBootApplication
@MapperScan("org.example.dianping.mapper")
public class DianpingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DianpingApplication.class, args);
    }

}
