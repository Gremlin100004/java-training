package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PostCommentMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentDao postCommentDao;

    @Override
    @Transactional
    public List<PostCommentDto> getComments(final int firstResult, final int maxResults) {
        return PostCommentMapper.getPostCommentDto(postCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updateComment(final PostCommentDto postCommentDto) {
        postCommentDao.updateRecord(PostCommentMapper.getPostComment(
            postCommentDto, postCommentDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final Long commentId) {
        PostComment postComment = postCommentDao.findByIdAndEmail(PrincipalUtil.getUserName(), commentId);
        if (postComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (postComment.getIsDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        postComment.setIsDeleted(true);
        postCommentDao.updateRecord(postComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        PostComment postComment = postCommentDao.findById(commentId);
        if (postComment == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        postCommentDao.deleteRecord(postComment);
    }

}
