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
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@NoArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<CommunityDto> getAllCommunities(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(communityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(communityDao.getCommunities(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesSortiedByNumberOfSubscribers(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunitiesFilteredByType(final CommunityType communityType,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesByType(communityType, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getOwnCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesByEmail(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getSubscribedCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getSubscribedCommunitiesByEmail(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void subscribeToCommunity(final Long communityId) {
        log.debug("[communityId: {}]", communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        UserProfile userProfile = userProfileDao.findByEmail(PrincipalUtil.getUserName());
        if (userProfile.getCommunitiesSubscribedTo().contains(community)) {
            throw new BusinessException("Error, the user is already subscribed to this community");
        }
        userProfile.getCommunitiesSubscribedTo().add(community);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void unsubscribeFromCommunity(final Long communityId) {
        log.debug("[communityId: {}]", communityId);
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        UserProfile userProfile = userProfileDao.findByEmail(PrincipalUtil.getUserName());
        if (!community.getSubscribers().contains(userProfile)) {
            throw new BusinessException("Error, this user is not subscribed to the community");
        }
        userProfile.getCommunitiesSubscribedTo().remove(community);
        community.getSubscribers().remove(userProfile);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public List<PostDto> getCommunityPosts(final Long communityId, final int firstResult, final int maxResults) {
        log.debug("[communityId: {}, firstResult: {}, maxResults: {}]", communityId, firstResult, maxResults);
        return PostMapper.getPostDto(postDao.getByCommunityId(communityId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public CommunityDto addCommunity(final CommunityForCreateDto communityDto) {
        log.debug("[communityDto: {}]", communityDto);
        return CommunityMapper.getCommunityDto(communityDao.saveRecord(CommunityMapper.getNewCommunity(
            communityDto, userProfileDao.findByEmail(PrincipalUtil.getUserName()))));
    }

    @Override
    @Transactional
    public void updateCommunity(final CommunityDto communityDto) {
        log.debug("[communityDto: {}]", communityDto);
        communityDao.updateRecord(CommunityMapper.getCommunity(
            communityDto, communityDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deleteCommunityByUser(final Long communityId) {
        log.debug("[messageId: {}]", communityId);
        Community community = communityDao.findByIdAndEmail(PrincipalUtil.getUserName(), communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
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
        log.debug("[postDto: {}, communityId: {}]", postDto, communityId);
        Community community = communityDao.findByIdAndEmail(PrincipalUtil.getUserName(), communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.getIsDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        return PostMapper.getPostDto(postDao.saveRecord(PostMapper.getNewPost(postDto, community)));
    }

}
