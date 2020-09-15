package com.senla.carservice.ui;

import com.senla.carservice.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.senla.carservice")
@PropertySource("classpath:application.properties")
public class CarServiceApp {

    @Bean
    public LocalEntityManagerFactoryBean emf() {
        LocalEntityManagerFactoryBean result = new LocalEntityManagerFactoryBean();
        result.setPersistenceUnitName("CarServiceJpa");
        return result;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager result = new JpaTransactionManager();
        result.setEntityManagerFactory(emf().getObject());
        return result;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarServiceApp.class);
        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}