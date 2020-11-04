package com.senla.socialnetwork.domain;

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
@ToString
@NoArgsConstructor
public class Post extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "tittle")
    private String tittle;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communities_id", nullable = false)
    private Community community;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(mappedBy = "post_comments")
    private List<PostComment> postComments = new ArrayList<>();

}
