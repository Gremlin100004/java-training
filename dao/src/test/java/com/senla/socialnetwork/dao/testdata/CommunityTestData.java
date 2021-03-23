package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.enumaration.CommunityType;

import java.util.*;

public final class CommunityTestData {
    private static final List<Date> CREATION_DATES = Arrays.asList(
        UserProfileTestData.getDateTime("2020-10-01 12:00"),
        UserProfileTestData.getDateTime("2020-10-02 12:00"),
        UserProfileTestData.getDateTime("2020-10-03 12:00"),
        UserProfileTestData.getDateTime("2020-10-04 12:00"));
    private static final List<String> TITTLES = Arrays.asList(
        "COVID-19: Updates for the US",
        "BBC Sport",
        "McLaren Automotive",
        "Lindsey Stirling"
        );

    private static final List<String> INFORMATION = Arrays.asList(
        "This page is a timeline of Tweets with the latest information and advice from the CDC, HHS, NIH and public "
        + "health authorities across the country. For more, visit https://https://cdc.gov/coronavirus.",
        "Official http://bbc.co.uk/sport account. Also follow @bbcmotd and @bbctms.",
        "Born and raised on the track, we use racing technology and expertise to create the most advanced performance "
        + "cars in the world.",
        "Fan community of violinist Lindsey Stirling."
                                                                );

    public static List<Community> getCommunities(List<UserProfile> userProfiles) {
        List<Community> communities = new ArrayList<>();
        communities.add(getCommunity(
            CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CommunityType.MEDICINE,
                TITTLES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                INFORMATION.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                false,
                new ArrayList<UserProfile>() {
                    {
                        add(userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
                        add(userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index));
                        add(userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index));
                    }
                }));
        communities.add(getCommunity(
            CREATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CommunityType.SPORT,
            TITTLES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            INFORMATION.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false,
                new ArrayList<UserProfile>() {
                    {
                        add(userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
                        add(userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index));
                    }
                }));
        communities.add(getCommunity(
            CREATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CommunityType.AUTO,
            TITTLES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            INFORMATION.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            true,
                new ArrayList<UserProfile>() {
                    {
                        add(userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index));
                    }
                }));
        communities.add(getCommunity(
            CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CommunityType.MUSIC,
            TITTLES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            INFORMATION.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false,
                new ArrayList<UserProfile>() {
                    {
                        add(userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
                        add(userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index));
                    }
                }));
        return communities;
    }

    private static Community getCommunity(Date creationDate,
                                          UserProfile author,
                                          CommunityType type,
                                          String tittle,
                                          String information,
                                          boolean isDelete,
                                          List<UserProfile> userProfiles) {
        Community community = new Community();
        community.setCreationDate(creationDate);
        community.setAuthor(author);
        author.getOwnCommunities().add(community);
        community.setType(type);
        community.setTitle(tittle);
        community.setInformation(information);
        community.setIsDeleted(isDelete);
        community.setSubscribers(userProfiles);
        community.setPosts(new ArrayList<>());
        return community;
    }

}
