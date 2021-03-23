package com.senla.socialnetwork;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.senla.socialnetwork.dao", "com.senla.socialnetwork.aspect"})
@EntityScan(basePackages = "com.senla.socialnetwork.model")
public class TestApplication {

}
