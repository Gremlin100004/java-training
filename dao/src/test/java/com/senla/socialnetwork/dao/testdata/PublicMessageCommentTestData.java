package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.model.UserProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public final class PublicMessageCommentTestData {
    private static final List<Date> CREATION_DATES = Arrays.asList(
        UserProfileTestData.getDateTime("2020-07-21 11:23"),
        UserProfileTestData.getDateTime("2020-07-21 11:45"),
        UserProfileTestData.getDateTime("2020-08-04 07:45"),
        UserProfileTestData.getDateTime("2020-08-04 08:56"),
        UserProfileTestData.getDateTime("2020-08-25 09:23"),
        UserProfileTestData.getDateTime("2020-08-25 09:57"),
        UserProfileTestData.getDateTime("2020-09-18 06:01"),
        UserProfileTestData.getDateTime("2020-09-18 07:01"),
        UserProfileTestData.getDateTime("2020-10-13 03:02"),
        UserProfileTestData.getDateTime("2020-10-13 04:34"),
        UserProfileTestData.getDateTime("2020-08-12 06:32"),
        UserProfileTestData.getDateTime("2020-08-12 10:54"),
        UserProfileTestData.getDateTime("2020-09-16 02:10"),
        UserProfileTestData.getDateTime("2020-09-16 04:20"),
        UserProfileTestData.getDateTime("2020-07-18 07:23"),
        UserProfileTestData.getDateTime("2020-07-18 08:34")
    );
    private static final List<String> CONTENTS = Arrays.asList(
       "I know I’m a couple months late but everyone saying Shane the same person he was when he started YouTube, "
       + "can you see his transformed into this caring youtuber, yeah he made funny sketches which may offend some "
       + "people but at the time he was posting it to make people laugh",
       "Shane was my influencer. The dark comedy and all the jokes man. It was something I had fun with in school and "
       + "helped evolve me into a funnier gay. My family thought he was hilarious.",
       "Ok pedo.",
       "I still love you Shane. I know that your a good person and would never intentionally try to hurt ANYONE!",
       "FAKE AND GAY",
       "Why did you sell your youtube channel though",
       "Too late! While you were busy in getting your ego stroked in India, the stock market took a nose dive.",
       "Why did you cut the CDC budget? Are you hoping for a pandemic?",
       "cook four?",
       "nailed it",
       "please follow me!!!!!!!!!!!!!!!!!!!!!:{:{:{:{:{:::):):):):):):",
       "he is real don''t diss",
       "Most likely scenario is that you were very recently infected with SARS-CoV-2, so that not all swabs are testing"
       + " positive yet. Hit me up if you want to talk about treatment options.",
       "It’s called science Elon. Even HIV tests aren’t 100% accurate. These are brand new. There’s no bogus. Don’t "
       + "feed the stupid people with more conspiracy theories.",
       "Nice blue hat Ryan.",
       "do I drop the money or no...");

    private PublicMessageCommentTestData() {
    }

    public static List<PublicMessageComment> getComments(List<UserProfile> userProfiles,
                                                         List<PublicMessage> publicMessages) {
        List<PublicMessageComment> publicMessageComments = new ArrayList<>();
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index), false
        ));
        publicMessageComments.add(getComment(
                CREATION_DATES.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
                userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                publicMessages.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                CONTENTS.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index), false
        ));

        return publicMessageComments;
    }

    private static PublicMessageComment getComment(Date creationDate,
                                                   UserProfile userProfile,
                                                   PublicMessage publicMessage,
                                                   String content,
                                                   boolean isDelete) {
        PublicMessageComment comment = new PublicMessageComment();
        comment.setCreationDate(creationDate);
        comment.setAuthor(userProfile);
        comment.setPublicMessage(publicMessage);
        comment.setContent(content);
        comment.setIsDeleted(isDelete);
        return comment;
    }

}
