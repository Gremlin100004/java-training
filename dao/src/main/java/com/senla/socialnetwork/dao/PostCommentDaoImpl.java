package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PostComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PostCommentDaoImpl extends AbstractDao<PostComment, Long> implements PostCommentDao {
    public PostCommentDaoImpl() {
        setType(PostComment.class);
    }

}
