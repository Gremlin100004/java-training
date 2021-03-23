package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SchoolCriteriaApiDaoImpl extends AbstractDao<School, Long> implements SchoolDao {
    public SchoolCriteriaApiDaoImpl() {
        setType(School.class);
    }

}
