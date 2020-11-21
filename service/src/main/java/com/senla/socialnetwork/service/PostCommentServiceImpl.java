package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PostCommentMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    PostCommentDao postCommentDao;
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
    public List<PostCommentDto> getComments(final int firstResult, final int maxResults) {
        log.debug("[getComments]");
        return PostCommentMapper.getPostCommentDto(postCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public PostCommentDto addComment(final PostCommentDto postCommentDto) {
        log.debug("[addComment]");
        log.debug("[postCommentDto: {}]", postCommentDto);
        return PostCommentMapper.getPostCommentDto(postCommentDao.saveRecord(PostCommentMapper.getPostComment(
            postCommentDto, postCommentDao, postDao, communityDao, userProfileDao,
            locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateComment(final PostCommentDto postCommentDto) {
        log.debug("[updateComment]");
        log.debug("[postCommentDto: {}]", postCommentDto);
        postCommentDao.updateRecord(PostCommentMapper.getPostComment(
            postCommentDto, postCommentDao, postDao, communityDao, userProfileDao,
            locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final String email, final Long commentId) {
        log.debug("[deleteCommentByUser]");
        log.debug("[email: {}, commentId: {}]", email, commentId);
        PostComment postComment = postCommentDao.findByIdAndEmail(email, commentId);
        if (postComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (postComment.isDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        postComment.setDeleted(true);
        postCommentDao.updateRecord(postComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        log.debug("[deleteComment]");
        log.debug("[commentId: {}]", commentId);
        if (postCommentDao.findById(commentId) == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        postCommentDao.deleteRecord(commentId);
    }

}
