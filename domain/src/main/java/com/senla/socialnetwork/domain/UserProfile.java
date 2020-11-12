package com.senla.socialnetwork.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@ToString(exclude = {"systemUser", "publicMessages", "senderPrivateMessage", "recipientPrivateMessage",
                     "friendshipRequests", "friends", "mappedByFriends", "friendshipRequests",
                     "mappedByFriendshipRequests", "communities", "postComments"})
@NoArgsConstructor
public class UserProfile extends AEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SystemUser systemUser;
    @Column(name = "registration_date")
    private Date registrationDate;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "telephone_number")
    private String telephone_number;
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToOne
    @JoinColumn(name = "school_id")
    private School school;
    @Column(name = "school_graduation_year")
    private Integer schoolGraduationYear;
    @OneToOne
    @JoinColumn(name = "university_id")
    private University university;
    @Column(name = "university_graduation_year")
    private Integer universityGraduationYear;
    @OneToMany(mappedBy = "author")
    private List<PublicMessage> publicMessages = new ArrayList<>();

    // ToDo need this field ?
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

    @OneToMany(mappedBy = "author")
    private List<Community> ownCommunities = new ArrayList<>();
    // ToDo need this field ?
    @OneToMany(mappedBy = "author")
    private List<PostComment> postComments = new ArrayList<>();
    @ManyToMany(mappedBy = "subscribedUsers", fetch = FetchType.LAZY)
    private List<UserProfile>subscribedToCommunities;

}
