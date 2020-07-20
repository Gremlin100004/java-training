package com.senla.carservice.config;

import com.senla.carservice.container.annotation.Config;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryOtherImpl;

import java.util.HashMap;
import java.util.Map;

@Config
public class ConfigFile {
    private Map<String, Class<?>> implementationClasses = new HashMap<>(){{
        put(MasterRepository.class.getName(), MasterRepositoryOtherImpl.class);
    }};
}