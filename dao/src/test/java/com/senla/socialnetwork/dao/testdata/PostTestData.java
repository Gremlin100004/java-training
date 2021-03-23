package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class PostTestData {
    private static final List<Date> CREATION_DATES = Arrays.asList(
        UserProfileTestData.getDateTime("2020-10-01 20:11"),
        UserProfileTestData.getDateTime("2020-10-02 12:11"),
        UserProfileTestData.getDateTime("2020-10-03 06:34"),
        UserProfileTestData.getDateTime("2020-10-04 11:11"),
        UserProfileTestData.getDateTime("2020-10-05 11:34"),
        UserProfileTestData.getDateTime("2020-10-06 06:11"),
        UserProfileTestData.getDateTime("2020-10-07 09:43"),
        UserProfileTestData.getDateTime("2020-10-08 04:32"),
        UserProfileTestData.getDateTime("2020-10-09 03:12"),
        UserProfileTestData.getDateTime("2020-10-10 01:52"),
        UserProfileTestData.getDateTime("2020-10-11 05:23"),
        UserProfileTestData.getDateTime("2020-10-12 02:15"),
        UserProfileTestData.getDateTime("2020-10-13 06:42"),
        UserProfileTestData.getDateTime("2020-10-14 02:48"),
        UserProfileTestData.getDateTime("2020-10-15 11:58"),
        UserProfileTestData.getDateTime("2020-10-16 03:23"));
    public static final List<String> TITTLES = Arrays.asList(
        "El Paso County sees drop in COVID-19 hospitalizations",
        "#COVID19 affects everyone differently",
        null,
        "COVID fatigue is real.",
        "When Lineker Met Maradona",
        "Great news!",
        "ST HELENS HAVE DONE IT IN DRAMATIC FASHION!",
        "FT Crystal Palace 0-2 Newcastle",
        "It''s #WallpaperWednesday time. This week we have some new images of the #McLarenGT in Japan. #GTadventures ",
        "Artura is the purest distillation of everything that we have done to date. We poured every drop of our "
        + "expertise in super-light engineering, extreme power, electrification and race-honed agility into its DNA to "
        + "deliver a uniquely intense McLaren experience. #Artura",
        "How the McLaren Sennas active aerodynamics works",
        "In the McLaren 765LT our longtail DNA is woven into everything. How? ",
        "Home for the Holidays Special",
        "So excited to share that the song @dariusrucker and I will perform on #CMAChristmas is now streaming!",
        "New merch just in time for my Home for the Holidays Special! Use code ''HOMEFORTHEHOLIDAYS'' for 15% off your "
        + "order and add a plush blanket for only $30 when you spend $75!",
        "Stay In Touch With The People Who Matter Most To You");
    public static final List<String> CONTENTS = Arrays.asList(
        "COVID hospitalizations in El Paso have been going down for about 2 weeks now. The number of people in El Paso "
        + "testing positive for COVID has been declining also. The positivity rate for all of Texas has declined for 14"
        + " consecutive days.",
        "Cases and hospitalizations are rising, with adults ages 65+ much more likely to be hospitalized. Protect your "
        + "loved ones to make sure they’re healthy for upcoming holidays and the new year. Learn more: "
        + "https://bit.ly/2HwJo2q",
        "Since one or more #COVID19 vaccines may be available in limited supply before the end of the year, CDC is "
        + "working closely with health departments and partners to get ready for when vaccines are available. Learn "
        + "more: https://bit.ly/3ppRU4H.",
        "COVID fatigue is real. COVID amnesia is deadly. Don’t forget where we were.",
        "At 22:00, we''ll be showing our documentary with @GaryLineker from 2006. @GaryLineker talks to Diego Maradona "
        + "about football, genius and *that* 1986 World Cup quarter-final.",
        "Snooker will welcome back spectators at the Masters in January. Read all about it: https://bbc.in/3qeSXoH",
        "Right at the death, tied at 4-4, a long drop-goal attempt hits the post and Jack Welsby chases it down to get "
        + "the try as the buzzer goes! Wigan Warriors 4-8 St Helens. Incredible drama! Follow LIVE: http://bbc.in/2JcRnCM",
        "Two late goals for Callum Wilson and Joelinton earn the three points for the Magpies. #CRYNEW live: "
        + "http://bbc.in/3q4tfCZ",
        "content",
        null,
        "The McLaren Senna is capable of generating an incredible 800kg of downforce. How? Via a cutting edge active "
        + "aerodynamics system. Join Senior Engineer Stephen Paine for an in-depth look at how the system works.",
        "New carbon fibre body panels, motorsport-inspired polycarbonate glazing, a unique dramatic titanium exhaust "
        + "system and not to mention light dual-spring suspension. #765LT",
        "Join me for my Home for the Holidays Special where all your favorite seasonal tunes will be brought to life "
        + "by stylized sets and glittering costumes, with the best view right from your home. Tickets on sale tomorrow "
        + "10/29 at 9am PT! Ticket options here: https://found.ee/LS_HomeForTheHolidays",
        "Listen to #WhatChildIsThis here: https://found.ee/LS_WhatChildIsThis",
        "Check out all the new items here: https://found.ee/LSmerch",
        "Gratitude post number 4: I’m grateful for the @marcopoloapp. I’m grateful that my friends and family can have "
        + "continuous group conversations to stay connected even through distance. It’s really helped my mental health. "
        + "#givethanks");

    private PostTestData() {
    }

    public static List<Post> getPosts(List<Community> communities) {
        List<Post> posts = new ArrayList<>();
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.NINTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.TENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        posts.add(getPost(
            CREATION_DATES.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
            TITTLES.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
            CONTENTS.get(ArrayIndex.SIXTEENTH_INDEX_OF_ARRAY.index),
            communities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
            false));
        return posts;
    }

    private static Post getPost(final Date creationDate,
                                final String tittle,
                                final String content,
                                final Community community, final Boolean isDeleted) {
        Post post = new Post();
        post.setCreationDate(creationDate);
        post.setTitle(tittle);
        post.setContent(content);
        post.setCommunity(community);
        post.setIsDeleted(isDeleted);
        post.setPostComments(new ArrayList<>());
        return post;
    }

}

