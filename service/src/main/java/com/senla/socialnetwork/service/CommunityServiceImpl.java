package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.CommunityMapper;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.PostMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<CommunityDto> getAllCommunities(final int firstResult, final int maxResults) {
        log.debug("[getOwnCommunities]");
        return CommunityMapper.getCommunityDto(communityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[getCommunities]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(communityDao.getCommunities(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[NumberOfSubscribers]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesSortiedByNumberOfSubscribers(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunitiesFilteredByType(final CommunityType communityType,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[getCommunityFilteredByType]");
        log.trace("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesByType(communityType, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getOwnCommunities(final HttpServletRequest request,
                                                final int firstResult,
                                                final int maxResults,
                                                final SecretKey secretKey) {
        log.debug("[getOwnCommunities]");
        log.trace("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(communityDao.getOwnCommunitiesByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getSubscribedCommunities(final HttpServletRequest request,
                                                       final int firstResult,
                                                       final int maxResults,
                                                       final SecretKey secretKey) {
        log.debug("[getSubscribedCommunities]");
        log.trace("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getSubscribedCommunitiesByEmail(JwtUtil.extractUsername(
                JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void subscribeToCommunity(final HttpServletRequest request,
                                     final Long communityId,
                                     final SecretKey secretKey) {
        log.debug("[subscribeToCommunity]");
        log.debug("[request: {}, communityId: {}]", request, communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        List<UserProfile> userProfiles = userProfileDao.getCommunityUsers(communityId);
        UserProfile userProfile = userProfileDao.findByEmail(JwtUtil.extractUsername(JwtUtil.getToken(
            request), secretKey));
        userProfile.getCommunitiesSubscribedTo().add(community);
        userProfiles.add(userProfile);
        community.setSubscribers(userProfiles);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void unsubscribeFromCommunity(final HttpServletRequest request,
                                         final Long communityId,
                                         final SecretKey secretKey) {
        log.debug("[subscribeToCommunity]");
        log.debug("[request: {}, communityId: {}]", request, communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        List<UserProfile> userProfiles = userProfileDao.getCommunityUsers(communityId);
        if (userProfiles.isEmpty()) {
            throw new BusinessException("Error, this user is not subscribed to the community");
        }
        UserProfile userProfile = userProfileDao.findByEmail(
            JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey));
        userProfile.getCommunitiesSubscribedTo().remove(community);
        userProfiles.remove(userProfile);
        community.setSubscribers(userProfiles);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public List<PostDto> getCommunityPosts(final Long communityId, final int firstResult, final int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[communityId: {}, firstResult: {}, maxResults: {}]", communityId, firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getByCommunityId(communityId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public CommunityDto addCommunity(final HttpServletRequest request,
                                     final CommunityForCreateDto communityDto,
                                     final SecretKey secretKey) {
        log.debug("[addCommunity]");
        log.debug("[request: {}, communityDto: {}]", request, communityDto);
        return CommunityMapper.getCommunityDto(communityDao.saveRecord(CommunityMapper.getNewCommunity(
            communityDto, userProfileDao.findByEmail(JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey)))));
    }

    @Override
    @Transactional
    public void updateCommunity(final HttpServletRequest request,
                                final CommunityDto communityDto,
                                final SecretKey secretKey) {
        log.debug("[updateCommunity]");
        log.debug("[request: {}, communityDto: {}]", request, communityDto);
        UserProfile userProfile = userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey));
        Community community = CommunityMapper.getCommunity(communityDto, communityDao, userProfileDao);
        if (community.getAuthor() != userProfile) {
            throw new BusinessException("Error, this community does not belong to this profile");
        }
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void deleteCommunityByUser(final HttpServletRequest request,
                                      final Long communityId,
                                      final SecretKey secretKey) {
        log.debug("[deleteMessageByUser]");
        log.debug("[request: {}, messageId: {}]", request, communityId);
        Community community = communityDao.findByIdAndEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        List<Post> posts = postDao.getByCommunityId(community.getId(), FIRST_RESULT, MAX_RESULTS);
        posts.forEach(post -> post.setIsDeleted(true));
        community.setIsDeleted(true);
        community.setSubscribers(new ArrayList<>());
        community.setPosts(posts);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void deleteCommunity(final Long communityId) {
        log.debug("[deleteCommunity]");
        log.debug("[communityId: {}]", communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        }
        communityDao.deleteRecord(community);
    }

    @Override
    @Transactional
    public PostDto addPostToCommunity(final HttpServletRequest request,
                                      final PostForCreationDto postDto,
                                      final Long communityId,
                                      final SecretKey secretKey) {
        log.debug("[addPosts]");
        log.debug("[request: {}, postDto: {}, communityId: {}]", request,  postDto, communityId);
        Community community = communityDao.findByIdAndEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        return PostMapper.getPostDto(postDao.saveRecord(PostMapper.getNewPost(postDto, community)));
    }

}
