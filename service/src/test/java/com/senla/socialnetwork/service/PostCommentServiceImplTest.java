package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.PostCommentTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PostCommentServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    PostCommentService postCommentService;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    PostCommentDao postCommentDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    private HttpServletRequest request;
    @Value("${com.senla.socialnetwork.service.util.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Test
    void PostCommentServiceImpl_getComments() {
        List<PostComment> postComments = PostCommentTestData.getTestPostComments();
        List<PostCommentDto> postCommentsDto = PostCommentTestData.getTestPostCommentsDto();
        Mockito.doReturn(postComments).when(postCommentDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostCommentDto> resultPostCommentsDto = postCommentService.getComments(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostCommentsDto);
        Assertions.assertEquals(PostCommentTestData.getRightNumberPostComments(), resultPostCommentsDto.size());
        Assertions.assertFalse(resultPostCommentsDto.isEmpty());
        Assertions.assertEquals(resultPostCommentsDto, postCommentsDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_updateComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(postComment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertDoesNotThrow(() -> postCommentService.updateComment(request, postCommentDto));
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.reset(postCommentDao);
        Mockito.reset(postDao);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PostCommentServiceImpl_updateComment_someoneElseComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfile wrongUserProfile = UserProfileTestData.getTestUserProfile();
        wrongUserProfile.setId(UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(postComment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(wrongUserProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.updateComment(request, postCommentDto));
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.reset(postCommentDao);
        Mockito.reset(postDao);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteCommentByUser(
            request, PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            request, PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_deletedObject() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        postComment.setIsDeleted(true);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            request, PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment() {
        PostComment comment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(comment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteComment(PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).deleteRecord(comment);
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment_postCommentDao_findById_nullObject() {
        PostComment comment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(null).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteComment(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(
            PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).deleteRecord(comment);
        Mockito.reset(postCommentDao);
    }

}
