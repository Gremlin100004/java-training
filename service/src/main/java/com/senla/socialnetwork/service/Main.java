package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ComponentScan("com.senla.socialnetwork")
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        UserProfileDao userProfileDao = applicationContext.getBean(UserProfileDao.class);
        List<UserProfile> userProfileList = userProfileDao.getAllRecords();
        System.out.println(userProfileList);

        PublicMessageDao publicMessage = applicationContext.getBean(PublicMessageDao.class);
        List<PublicMessage> publicMessages = publicMessage.getAllRecords();
        System.out.println(publicMessages);

        PublicMessageCommentDao publicMessageDao = applicationContext.getBean(PublicMessageCommentDao.class);
        List<PublicMessageComment> publicMessagesComment = publicMessageDao.getAllRecords();
        System.out.println(publicMessagesComment);

        PrivateMessageDao privateMessageDao = applicationContext.getBean(PrivateMessageDao.class);
        List<PrivateMessage> privateMessages = privateMessageDao.getAllRecords();
        System.out.println(privateMessages);

        CommunityDao communityDao = applicationContext.getBean(CommunityDao.class);
        List<Community> communities = communityDao.getAllRecords();
        System.out.println(communities);

        PostDao postDao = applicationContext.getBean(PostDao.class);
        List<Post> posts = postDao.getAllRecords();
        System.out.println(posts);

        PostCommentDao postCommentDao = applicationContext.getBean(PostCommentDao.class);
        List<PostComment> postComments = postCommentDao.getAllRecords();
        System.out.println(postComments);
    }

}
