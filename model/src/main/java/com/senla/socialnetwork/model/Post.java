package com.senla.socialnetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString(exclude = {"community", "postComments"})
@NoArgsConstructor
public class Post extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "title", length = 1000)
    private String title;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "content", length = 8000)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communities_id", nullable = false)
    private Community community;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments = new ArrayList<>();

}
