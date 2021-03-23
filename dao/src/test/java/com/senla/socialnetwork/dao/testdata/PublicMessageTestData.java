package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.UserProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public final class PublicMessageTestData {
    private PublicMessageTestData() {
    }
    private static final List<Date> CREATION_DATES = Arrays.asList(
            UserProfileTestData.getDateTime("2020-07-21 10:00"),
            UserProfileTestData.getDateTime("2020-08-04 10:00"),
            UserProfileTestData.getDateTime("2020-08-25 10:00"),
            UserProfileTestData.getDateTime("2020-09-18 10:00"),
            UserProfileTestData.getDateTime("2020-10-13 10:00"),
            UserProfileTestData.getDateTime("2020-08-12 10:00"),
            UserProfileTestData.getDateTime("2020-09-16 10:00"),
            UserProfileTestData.getDateTime("2020-07-18 10:00")
    );
    private static final List<String> TITTLES = Arrays.asList(
       "I deleted everything.",
       "CONSPIRACY RELAUNCH IS LIVE!! :,))))",
       "I don’t usually upload my TikToks to Twitter, but idk this one feels like it might fit...",
       "The Coronavirus is very much under control",
       "Keep an eye on my vlogging channel tomorrow at 6pm for a reveal and something to do with something that rhymes"
       + " with ''Rook Pour''",
       "Good morning everyone!",
       "Something extremely bogus is going on.",
       "It''s just a shout out but for a good cause promoting social inclusion, job opportunities and leadership "
       + "development for children and adults with intellectual and developmental disabilities (IDD). Check it!"
    );

    private static final List<String> CONTENTS = Arrays.asList(
       "I deleted everything. I’m done. For those who wanted me to “address it” I did. I’m sure u can find it reposted"
       + " somewhere. But I don’t want this energy in my life or on my timeline. I’m too sensitive for this shit and "
       + "I’m done.",
       "https://jeffreestarcosmetics.com/collections/shane-x-jeffree-conspiracy-collection",
       null,
       "The Coronavirus is very much under control in the USA. We are in contact with everyone and all relevant "
       + "countries. CDC & World Health have been working hard and very smart. Stock Market starting to look very good"
       + " to me!",
       null,
       "OMG IT''S #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY "
       + "#SUGGSUNDAY",
       "Was tested for covid four times today. Two tests came back negative, two came back positive. Same machine, same"
       + " test, same nurse. Rapid antigen test from BD.",
       "The Best Buddies Friendship Jam Silent Auction just went live! https://e.givesmart.com/events/iCc/i/"
    );



    public static List<PublicMessage> getPublicMessages(List<UserProfile> userProfiles) {
        List<PublicMessage> publicMessages = new ArrayList<>();
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                false
                ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
                false
        ));
        publicMessages.add(getPublicMessage(
                CREATION_DATES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                TITTLES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
                true
        ));
        return publicMessages;
    }

    private static PublicMessage getPublicMessage(Date creationDate,
                                           UserProfile userProfile,
                                           String tittle,
                                           String content,
                                           boolean deleteStatus) {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setCreationDate(creationDate);
        publicMessage.setAuthor(userProfile);
        publicMessage.setTitle(tittle);
        publicMessage.setContent(content);
        publicMessage.setIsDeleted(deleteStatus);
        publicMessage.setPublicMessageComments(new ArrayList<>());
        return publicMessage;
    }

}
