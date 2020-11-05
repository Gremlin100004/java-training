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
@ToString(exclude = {"systemUser", "location", "school", "university", "publicMessageComments", "publicMessages",
                     "privateMessage", "friendshipRequests", "friends", "communities", "postComments"})
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private Location school;
    @Column(name = "school_graduation_year")
    private Integer schoolGraduationYear;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private Location university;
    @Column(name = "university_graduation_year")
    private Integer universityGraduationYear;
    @OneToMany(mappedBy = "author")
    private List<PublicMessage> publicMessages = new ArrayList<>();
    // ToDo need this field ?
    @OneToMany(mappedBy = "author")
    private List<PublicMessageComment> publicMessageComments = new ArrayList<>();
    // ToDo need this field ?
    @OneToMany(mappedBy = "sender")
    private List<PrivateMessage> privateMessage = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "friendship_requests", joinColumns = @JoinColumn(name = "user_profiles_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UserProfile> friendshipRequests = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_profiles_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UserProfile> friends = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private List<Community> communities = new ArrayList<>();
    // ToDo need this field ?
    @OneToMany(mappedBy = "author")
    private List<PostComment> postComments = new ArrayList<>();

}
