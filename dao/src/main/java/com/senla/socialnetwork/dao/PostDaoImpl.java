package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PostDaoImpl extends AbstractDao<Post, Long> implements PostDao {
    public PostDaoImpl() {
        setType(Post.class);
    }

}
