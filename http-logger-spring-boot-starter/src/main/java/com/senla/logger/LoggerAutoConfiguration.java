package com.senla.logger;

import com.senla.logger.filter.RequestResponseLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class LoggerAutoConfiguration {

    @Bean
    public RequestResponseLoggingFilter loggingFilter() {
        return new RequestResponseLoggingFilter();
    }
}
