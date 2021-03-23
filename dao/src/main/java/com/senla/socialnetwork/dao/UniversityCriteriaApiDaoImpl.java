package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.University;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UniversityCriteriaApiDaoImpl extends AbstractDao<University, Long> implements UniversityDao {
    public UniversityCriteriaApiDaoImpl() {
        setType(University.class);
    }

}
