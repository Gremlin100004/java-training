package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.PrivateMessage;
import com.senla.socialnetwork.model.UserProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class PrivateMessageTestData {
    private static final List<Date> DEPARTURE_DATES = Arrays.asList(
        UserProfileTestData.getDateTime("2020-11-11 12:00"),
        UserProfileTestData.getDateTime("2020-11-11 12:01"),
        UserProfileTestData.getDateTime("2020-11-11 12:03"),
        UserProfileTestData.getDateTime("2020-11-11 12:04"),
        UserProfileTestData.getDateTime("2020-11-11 12:05"),
        UserProfileTestData.getDateTime("2020-11-01 10:01"),
        UserProfileTestData.getDateTime("2020-11-01 12:02"),
        UserProfileTestData.getDateTime("2020-11-01 12:03"),
        UserProfileTestData.getDateTime("2020-11-01 12:04"),
        UserProfileTestData.getDateTime("2020-10-28 07:01"),
        UserProfileTestData.getDateTime("2020-10-28 07:02"),
        UserProfileTestData.getDateTime("2020-10-28 07:03"),
        UserProfileTestData.getDateTime("2020-10-28 07:04"),
        UserProfileTestData.getDateTime("2020-10-28 07:05"));
    public static final List<String> CONTENTS = Arrays.asList(
        "Your great aunt just passed away. LOL",
        "Why is that funny?",
        "it''s not funny! What do ypu mean&",
        "Lol means laughing out loud!!!",
        "Oh my goodness!! I sent that to everyone I thought it meant lots of love. I have tocall rveryone back oh god",
        "What does IDK? LY & TTYL mean&?",
        "I don''t know, talk to you later",
        "OK, i will ask your sister",
        "Well that''s fantastic",
        "I''m working in my university July( But in August I will go to the gradma... How are ypur family?",
        "Im great!! Working, sending time with friends.. waiting for university to start again",
        "Everyone is okay!! We all miss you",
        "How is your family??",
        "They are good) I really glad to hear from you! but i must go to sleep, because i get up very early. good night sweety!"
                                                             );

    private PrivateMessageTestData() {
    }

    public static List<PrivateMessage> getPrivateMessages (List<UserProfile> userProfiles) {
        List<PrivateMessage> privateMessages = new ArrayList<>();
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            false, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            true, false));
        privateMessages.add(getPrivateMessage(
            DEPARTURE_DATES.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            true, false));
        return privateMessages;
    }

    private static PrivateMessage getPrivateMessage (Date departureDate,
                                                     UserProfile sender,
                                                     UserProfile recipient,
                                                     String content,
                                                     boolean isRead,
                                                     boolean isDelete) {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setDepartureDate(departureDate);
        privateMessage.setSender(sender);
        privateMessage.setRecipient(recipient);
        privateMessage.setContent(content);
        privateMessage.setIsRead(isRead);
        privateMessage.setIsDeleted(isDelete);
        return privateMessage;
    }
}
