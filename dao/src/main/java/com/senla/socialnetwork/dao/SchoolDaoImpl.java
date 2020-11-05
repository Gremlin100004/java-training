package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SchoolDaoImpl extends AbstractDao<School, Long> implements SchoolDao {
    public SchoolDaoImpl() {
        setType(School.class);
    }

}
