package com.springdemo.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.springdemo.coach", "com.springdemo.fortuneteller", "com.springdemo.log"})
@PropertySource("classpath:coach2.properties")
public class BeanScanConfigurationUsesComponentScanAnnotation {
}