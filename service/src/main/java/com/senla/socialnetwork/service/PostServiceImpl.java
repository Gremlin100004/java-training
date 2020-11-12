package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.exception.BusinessException;
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
    public List<PostDto> getCommunityPosts(Long communityId, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[communityId: {}, firstResult: {}, maxResults: {}]", communityId, firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getByCommunityId(communityId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PostDto> getPostsFromSubscribedCommunities(String email, int firstResult, int maxResults) {
        log.debug("[getPostsFromSubscribedCommunities]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public void addPostToCommunity(String email, PostDto postDto, Long communityId) {
        log.debug("[addPosts]");
        log.debug("[email: {}, postDto: {}, communityId: {}]", email,  postDto, communityId);
        if (postDto == null) {
            throw new BusinessException("Error, null post");
        }
        Community community = communityDao.findByIdAndEmail(email, communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        }
        List<Post> posts = postDao.getByCommunityId(communityId, FIRST_RESULT, MAX_RESULTS);
        posts.add(PostMapper.getPost(
            postDto, postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
        community.setPosts(posts);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void updatePost(PostDto postDto) {
        log.debug("[updatePosts]");
        log.debug("[postDto: {}]", postDto);
        if (postDto == null) {
            throw new BusinessException("Error, null post");
        }
        postDao.updateRecord(PostMapper.getPost(
            postDto, postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[postId: {}]", postId);
        Post post = postDao.findById(postId);
        if (post == null) {
            throw new BusinessException("Error, there is no such post");
        } else if (post.isDeleted()) {
            throw new BusinessException("Error, the post has already been deleted");
        }
        post.setDeleted(true);
//        post.deleteComments
        postDao.updateRecord(post);
    }

}
