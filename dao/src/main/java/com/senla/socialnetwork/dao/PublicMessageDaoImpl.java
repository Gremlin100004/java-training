package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PublicMessageDaoImpl extends AbstractDao<PublicMessage, Long> implements PublicMessageDao {
    public PublicMessageDaoImpl() {
        setType(PublicMessage.class);
    }

}
