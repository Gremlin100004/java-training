package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PostCommentMapper;
import com.senla.socialnetwork.service.util.PostMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    PostDao postDao;
    @Autowired
    PostCommentDao postCommentDao;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Override
    @Transactional
    public List<PostDto> getPosts(final int firstResult, final int maxResults) {
        log.debug("[getPosts]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PostDto> getPostsFromSubscribedCommunities(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[getPostsFromSubscribedCommunities]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updatePost(final PostDto postDto) {
        log.debug("[updatePosts]");
        log.debug("[postDto: {}]", postDto);
        postDao.updateRecord(PostMapper.getPost(
            postDto, postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deletePostByUser(final String email, final Long postId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[postId: {}]", postId);
        Post post = postDao.findByIdAndEmail(email, postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        } else if (post.isDeleted()) {
            throw new BusinessException("Error, the post has already been deleted");
        }
        post.setDeleted(true);
        List<PostComment> comments = postCommentDao.getPostComments(postId, FIRST_RESULT, MAX_RESULTS);
        comments.forEach(postComment -> postComment.setDeleted(true));
        post.setPostComments(comments);
        postDao.updateRecord(post);
    }

    @Override
    @Transactional
    public void deletePost(final Long postId) {
        log.debug("[deleteSchool]");
        log.debug("[postId: {}]", postId);
        Post post = postDao.findById(postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        }
        postDao.deleteRecord(postId);
    }

    @Override
    @Transactional
    public List<PostCommentDto> getPostComments(final Long postId, final int firstResult, final int maxResults) {
        log.debug("[getPostComments]");
        log.trace("[postId: {}, firstResult: {}, maxResults: {}]", postId, firstResult, maxResults);
        return PostCommentMapper.getPostCommentDto(postCommentDao.getPostComments(postId, firstResult, maxResults));
    }

}
