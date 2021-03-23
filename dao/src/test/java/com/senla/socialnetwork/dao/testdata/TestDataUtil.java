package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.*;
import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.model.*;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestDataUtil {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private UniversityDao universityDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private PublicMessageDao publicMessageDao;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    private PrivateMessageDao privateMessageDao;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostCommentDao postCommentDao;
    @Autowired
    private WeatherConditionDao weatherConditionDao;
    private List<Location> locations;
    private List<School> schools;
    private List<University> universities;
    private List<SystemUser> users;
    private List<UserProfile> userProfiles;
    private List<PublicMessage> publicMessages;
    private List<PublicMessageComment> publicMessageComments;
    private List<PrivateMessage> privateMessages;
    private List<Community> communities;
    private List<Post> posts;
    private List<PostComment> postComments;
    private List<WeatherCondition> weatherConditions;

    public void fillWithData() {
        fillLocations();
        fillSchools();
        fillUniversities();
        fillUsers();
        fillUserProfiles();
        fillPublicMessages();
        fillPublicMessageComments();
        fillPrivateMessage();
        fillCommunities();
        fillPosts();
        fillPostComments();
        fillWeatherConditions();
    }

    public void clearData() {
        weatherConditionDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
            weatherCondition -> weatherConditionDao.deleteRecord(weatherCondition));
        postCommentDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(postComment -> postCommentDao.deleteRecord(
            postComment));
        postDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(post -> postDao.deleteRecord(post));
        communityDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(community -> communityDao.deleteRecord(community));
        privateMessageDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
            privateMessage -> privateMessageDao.deleteRecord(privateMessage));
        publicMessageCommentDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
                publicMessageComment -> publicMessageCommentDao.deleteRecord(publicMessageComment));
        publicMessageDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
                publicMessage -> publicMessageDao.deleteRecord(publicMessage));
        userProfileDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
                userProfile -> userProfileDao.deleteRecord(userProfile));
        userDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(systemUser -> userDao.deleteRecord(systemUser));
        universityDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(
            university -> universityDao.deleteRecord(university));
        schoolDao.getAllRecords(FIRST_RESULT, MAX_RESULT).forEach(school -> schoolDao.deleteRecord(school));
        locationDao.deleteAll();
    }

    public Location getLocationByEmail(String email) {
        return userProfiles.stream()
            .filter(userProfile -> userProfile.getSystemUser().getEmail().equals(email))
            .findFirst()
            .map(UserProfile::getLocation)
            .orElse(null);
    }

    public List<Community> getCommunitiesNotDelete() {
        List<Community> resultCommunities = new ArrayList<>();
        for (Community community:communities) {
            if (community.getIsDeleted().equals(false)) {
                resultCommunities.add(community);
            }
        }
        return resultCommunities;
    }

    public List<Community> getCommunitiesByType(CommunityType type) {
        List<Community> resultCommunities = new ArrayList<>();
        for (Community community:communities) {
            if (community.getType().equals(type)) {
                resultCommunities.add(community);
            }
        }
        return resultCommunities;
    }

    public List<UserProfile> getUserProfilesByCommunityId(Long communityId) {
        for (Community community: communities) {
            if (community.getId().equals(communityId)) {
                return community.getSubscribers();
            }
        }
        return new ArrayList<>();
    }
    public List<UserProfile> getUserProfilesByLocation(Location location) {
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getLocation().equals(location))
                .collect(Collectors.toList());
    }

    public List<UserProfile> getUserProfilesBySchool(School school) {
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getSchool().equals(school))
                .collect(Collectors.toList());
    }

    public List<UserProfile> getUserProfilesByUniversity(University university) {
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getUniversity().equals(university))
                .collect(Collectors.toList());
    }

    public List<UserProfile> getUserProfilesByAge(Date startPeriodDate, Date endPeriodDate) {
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getDateOfBirth().after(
                        startPeriodDate) && userProfile.getDateOfBirth().before(endPeriodDate))
                .collect(Collectors.toList());
    }

    public List<UserProfile> getFriend(String email) {
        for (UserProfile userProfile: userProfiles) {
            if (userProfile.getSystemUser().getEmail().equals(email)) {
                return userProfile.getFriends();
            }
        }
        return new ArrayList<>();
    }

    public List<PublicMessage> getPublicMessagesByEmail(String email) {
        return publicMessages.stream()
                .filter(publicMessage -> publicMessage.getAuthor().getSystemUser().getEmail().equals(email))
                .collect(Collectors.toList());

    }

    public List<PublicMessage> getFriendsPublicMessages(String email) {
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getSystemUser().getEmail().equals(email))
                .flatMap(userProfile -> userProfile.getFriends().stream())
                .flatMap(friend -> friend.getPublicMessages().stream())
                .filter(publicMessage -> !publicMessage.getIsDeleted())
                .collect(Collectors.toList());
    }

    public List<PrivateMessage> getPrivateMessagesByEmail(String email) {
        return privateMessages.stream()
            .filter(privateMessage -> privateMessage.getSender().getSystemUser().getEmail().equals(email)
                                      || privateMessage.getRecipient().getSystemUser().getEmail().equals(email))
            .collect(Collectors.toList());
    }

    public List<PrivateMessage> getPrivateMessagesByEmailAndUser(final SystemUser user, final String email) {
        return privateMessages.stream()
            .filter(privateMessage -> privateMessage.getSender().getSystemUser().getEmail().equals(email)
                                      && privateMessage.getRecipient().getSystemUser().equals(user)
                                      || privateMessage.getRecipient().getSystemUser().getEmail().equals(email)
                                         && privateMessage.getSender().getSystemUser().equals(user))
            .collect(Collectors.toList());
    }

    public List<PrivateMessage> getUnreadPrivateMessages(final String email) {
        return privateMessages.stream()
            .filter(privateMessage -> privateMessage.getRecipient().getSystemUser().getEmail().equals(email)
                                      && privateMessage.getIsRead().equals(false)
                                      && privateMessage.getIsDeleted().equals(false))
            .collect(Collectors.toList());
    }

    public List<PrivateMessage> getPrivateMessagesByPeriod(final String email,
                                                           final Date startPeriod,
                                                           final Date endPeriod) {
        return privateMessages.stream()
            .filter(privateMessage -> privateMessage.getSender().getSystemUser().getEmail().equals(email)
                                      && privateMessage.getDepartureDate().after(startPeriod)
                                      || privateMessage.getRecipient().getSystemUser().getEmail().equals(email)
                                         && privateMessage.getDepartureDate().before(endPeriod))
            .collect(Collectors.toList());
    }

    public List<Post> getPostsByEmail(final String email) {
        return posts.stream()
            .filter(post -> post.getCommunity().getAuthor().getSystemUser().getEmail().equals(email)
                            && post.getIsDeleted().equals(false))
            .collect(Collectors.toList());
    }

    public List<Community> getCommunitiesByEmail(final String email) {
        List<Community> communities = new ArrayList<>();
        for (Community community : this.communities) {
            if (community.getAuthor().getSystemUser().getEmail().equals(email)) {
                communities.add(community);
            }
        }
        return communities;
    }

    public List<Community> getSubscribedCommunitiesByEmail(final String email) {
        List<Community> communities = new ArrayList<>();
        for (Community community : this.communities) {
            if (community.getAuthor().getSystemUser().getEmail().equals(email)) {
                communities.addAll(community.getAuthor().getCommunitiesSubscribedTo());
            }
        }
        return communities;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<School> getSchools() {
        return schools;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public List<SystemUser> getUsers() {
        return users;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public List<PublicMessage> getPublicMessages() {
        return publicMessages;
    }

    public List<PublicMessageComment> getPublicMessageComments() {
        return publicMessageComments;
    }

    public List<PrivateMessage> getPrivateMessages() {
        return privateMessages;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public List<WeatherCondition> getWeatherConditions() {
        return weatherConditions;
    }

    private void fillLocations() {
        locations = new ArrayList<>();
        LocationTestData.getLocations().forEach(location -> {
            locationDao.save(location);
            locations.add(location);
        });
    }

    private void fillSchools() {
        schools = new ArrayList<>();
        SchoolTestData.getSchools(locations).forEach(school -> {
            schoolDao.save(school);
            school.getLocation().getSchools().add(school);
            schools.add(school);
        });
    }

    private void fillUniversities() {
        universities = new ArrayList<>();
        UniversityTestData.getUniversities(locations).forEach(university -> {
            universityDao.save(university);
            university.getLocation().getUniversities().add(university);
            universities.add(university);
        });
    }

    private void fillUsers() {
        users = new ArrayList<>();
        List<SystemUser> users = UserTestData.getSystemUsers();
        for (SystemUser systemUser : users) {
            userDao.save(systemUser);
            this.users.add(systemUser);
        }
    }

    private void fillUserProfiles() {
        userProfiles = new ArrayList<>();
        UserProfileTestData.getUsersProfiles(locations, schools, universities, users).forEach(userProfile -> {
            userProfileDao.save(userProfile);
            userProfile.getLocation().getUserProfiles().add(userProfile);
            userProfile.getSchool().getUserProfiles().add(userProfile);
            userProfile.getUniversity().getUserProfiles().add(userProfile);
            userProfiles.add(userProfile);
        });
        userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).setFriends(
                new ArrayList<UserProfile>() {
                    {
                    add(userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index));
                    add(userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index));
                    }
                });
        userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index).setFriends(
                new ArrayList<UserProfile>(){
                    {
                        add(userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index));
                    }
                });
        userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).setFriendshipRequests(
                new ArrayList<UserProfile>(){
                    {
                        add(userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
                    }
                });
    }

    private void fillPublicMessages() {
        publicMessages = new ArrayList<>();
        PublicMessageTestData.getPublicMessages(userProfiles).forEach(publicMessage -> {
            publicMessageDao.save(publicMessage);
            publicMessages.add(publicMessage);
            publicMessage.getAuthor().getPublicMessages().add(publicMessage);
        });
    }

    private void fillPublicMessageComments() {
        publicMessageComments = new ArrayList<>();
        PublicMessageCommentTestData.getComments(userProfiles, publicMessages).forEach(publicMessageComment -> {
            publicMessageCommentDao.save(publicMessageComment);
            publicMessageComments.add(publicMessageComment);
            publicMessageComment.getPublicMessage().getPublicMessageComments().add(publicMessageComment);
        });
    }

    private void fillPrivateMessage() {
        privateMessages = new ArrayList<>();
        PrivateMessageTestData.getPrivateMessages(userProfiles).forEach(privateMessage -> {
            privateMessageDao.save(privateMessage);
            privateMessages.add(privateMessage);
        });
    }

    private void fillCommunities() {
        communities = new ArrayList<>();
        CommunityTestData.getCommunities(userProfiles).forEach(community -> {
            communityDao.save(community);
            communities.add(community);
            community.getSubscribers().forEach(userProfile -> userProfile.getCommunitiesSubscribedTo().add(community));
        });
    }

    private void fillPosts() {
        posts = new ArrayList<>();
        PostTestData.getPosts(communities).forEach(post -> {
            postDao.save(post);
            posts.add(post);
            post.getCommunity().getPosts().add(post);
        });
    }

    private void fillPostComments() {
        postComments = new ArrayList<>();
        PostCommentTestData.getComments(userProfiles, posts).forEach(postComment -> {
            postCommentDao.save(postComment);
            postComments.add(postComment);
            postComment.getPost().getPostComments().add(postComment);
        });
    }

    private void fillWeatherConditions() {
        weatherConditions = new ArrayList<>();
        WeatherConditionTestData.getWeatherConditions(locations).forEach(weatherCondition -> {
            weatherConditionDao.save(weatherCondition);
            weatherCondition.getLocation().getWeatherConditions().add(weatherCondition);
            weatherConditions.add(weatherCondition);
        });
    }

}
