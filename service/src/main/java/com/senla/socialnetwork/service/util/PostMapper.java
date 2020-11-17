package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.dto.PostDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto getPostDto(Post post) {
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
        postDto.setDeleted(post.isDeleted());
        return postDto;
    }

    public static List<PostDto> getPostDto(List<Post> posts) {
        return posts.stream()
                .map(PostMapper::getPostDto)
                .collect(Collectors.toList());
    }

    public static Post getPost(PostDto postDto,
                               PostDao postDao,
                               CommunityDao communityDao,
                               UserProfileDao userProfileDao,
                               LocationDao locationDao,
                               SchoolDao schoolDao,
                               UniversityDao universityDao) {
        Post post;
        if (postDto.getId() == null) {
            post = new Post();
        } else {
            post = postDao.findById(postDto.getId());
        }
        post.setTittle(postDto.getTittle());
        post.setContent(postDto.getContent());
        post.setCommunity(CommunityMapper.getCommunity(
                postDto.getCommunity(), communityDao, userProfileDao, locationDao, schoolDao, universityDao));
        post.setDeleted(postDto.isDeleted());
        return post;
    }

}
