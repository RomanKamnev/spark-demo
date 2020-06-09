package com.example.demo.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public SparkSession getSparkSession(){

        return SparkSession
                .builder()
                .appName("SparkJavaExample")
                .master("local[3]")
                .config("spark.driver.bindAddress", "127.0.0.1")
                .getOrCreate();
    }
}
