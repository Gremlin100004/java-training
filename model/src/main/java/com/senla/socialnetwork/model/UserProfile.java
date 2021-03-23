package com.senla.socialnetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@NamedEntityGraph(
    name = "graph.UserProfile.communitiesSubscribedTo",
    attributeNodes = @NamedAttributeNode(value = "communitiesSubscribedTo", subgraph = "subgraph.communitiesSubscribedTo"),
    subgraphs = {
        @NamedSubgraph(name = "subgraph.communitiesSubscribedTo",
            attributeNodes = @NamedAttributeNode(value = "author", subgraph = "subgraph.author")),
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "school", subgraph = "subgraph.school")),
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "location", subgraph = "subgraph.location")),
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "university", subgraph = "subgraph.university")) })
@Getter
@Setter
@ToString(exclude = {"systemUser", "publicMessages", "senderPrivateMessage", "recipientPrivateMessage",
                     "friendshipRequests", "friends", "mappedByFriends", "friendshipRequests",
                     "mappedByFriendshipRequests", "ownCommunities", "communitiesSubscribedTo"})
@NoArgsConstructor
public class UserProfile extends AEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private SystemUser systemUser;
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "name", length = 45)
    private String name;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "surname", length = 45)
    private String surname;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "telephone_number", length = 20)
    private String telephoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @Column(name = "school_graduation_year")
    private Integer schoolGraduationYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;
    @Column(name = "university_graduation_year")
    private Integer universityGraduationYear;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<PublicMessage> publicMessages = new ArrayList<>();
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<PrivateMessage> senderPrivateMessage = new ArrayList<>();
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private List<PrivateMessage> recipientPrivateMessage = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "friendship_requests", joinColumns = @JoinColumn(name = "user_profiles_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UserProfile> friendshipRequests = new ArrayList<>();
    @ManyToMany(mappedBy = "friendshipRequests", fetch = FetchType.LAZY)
    private List<UserProfile> mappedByFriendshipRequests = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_profiles_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UserProfile> friends = new ArrayList<>();
    @ManyToMany(mappedBy = "friends", fetch = FetchType.LAZY)
    private List<UserProfile> mappedByFriends = new ArrayList<>();
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Community> ownCommunities = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "community_user", joinColumns = @JoinColumn(name = "user_profiles_id"),
        inverseJoinColumns = @JoinColumn(name = "communities_id"))
    private List<Community> communitiesSubscribedTo;

}
