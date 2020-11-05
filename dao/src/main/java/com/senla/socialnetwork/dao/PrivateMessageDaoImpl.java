package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PrivateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PrivateMessageDaoImpl extends AbstractDao<PrivateMessage, Long> implements PrivateMessageDao {
    public PrivateMessageDaoImpl() {
        setType(PrivateMessage.class);
    }

}
