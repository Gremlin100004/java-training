package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.model.UserProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PostCommentTestData {
    private static final List<Date> CREATION_DATES = Arrays.asList(
        UserProfileTestData.getDateTime("2020-10-02 09:23"),
        UserProfileTestData.getDateTime("2020-10-02 11:12"),
        UserProfileTestData.getDateTime("2020-10-03 10:23"),
        UserProfileTestData.getDateTime("2020-10-03 11:23"),
        UserProfileTestData.getDateTime("2020-10-04 07:23"),
        UserProfileTestData.getDateTime("2020-10-04 08:23"),
        UserProfileTestData.getDateTime("2020-10-05 01:23"),
        UserProfileTestData.getDateTime("2020-10-05 02:23"),
        UserProfileTestData.getDateTime("2020-10-06 04:23"),
        UserProfileTestData.getDateTime("2020-10-06 05:23"),
        UserProfileTestData.getDateTime("2020-10-07 09:23"),
        UserProfileTestData.getDateTime("2020-10-07 10:23"),
        UserProfileTestData.getDateTime("2020-10-08 02:23"),
        UserProfileTestData.getDateTime("2020-10-08 03:23"),
        UserProfileTestData.getDateTime("2020-10-09 05:23"),
        UserProfileTestData.getDateTime("2020-10-09 06:23"),
        UserProfileTestData.getDateTime("2020-10-10 03:23"),
        UserProfileTestData.getDateTime("2020-10-10 04:23"),
        UserProfileTestData.getDateTime("2020-10-11 09:23"),
        UserProfileTestData.getDateTime("2020-10-11 10:23"),
        UserProfileTestData.getDateTime("2020-10-12 10:23"),
        UserProfileTestData.getDateTime("2020-10-12 11:23"),
        UserProfileTestData.getDateTime("2020-10-13 01:23"),
        UserProfileTestData.getDateTime("2020-10-13 04:23"),
        UserProfileTestData.getDateTime("2020-10-14 03:23"),
        UserProfileTestData.getDateTime("2020-10-14 09:23"),
        UserProfileTestData.getDateTime("2020-10-15 01:2"),
        UserProfileTestData.getDateTime("2020-10-15 10:23"),
        UserProfileTestData.getDateTime("2020-10-16 03:23"),
        UserProfileTestData.getDateTime("2020-10-16 06:23"),
        UserProfileTestData.getDateTime("2020-10-17 06:23"),
        UserProfileTestData.getDateTime("2020-10-17 11:23"));

    private static final List<String> CONTENT = Arrays.asList(
        "Do lockdowns, we need them.",
        "No thanks to you...  I''ll keep thanking the people aiding the sick and those developing novel therapies "
        + "for covid.",
        "It makes no sense that we cannot visit a loved one that is fighting for their life, whom we may never see "
        + "again, BUT we allow people to pack in restaurants and bars.  This rule is wrong on so many levels!",
        "People are math challenged and hardwired for FEAR. My age group: 50-64 is HOSPITALIZED at a rate of 15/100,000"
        + " which is less than 2/100ths of 1%..which is to say 0.00015% In context, the cancer DEATH rate is "
        + "158.3/100,000. Should we quiver in fear of cancer, or live our lives?",
        "Will Walmart have pre-orders like they did on the PS5s?  Will the UK scalper bots get them all?",
        "We need rapid at home testing! Over the counter!",
        "I''m tired of stupid and uncaring people who refuse to stay home and wear  a mask when they must go out. "
        + "That''s what I''m tired of.",
        "Perhaps they can turn churches into temporary covid treatment centers or morgues.",
        "They have the same nose, Diego’s probably a little more used.",
        "Just watched it on YouTube, really good, once @GaryLineker finally gets him sat down Face with tears of"
        + " joyZany face",
        "I''ll be there with my 9 flatmates!",
        "So will I",
        "Touch judge wins the Harry Sunderland Trophy for his match winning turn for St Helens.",
        "Crazy, sport can be entertaining but also very cruel.",
        "Imagine being so bad Joelinton scores against you Face with tears of joy",
        "I don''t have to imagine.",
        "The fascinating Japanese architecture of the past with the British automotive elegance and refinement of "
        + "the present. Past and present in a beautiful photo. 2 very special design approache",
        "I wish this were taken as a panoramic shot, or just in landscape mode.Slightly smiling face very beautiful, of"
        + " course. Thanks!",
        "any hints as to what it is?",
        "Graphics card. Bold move for McLaren to take on Nvidia.",
        "Clearly explained",
        "Awesome",
        "\"Long tail\" ... meer inches longer than the 720s.",
        "The first couple of shots looked like a forzavista cinematic look around and made me think this was in horizon"
        + " 4. Really hope it comes to that game",
        "I honestly would grab tickets but being in Australia the times aren''t in my favour one bit . I''d be asleep "
        + "for the first show and then at work during the second ",
        "hello, Lind when he comes to play for us here in Brazil, I want to see you live dancing and letting you see on"
        + " the violin until the strings explode",
        "This sounds absolutely lovely! Looking forward to listening to it/see you play on #CMAChristmas",
        "It was an excellent and very beautiful song ",
        "I got the violin hat t shirt and the fireplace t shirt! Thanks Lindsey!",
        "I love the blankets. I have the red and blue from the last two years. Time to do a little shopping for some"
        + " friends.",
        "Thank you for sharing this app and for the last live stream, it was so much fun and sweet",
        "I''m happy to hear you''re able to stay connected with your friends and family. That app is pretty cool.");

    public static List<PostComment> getComments(List<UserProfile> userProfiles, List<Post> posts) {
        List<PostComment> comments = new ArrayList<>();
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.SEVENTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.SEVENTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.EIGHTEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.EIGHTEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.NINETEENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.NINETEENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTIETH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTIETH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_FIRST_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_SECOND_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_THIRD_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_THIRD_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_FOURTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_FOURTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_FIFTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_FIFTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_SIXTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_SIXTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_SEVENTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_SEVENTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_EIGHTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_EIGHTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.TWENTY_NINTH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.TWENTY_NINTH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.THIRTIETH_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.THIRTIETH_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.THIRTY_FIRST_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        comments.add(getComment(
            CREATION_DATES.get(ArrayIndex.THIRTY_SECOND_INDEX_OF_ARRAY.index),
            userProfiles.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            posts.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            CONTENT.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        return comments;
    }
    private static PostComment getComment(final Date creationDate,
                                          final UserProfile author,
                                          final Post post,
                                          final String content,
                                          final Boolean isDelete) {
        PostComment comment = new PostComment();
        comment.setCreationDate(creationDate);
        comment.setAuthor(author);
        comment.setPost(post);
        comment.setContent(content);
        comment.setIsDeleted(isDelete);
        return comment;
    }
}
