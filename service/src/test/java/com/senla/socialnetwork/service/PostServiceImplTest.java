package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PostCommentTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PostServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    @Autowired
    PostService postService;
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
        Mockito.doReturn(posts).when(postDao).getByEmail(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostDto> resultPostsDto = postService.getPostsFromSubscribedCommunities(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
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
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> postService.updatePost(postDto));
        Mockito.verify(postDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(postDao);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser() {
        Post post = PostTestData.getTestPost();
        List<PostComment> postComments = PostCommentTestData.getTestPostComments();
        Mockito.doReturn(post).when(postDao).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.doReturn(postComments).when(postCommentDao).getPostComments(PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> postService.deletePostByUser(UserTestData.getEmail(), PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postCommentDao, Mockito.times(1)).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postDao);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser_postDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(postDao).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePostByUser(UserTestData.getEmail(), PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Post.class));
        Mockito.verify(postCommentDao, Mockito.never()).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePostByUser_postDao_findByIdAndEmail_deletedObject() {
        Post post = PostTestData.getTestPost();
        post.setDeleted(true);
        Mockito.doReturn(null).when(postDao).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePostByUser(UserTestData.getEmail(), PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findByIdAndEmail(UserTestData.getEmail(), PostTestData.getPostId());
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
        Mockito.verify(postDao, Mockito.times(1)).deleteRecord(PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_deletePost_postDao_findById_nullObject() {
        Mockito.doReturn(null).when(postDao).findById(PostTestData.getPostId());

        Assertions.assertThrows(BusinessException.class, () -> postService.deletePost(PostTestData.getPostId()));
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(postDao, Mockito.never()).deleteRecord(PostTestData.getPostId());
        Mockito.reset(postDao);
    }

    @Test
    void PostServiceImpl_getPostComments() {
        List<PostComment> posts = PostCommentTestData.getTestPostComments();
        List<PostCommentDto> postsDto = PostCommentTestData.getTestPostCommentsDto();
        Mockito.doReturn(posts).when(postCommentDao).getPostComments(PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostCommentDto> resultPostCommentsDto = postService.getPostComments(PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostCommentsDto);
        Assertions.assertEquals(PostCommentTestData.getRightNumberPostComments(), resultPostCommentsDto.size());
        Assertions.assertFalse(resultPostCommentsDto.isEmpty());
        Assertions.assertEquals(resultPostCommentsDto, postsDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).getPostComments(
            PostTestData.getPostId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postCommentDao);
    }

}
