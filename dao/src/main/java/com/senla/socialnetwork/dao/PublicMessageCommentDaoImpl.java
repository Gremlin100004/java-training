package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessageComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PublicMessageCommentDaoImpl extends AbstractDao<PublicMessageComment, Long> implements PublicMessageCommentDao {
    public PublicMessageCommentDaoImpl() {
        setType(PublicMessageComment.class);
    }

}
