package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.University;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UniversityDaoImpl extends AbstractDao<University, Long> implements UniversityDao {
    public UniversityDaoImpl() {
        setType(University.class);
    }

}
