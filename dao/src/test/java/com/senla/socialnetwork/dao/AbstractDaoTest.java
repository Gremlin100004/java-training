package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.testdata.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@EnableJpaRepositories
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class AbstractDaoTest {
    @Autowired
    protected TestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        testDataUtil.fillWithData();
    }

    @AfterEach
    void clearDatabase() {
        testDataUtil.clearData();
    }

}
