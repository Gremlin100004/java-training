package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dao.connection.DatabaseConnection;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.CommunityMapper;
import com.senla.socialnetwork.service.mapper.PostMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@ServiceLog
@NoArgsConstructor
public class CommunityJdbcServiceImpl implements CommunityService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private DatabaseConnection databaseConnection;

    @Override
    public List<CommunityDto> getAllCommunities(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(communityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    public List<CommunityDto> getCommunities(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(communityDao.getCommunities(firstResult, maxResults));
    }

    @Override
    public List<CommunityDto> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesSortiedByNumberOfSubscribers(firstResult, maxResults));
    }

    @Override
    public List<CommunityDto> getCommunitiesFilteredByType(final CommunityType communityType,
                                                           final int firstResult,
                                                           final int maxResults) {
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesByType(communityType, firstResult, maxResults));
    }

    @Override
    public List<CommunityDto> getOwnCommunities(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(
            communityDao.getCommunitiesByEmail(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    public List<CommunityDto> getSubscribedCommunities(final int firstResult, final int maxResults) {
        return CommunityMapper.getCommunityDto(
            communityDao.getSubscribedCommunitiesByEmail(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    public void subscribeToCommunity(final Long communityId) {
        try {
            databaseConnection.disableAutoCommit();
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
            databaseConnection.commitTransaction();
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction subscribe to community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void unsubscribeFromCommunity(final Long communityId) {
        try {
            databaseConnection.disableAutoCommit();
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
            databaseConnection.commitTransaction();
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction unsubscribe from community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<PostDto> getCommunityPosts(final Long communityId, final int firstResult, final int maxResults) {
        return PostMapper.getPostDto(postDao.getByCommunityId(communityId, firstResult, maxResults));
    }

    @Override
    public CommunityDto addCommunity(final CommunityForCreateDto communityDto) {
        try {
            databaseConnection.disableAutoCommit();
            CommunityDto
                returnedCommunityDto = CommunityMapper.getCommunityDto(communityDao.save(CommunityMapper.getNewCommunity(
                communityDto, userProfileDao.findByEmail(PrincipalUtil.getUserName()))));
            databaseConnection.commitTransaction();
            return returnedCommunityDto;
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void updateCommunity(final CommunityDto communityDto) {
        try {
            databaseConnection.disableAutoCommit();
            communityDao.updateRecord(CommunityMapper.getCommunity(
                communityDto, communityDao, PrincipalUtil.getUserName()));
            databaseConnection.commitTransaction();
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction update community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void deleteCommunityByUser(final Long communityId) {
        try {
            databaseConnection.disableAutoCommit();
            Community community = communityDao.findByIdAndEmail(PrincipalUtil.getUserName(), communityId);
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
            databaseConnection.commitTransaction();
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void deleteCommunity(final Long communityId) {
        try {
            databaseConnection.disableAutoCommit();
            Community community = communityDao.findById(communityId);
            if (community == null) {
                throw new BusinessException("Error, there is no such community");
            }
            communityDao.deleteRecord(community);
            databaseConnection.commitTransaction();
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete community");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public PostDto addPostToCommunity(final PostForCreationDto postDto, final Long communityId) {
        try {
            databaseConnection.disableAutoCommit();
            Community community = communityDao.findByIdAndEmail(PrincipalUtil.getUserName(), communityId);
            if (community == null) {
                throw new BusinessException("Error, there is no such community");
            } else if (community.getIsDeleted()) {
                throw new BusinessException("Error, the community has already been deleted");
            }
            PostDto
                returnedPostDto = PostMapper.getPostDto(postDao.save(PostMapper.getNewPost(postDto, community)));
            databaseConnection.commitTransaction();
            return returnedPostDto;
        } catch (BusinessException exception) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add post");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

}
