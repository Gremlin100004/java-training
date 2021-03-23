package com.senla.socialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.socialnetwork.controller.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = TestConfig.class)
public class AbstractControllerTest {
    protected static final int FIRST_RESULT = 0;
    protected static final int MAX_RESULTS = 0;
    protected static final String FIRST_RESULT_PARAM_NAME = "firstResult";
    protected static final String MAX_RESULTS_PARAM_NAME = "maxResults";
    protected static final String PATH_SEPARATOR = "/";
    protected static final String ACCESS_ERROR_MESSAGE = "Error, you do not have access rights";
    protected static final String SECURITY_ERROR_MESSAGE = "Permission denied";
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;

}
