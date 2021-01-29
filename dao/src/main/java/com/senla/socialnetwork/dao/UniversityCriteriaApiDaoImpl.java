package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.University;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityCriteriaApiDaoImpl extends AbstractDao<University, Long> implements UniversityDao {
    public UniversityCriteriaApiDaoImpl() {
        setType(University.class);
    }

}
