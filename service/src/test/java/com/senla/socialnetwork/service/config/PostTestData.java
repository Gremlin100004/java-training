package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostTestData {
    private static final Long POST_ID = 1L;
    private static final Long POST_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_POSTS = 2L;
    private static final Date POST_CREATION_DATE = new Date();
    private static final String POST_TITTLE = "Test";

    public static Long getPostId() {
        return POST_ID;
    }

    public static Long getRightNumberPosts() {
        return RIGHT_NUMBER_POSTS;
    }

    public static Post getTestPost() {
        Post post = new Post();
        post.setId(POST_ID);
        post.setTittle(POST_TITTLE);
        post.setCreationDate(POST_CREATION_DATE);
        post.setCommunity(CommunityTestData.getTestCommunity());
        return post;
    }

    public static PostDto getTestPostDto() {
        PostDto postDto = new PostDto();
        postDto.setId(POST_ID);
        postDto.setTittle(POST_TITTLE);
        postDto.setCreationDate(POST_CREATION_DATE);
        postDto.setCommunity(CommunityTestData.getTestCommunityDto());
        return postDto;
    }

    public static PostForCreationDto getTestPostForCreationDto() {
        PostForCreationDto postDto = new PostForCreationDto();
        postDto.setTittle(POST_TITTLE);
        return postDto;
    }

    public static List<Post> getTestPosts() {
        Post postOne = getTestPost();
        Post postTwo = getTestPost();
        postTwo.setId(POST_OTHER_ID);
        List<Post> posts = new ArrayList<>();
        posts.add(postOne);
        posts.add(postTwo);
        return posts;
    }

    public static List<PostDto> getTestPostsDto() {
        PostDto postDtoOne = getTestPostDto();
        PostDto postDtoTwo = getTestPostDto();
        postDtoTwo.setId(POST_OTHER_ID);
        List<PostDto> postsDto = new ArrayList<>();
        postsDto.add(postDtoOne);
        postsDto.add(postDtoTwo);
        return postsDto;
    }

}
