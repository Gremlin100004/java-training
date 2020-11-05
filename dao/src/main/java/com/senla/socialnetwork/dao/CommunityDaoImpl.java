package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CommunityDaoImpl extends AbstractDao<Community, Long> implements CommunityDao {
    public CommunityDaoImpl() {
        setType(Community.class);
    }

}
