package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PostCommentMapper;
import com.senla.socialnetwork.service.mapper.PostMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class PostServiceImpl implements PostService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostCommentDao postCommentDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PostDto> getPosts(final int firstResult, final int maxResults) {
        return PostMapper.getPostDto(postDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PostDto> getPostsFromSubscribedCommunities(final int firstResult, final int maxResults) {
        return PostMapper.getPostDto(postDao.getByEmail(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updatePost(final PostDto postDto) {
        postDao.updateRecord(PostMapper.getPost(postDto, postDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deletePostByUser(final Long postId) {
        Post post = postDao.findByIdAndEmail(PrincipalUtil.getUserName(), postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        } else if (post.getIsDeleted()) {
            throw new BusinessException("Error, the post has already been deleted");
        }
        post.setIsDeleted(true);
        List<PostComment> comments = postCommentDao.getPostComments(postId, FIRST_RESULT, MAX_RESULTS);
        comments.forEach(postComment -> postComment.setIsDeleted(true));
        post.setPostComments(comments);
        postDao.updateRecord(post);
    }

    @Override
    @Transactional
    public void deletePost(final Long postId) {
        Post post = postDao.findById(postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        }
        postDao.deleteRecord(post);
    }

    @Override
    @Transactional
    public List<PostCommentDto> getPostComments(final Long postId, final int firstResult, final int maxResults) {
        return PostCommentMapper.getPostCommentDto(postCommentDao.getPostComments(postId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PostCommentDto addComment(final Long postId, final PostCommentForCreateDto postCommentDto) {
        Post post = postDao.findById(postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        } else if (post.getIsDeleted()) {
            throw new BusinessException("Error, the post has already been deleted");
        }
        return PostCommentMapper.getPostCommentDto(postCommentDao.save(PostCommentMapper.getPostNewComment(
            postCommentDto, post, userProfileDao.findByEmail(PrincipalUtil.getUserName()))));
    }

}
