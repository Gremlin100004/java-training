package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@ComponentScan("com.senla.socialnetwork")
@PropertySource("classpath:application.properties")
public class DevTestDao {
    private static void test(CommunityDao communityDao){
                System.out.println("--------------------------------------------------------\ngetCommunities:");
        List<Community> communities = communityDao.getCommunities(0, 10);
        communities.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\ngetCommunitiesByType:");
        List<Community> communities2 = communityDao.getCommunitiesByType(CommunityType.SPORT, 0, 10);
        communities2.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\ngetCommunitiesSortiedByNumberOfSubscribers:");
        List<Community> communities3 = communityDao.getCommunitiesSortiedByNumberOfSubscribers(0, 10);
        communities3.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\ngetCommunitiesByEmail:");
        List<Community> communities4 = communityDao.getCommunitiesByEmail("user1@test.com", 0, 10);
        communities4.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\ngetSubscribedCommunitiesByEmail:");
        List<Community> communities5 = communityDao.getSubscribedCommunitiesByEmail("user1@test.com", 0, 10);
        communities5.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\nfindByIdAndEmail:");
        Community communities6 = communityDao.findByIdAndEmail("user1@test.com", 4L);
        System.out.println(communities6);
//        System.out.println("--------------------------------------------------------\nsaveRecord:");
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setRegistrationDate(new Date());
        userProfile.setLocation(null);
        userProfile.setSchool(null);
        userProfile.setUniversity(null);
        Community community = new Community();
        community.setAuthor(userProfile);
        community.setTitle("test");
        community.setIsDeleted(false);
        community.setCreationDate(new Date());
//        communityDao.saveRecord(community);
        System.out.println("--------------------------------------------------------\nfindById:");
        Community communityq = communityDao.findById(4L);
        System.out.println(communityq);
        System.out.println("--------------------------------------------------------\ngetAllRecords:");
        List<Community> community7 = communityDao.getAllRecords(0, 10);
        community7.forEach(System.out::println);
        System.out.println("--------------------------------------------------------\nupdateRecord:");
        community.setId(1L);
        communityDao.updateRecord(community);
        System.out.println("updated record");
        System.out.println("--------------------------------------------------------\ndeleteRecord:");
        community.setId(1L);
        communityDao.deleteRecord(community);
        System.out.println("deleted record");
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DevTestDao.class);
        test(applicationContext.getBean(CommunityDao.class));
    }

}
