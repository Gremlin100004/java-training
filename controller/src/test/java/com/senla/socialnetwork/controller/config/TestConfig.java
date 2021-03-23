package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.service.CommunityService;
import com.senla.socialnetwork.service.LocationService;
import com.senla.socialnetwork.service.PostCommentService;
import com.senla.socialnetwork.service.PostService;
import com.senla.socialnetwork.service.PrivateMessageService;
import com.senla.socialnetwork.service.PublicMessageCommentService;
import com.senla.socialnetwork.service.PublicMessageService;
import com.senla.socialnetwork.service.SchoolService;
import com.senla.socialnetwork.service.UniversityService;
import com.senla.socialnetwork.service.UserProfileService;
import com.senla.socialnetwork.service.UserService;
import com.senla.socialnetwork.service.WeatherConditionService;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootConfiguration
@ComponentScan("com.senla.socialnetwork.controller")
public class TestConfig {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    CommunityService communityService() {
        return Mockito.mock(CommunityService.class);
    }

    @Bean
    LocationService locationService() {
        return Mockito.mock(LocationService.class);
    }

    @Bean
    PostCommentService postCommentService() {
        return Mockito.mock(PostCommentService.class);
    }

    @Bean
    PostService postService() {
        return Mockito.mock(PostService.class);
    }

    @Bean
    PrivateMessageService privateMessageService() {
        return Mockito.mock(PrivateMessageService.class);
    }

    @Bean
    PublicMessageCommentService publicMessageCommentService() {
        return Mockito.mock(PublicMessageCommentService.class);
    }

    @Bean
    PublicMessageService publicMessageService() {
        return Mockito.mock(PublicMessageService.class);
    }

    @Bean
    SchoolService schoolService() {
        return Mockito.mock(SchoolService.class);
    }

    @Bean
    UniversityService universityService() {
        return Mockito.mock(UniversityService.class);
    }

    @Bean
    UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    UserProfileService userProfileService() {
        return Mockito.mock(UserProfileService.class);
    }

    @Bean
    WeatherConditionService weatherConditionService() {
        return Mockito.mock(WeatherConditionService.class);
    }

    @Bean
    UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

}
