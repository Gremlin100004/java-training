package com.senla.socialnetwork.domain;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "communities")
@Getter
@Setter
@ToString(exclude = {"posts", "subscribers"})
@NoArgsConstructor
public class Community extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToOne
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
    @ManyToMany(mappedBy = "communitiesSubscribedTo", fetch = FetchType.LAZY)
    private List<UserProfile> subscribers;

}
