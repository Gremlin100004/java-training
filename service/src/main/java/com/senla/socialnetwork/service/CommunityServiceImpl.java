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
import com.senla.socialnetwork.service.mapper.CommunityMapper;
import com.senla.socialnetwork.service.mapper.PostMapper;
import com.senla.socialnetwork.service.security.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CommunityDto> getOwnCommunities(final int firstResult, final int maxResults) {
        log.debug("[getOwnCommunities]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getOwnCommunitiesByEmail(getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getSubscribedCommunities(final int firstResult, final int maxResults) {
        log.debug("[getSubscribedCommunities]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getSubscribedCommunitiesByEmail(getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void subscribeToCommunity(final Long communityId) {
        log.debug("[subscribeToCommunity]");
        log.debug("[communityId: {}]", communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        List<UserProfile> userProfiles = userProfileDao.getCommunityUsers(communityId);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        userProfile.getCommunitiesSubscribedTo().add(community);
        userProfiles.add(userProfile);
        community.setSubscribers(userProfiles);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void unsubscribeFromCommunity(final Long communityId) {
        log.debug("[subscribeToCommunity]");
        log.debug("[communityId: {}]", communityId);
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
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
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
    public CommunityDto addCommunity(final CommunityForCreateDto communityDto) {
        log.debug("[addCommunity]");
        log.debug("[communityDto: {}]", communityDto);
        return CommunityMapper.getCommunityDto(communityDao.saveRecord(CommunityMapper.getNewCommunity(
            communityDto, userProfileDao.findByEmail(getUserName()))));
    }

    @Override
    @Transactional
    public void updateCommunity(final CommunityDto communityDto) {
        log.debug("[updateCommunity]");
        log.debug("[communityDto: {}]", communityDto);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        Community community = CommunityMapper.getCommunity(communityDto, communityDao, userProfileDao);
        if (community.getAuthor() != userProfile) {
            throw new BusinessException("Error, this community does not belong to this profile");
        }
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void deleteCommunityByUser(final Long communityId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[messageId: {}]", communityId);
        Community community = communityDao.findByIdAndEmail(getUserName(), communityId);
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
    public PostDto addPostToCommunity(final PostForCreationDto postDto, final Long communityId) {
        log.debug("[addPosts]");
        log.debug("[postDto: {}, communityId: {}]", postDto, communityId);
        Community community = communityDao.findByIdAndEmail(getUserName(), communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        return PostMapper.getPostDto(postDao.saveRecord(PostMapper.getNewPost(postDto, community)));
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
