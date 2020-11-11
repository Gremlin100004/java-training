package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserProfileDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.senla.socialnetwork")
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
//        UserProfileDao userProfileDao = applicationContext.getBean(UserProfileDao.class);
//        List<UserProfile> userProfileList = userProfileDao.getAllRecords();
//        System.out.println(userProfileList);
//
//        PublicMessageDao publicMessage = applicationContext.getBean(PublicMessageDao.class);
//        List<PublicMessage> publicMessages = publicMessage.getAllRecords();
//        System.out.println(publicMessages);
//
//        PublicMessageCommentDao publicMessageDao = applicationContext.getBean(PublicMessageCommentDao.class);
//        List<PublicMessageComment> publicMessagesComment = publicMessageDao.getAllRecords();
//        System.out.println(publicMessagesComment);
//
//        PrivateMessageDao privateMessageDao = applicationContext.getBean(PrivateMessageDao.class);
//        List<PrivateMessage> privateMessages = privateMessageDao.getAllRecords();
//        System.out.println(privateMessages);
//
//        CommunityDao communityDao = applicationContext.getBean(CommunityDao.class);
//        List<Community> communities = communityDao.getAllRecords();
//        System.out.println(communities);
//
//        PostDao postDao = applicationContext.getBean(PostDao.class);
//        List<Post> posts = postDao.getAllRecords();
//        System.out.println(posts);
//
//        PostCommentDao postCommentDao = applicationContext.getBean(PostCommentDao.class);
//        List<PostComment> postComments = postCommentDao.getAllRecords();
//        System.out.println(postComments);

//        String email = "user1@test.com";
//        WeatherConditionService postCommentDao = applicationContext.getBean(WeatherConditionService.class);
//        System.out.println(postCommentDao.getWeatherCondition(email));

//        String email = "user1@test.com";
//        PrivateMessageService privateMessageService = applicationContext.getBean(PrivateMessageService.class);
//        System.out.println(privateMessageService.getUserProfileMessages(email, 0, 20).size());

//        String email = "user1@test.com";
//        PrivateMessageService privateMessageService = applicationContext.getBean(PrivateMessageService.class);
//        System.out.println(privateMessageService.getDialogue(email, 3L, 0, 20).size());

//        String email = "user1@test.com";
//        PrivateMessageService privateMessageService = applicationContext.getBean(PrivateMessageService.class);
//        System.out.println(privateMessageService.getUnreadMessages(email, 0, 10).size());

//        String email = "user2@test.com";
//        PublicMessageService publicMessageService = applicationContext.getBean(PublicMessageService.class);
//        List<PublicMessageDto> publicMessages = publicMessageService.getUserProfileMessages(email, 0, 10);
//        System.out.println("+++++++++++++++++++++++++++++++++++");
//        publicMessages.forEach(System.out::println);

//        String email = "user2@test.com";
//        PublicMessageCommentDao publicMessageService = applicationContext.getBean(PublicMessageCommentDao.class);
//        List<PublicMessageComment> publicMessages = publicMessageService.getPublicMessageComments(3L);
//        System.out.println("+++++++++++++++++++++++++++++++++++");
//        publicMessages.forEach(System.out::println);

//        String email = "user2@test.com";
//        PublicMessageDao publicMessageDao = applicationContext.getBean(PublicMessageDao.class);
//        List<PublicMessage> publicMessages = publicMessageDao.getFriendsMessages(email, 0, 10);
//        System.out.println("+++++++++++++++++++++++++++++++++++");
//        publicMessages.forEach(System.out::println);

        String email = "user1@test.com";
        UserProfileDao userProfileDao = applicationContext.getBean(UserProfileDao.class);
        System.out.println(userProfileDao.getUserProfilesSortByNumberOfFriends(0, 10));


//        UserProfileService userProfileService = applicationContext.getBean(UserProfileService.class);
//        userProfileService.getNearestDateOfBirth(email);
//        System.out.println(userProfileService.getNearestDateOfBirth(email));

//        String email = "user1@test.com";
//        UserProfileService userProfileService = applicationContext.getBean(UserProfileService.class);
//        userProfileService.sendAFriendRequest(email, 4L);
//        System.out.println(userProfileService.getNearestDateOfBirth());

    }

}
