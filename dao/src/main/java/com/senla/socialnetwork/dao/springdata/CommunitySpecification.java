package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Community_;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class CommunitySpecification {
    private CommunitySpecification() {
    }

    public static Specification<Community> communityIsDeleted(final boolean isDeleted) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(
            root.get(Community_.isDeleted), isDeleted);
    }

    public static Specification<Community> communityBelongToType(final CommunityType type) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Community_.type), type);
    }

    public static Specification<Community> emailLike(final String email) {
        return (Specification<Community>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Community, UserProfile> communityUserProfileJoin = root.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                UserProfile_.systemUser);
            return criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email);
        };
    }

    public static Specification<Community> emailAndIdLike(final Long id, final String email) {
        return (Specification<Community>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Community, UserProfile> communityUserProfileJoin = root.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                    UserProfile_.systemUser);
            return criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                    SystemUser_.email), email), criteriaBuilder.equal(root.get(Community_.id), id));
        };
    }

}
