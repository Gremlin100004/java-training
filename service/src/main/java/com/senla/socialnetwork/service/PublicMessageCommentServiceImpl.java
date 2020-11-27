package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.PublicMessageCommentMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PublicMessageCommentServiceImpl implements PublicMessageCommentService {
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    PublicMessageDao publicMessageDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getComments(final int firstResult, final int maxResults) {
        log.debug("[Comments]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageCommentDto addComment(final HttpServletRequest request,
                                              final PublicMessageCommentForCreateDto publicMessageCommentDto) {
        log.debug("[addComment]");
        log.debug("[request: {}, publicMessageCommentDto: {}]", request, publicMessageCommentDto);
        PublicMessageComment publicMessageComment = PublicMessageCommentMapper.getNewPublicMessageComment(
            publicMessageCommentDto, publicMessageDao,  userProfileDao, locationDao, schoolDao, universityDao);
        publicMessageComment.setAuthor(userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey)));
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.saveRecord(publicMessageComment));
    }

    @Override
    @Transactional
    public void updateComment(final HttpServletRequest request, final PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[updateComment]");
        log.debug("[request: {}, publicMessageCommentDto: {}]", request, publicMessageCommentDto);
        UserProfile userProfile = userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey));
        PublicMessageComment publicMessageComment = PublicMessageCommentMapper.getPublicMessageComment(
            publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao,
            locationDao, schoolDao, universityDao);
        if (publicMessageComment.getAuthor() != userProfile) {
            throw new BusinessException("Error, this comment does not belong to this profile");
        }
        publicMessageCommentDao.updateRecord(publicMessageComment);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final HttpServletRequest request, final Long commentId) {
        log.debug("[deleteCommentByUser]");
        log.debug("[request: {}, commentId: {}]", request, commentId);
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findByIdAndEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), commentId);
        if (publicMessageComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (publicMessageComment.isDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        publicMessageComment.setDeleted(true);
        publicMessageCommentDao.updateRecord(publicMessageComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        log.debug("[deleteComment]");
        log.debug("[commentId: {}]", commentId);
        if (publicMessageCommentDao.findById(commentId) == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        publicMessageCommentDao.deleteRecord(commentId);
    }
}
