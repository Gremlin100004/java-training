package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto getPostDto(final Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setCreationDate(post.getCreationDate());
        if (post.getTittle() != null) {
            postDto.setTittle(post.getTittle());
        }
        if (post.getContent() != null) {
            postDto.setContent(post.getContent());
        }
        postDto.setCommunity(CommunityMapper.getCommunityDto(post.getCommunity()));
        postDto.setDeleted(post.getIsDeleted());
        return postDto;
    }

    public static List<PostDto> getPostDto(final List<Post> posts) {
        return posts.stream()
                .map(PostMapper::getPostDto)
                .collect(Collectors.toList());
    }

    public static Post getPost(final PostDto postDto,
                               final PostDao postDao,
                               final CommunityDao communityDao,
                               final UserProfileDao userProfileDao) {
        Post post = postDao.findById(postDto.getId());
        post.setTittle(postDto.getTittle());
        post.setContent(postDto.getContent());
        post.setCommunity(CommunityMapper.getCommunity(postDto.getCommunity(), communityDao, userProfileDao));
        post.setIsDeleted(postDto.getDeleted());
        return post;
    }

    public static Post getNewPost(final PostForCreationDto postDto, final Community community) {
        Post post = new Post();
        post.setTittle(postDto.getTittle());
        post.setContent(postDto.getContent());
        post.setCommunity(community);
        post.setCreationDate(new Date());
        return post;
    }

}
