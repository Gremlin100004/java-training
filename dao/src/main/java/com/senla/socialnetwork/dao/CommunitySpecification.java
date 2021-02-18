package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.*;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

@Slf4j
public class CommunitySpecification {

    public static Specification<Community> communityIsDeleted(boolean isDeleted) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(
            root.get(Community_.isDeleted), isDeleted);
    }

    public static Specification<Community> communityBelongToType(CommunityType type) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Community_.type), type);
    }

    public static Specification<Community> emailLike(String email) {
        return (Specification<Community>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Community, UserProfile> communityUserProfileJoin = root.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                    UserProfile_.systemUser);
            return criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email);
        };
    }

    public static Specification<Community> emailAndIdLike(Long id, String email) {
        return (Specification<Community>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Community, UserProfile> communityUserProfileJoin = root.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                    UserProfile_.systemUser);
            return criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                    SystemUser_.email), email), criteriaBuilder.equal(root.get(Community_.id), id));
        };
    }

}
