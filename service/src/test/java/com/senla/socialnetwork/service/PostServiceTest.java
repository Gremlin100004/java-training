package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.PostCommentTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@SpringBootTest(classes = TestConfig.class)
public class PostServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private PostService postService;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostCommentDao postCommentDao;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Test
    void PostServiceImpl_getPosts() {
        List<Post> posts = PostTestData.getTestPosts();
        List<PostDto> postsDto = PostTestData.getTestPostsDto();
        Mockito.doReturn(posts).when(postDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostDto> resultPostsDto = postService.getPosts(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostsDto);
        Assertions.assertEquals(PostTestData.getRightNumberPosts(), resultPostsDto.size());
        Assertions.assertFalse(resultPostsDto.isEmpty());
        Assertions.assertEquals(resultPostsDto, postsDto);
        Mockito.verify(postDao, Mockito.times(1)).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_getPostsFromSubscribedCommunities() {
        List<Post> posts = PostTestData.getTestPosts();
        List<PostDto> postsDto = PostTestData.getTestPostsDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(posts).when(postDao).getByEmail(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostDto> resultPostsDto = postService.getPostsFromSubscribedCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostsDto);
        Assertions.assertEquals(PostTestData.getRightNumberPosts(), resultPostsDto.size());
        Assertions.assertFalse(resultPostsDto.isEmpty());
        Assertions.assertEquals(resultPostsDto, postsDto);
        Mockito.verify(postDao, Mockito.times(1)).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_updatePost() {
        Post post = PostTestData.getTestPost();
        PostDto postDto = PostTestData.getTestPostDto();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(post).when(postDao).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertDoesNotThrow(() -> postService.updatePost(postDto));
        Mockito.verify(postDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser() {
        Post post = PostTestData.getTestPost();
        List<PostComment> postComments = PostCommentTestData.getTestPostComments();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(post).when(postDao).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.doReturn(postComments).when(postCommentDao).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> postService.deletePostByUser(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postCommentDao, Mockito.times(1)).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postDao);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser_postDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(postDao).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePostByUser(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postCommentDao, Mockito.never()).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser_postDao_findByIdAndEmail_deletedObject() {
        Post post = PostTestData.getTestPost();
        post.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(postDao).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePostByUser(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postCommentDao, Mockito.never()).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePost() {
        Post post = PostTestData.getTestPost();
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());

        Assertions.assertDoesNotThrow(() -> postService.deletePost(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).deleteRecord(post);
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePost_postDao_findById_nullObject() {
        Post post = PostTestData.getTestPost();
        Mockito.doReturn(null).when(postDao).findById(PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePost(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.never()).deleteRecord(post);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_getPostComments() {
        List<PostComment> posts = PostCommentTestData.getTestPostComments();
        List<PostCommentDto> postsDto = PostCommentTestData.getTestPostCommentsDto();
        Mockito.doReturn(posts).when(postCommentDao).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostCommentDto> resultPostCommentsDto = postService.getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostCommentsDto);
        Assertions.assertEquals(PostCommentTestData.getRightNumberPostComments(), resultPostCommentsDto.size());
        Assertions.assertFalse(resultPostCommentsDto.isEmpty());
        Assertions.assertEquals(resultPostCommentsDto, postsDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_addComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentForCreateDto postCommentDto = PostCommentTestData.getTestPostCommentForCreationDto();
        Post post = PostTestData.getTestPost();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(postComment).when(postCommentDao).save(ArgumentMatchers.any(PostComment.class));
        Mockito.doReturn(post).when(postDao).findById( PostTestData.getPostId());

        PostCommentDto resultPostCommentDto = postService.addComment(
            PostCommentTestData.getPostCommentId(), postCommentDto);
        Assertions.assertNotNull(resultPostCommentDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).save(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.reset(postCommentDao);
        Mockito.reset(postDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PostCommentServiceImpl_addComment_postDao_findByIdAndEmail_nullObject() {
        PostCommentForCreateDto postCommentDto = PostCommentTestData.getTestPostCommentForCreationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(postDao).findById(PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.addComment(
            PostCommentTestData.getPostCommentId(), postCommentDto));
        Mockito.verify(postCommentDao, Mockito.never()).save(ArgumentMatchers.any(PostComment.class));
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.reset(postDao);
    }

    @Test
    void PostCommentServiceImpl_addComment_postDao_findByIdAndEmail_deletedObject() {
        Post post = PostTestData.getTestPost();
        post.setIsDeleted(true);
        PostCommentForCreateDto postCommentDto = PostCommentTestData.getTestPostCommentForCreationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.addComment(
            PostCommentTestData.getPostCommentId(), postCommentDto));
        Mockito.verify(postCommentDao, Mockito.never()).save(ArgumentMatchers.any(PostComment.class));
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.reset(postDao);
    }

}
