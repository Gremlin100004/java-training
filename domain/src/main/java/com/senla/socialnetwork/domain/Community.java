package com.senla.socialnetwork.domain;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "communities")
@Getter
@Setter
@ToString(exclude = {"author", "posts"})
@NoArgsConstructor
public class Community extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserProfile author;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CommunityType type = CommunityType.GENERAL;
    @Column(name = "tittle")
    private String tittle;
    @Column(name = "information")
    private String information;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(mappedBy = "community")
    private List<Post> posts = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "community_user", joinColumns = @JoinColumn(name = "communities_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<UserProfile>subscribedUsers;
}
