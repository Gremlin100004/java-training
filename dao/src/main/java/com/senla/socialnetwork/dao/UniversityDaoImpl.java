package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.University;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityDaoImpl extends AbstractDao<University, Long> implements UniversityDao {
    public UniversityDaoImpl() {
        setType(University.class);
    }

}
