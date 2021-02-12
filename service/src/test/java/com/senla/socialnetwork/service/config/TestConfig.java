package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dao.WeatherConditionDao;
import com.senla.socialnetwork.dao.connection.DatabaseConnection;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

@Configuration
@ComponentScan("com.senla.socialnetwork.service")
public class TestConfig {

    @Bean
    CommunityDao communityDao() {
        return Mockito.mock(CommunityDao.class);
    }

    @Bean
    LocationDao locationDao() {
        return Mockito.mock(LocationDao.class);
    }

    @Bean
    PostCommentDao postCommentDao() {
        return Mockito.mock(PostCommentDao.class);
    }

    @Bean
    PostDao postDao() {
        return Mockito.mock(PostDao.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    PrivateMessageDao privateMessageDao() {
        return Mockito.mock(PrivateMessageDao.class);
    }

    @Bean
    PublicMessageCommentDao publicMessageCommentDao() {
        return Mockito.mock(PublicMessageCommentDao.class);
    }

    @Bean
    PublicMessageDao publicMessageDao() {
        return Mockito.mock(PublicMessageDao.class);
    }

    @Bean
    SchoolDao schoolDao() {
        return Mockito.mock(SchoolDao.class);
    }

    @Bean
    UniversityDao universitydao() {
        return Mockito.mock(UniversityDao.class);
    }

    @Bean
    UserDao userDao() {
        return Mockito.mock(UserDao.class);
    }

    @Bean
    UserProfileDao userProfileDao() {
        return Mockito.mock(UserProfileDao.class);
    }

    @Bean
    WeatherConditionDao weatherConditionDao() {
        return Mockito.mock(WeatherConditionDao.class);
    }

    @Bean
    HttpServletRequest request() {
        return Mockito.mock(HttpServletRequest.class);
    }

    @Bean
    SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    DatabaseConnection databaseConnection() {
        return Mockito.mock(DatabaseConnection.class);
    }

}