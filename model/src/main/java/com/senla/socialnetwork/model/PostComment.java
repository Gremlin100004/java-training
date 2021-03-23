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
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "post_comments")
@Getter
@Setter
@ToString(exclude = "post")
@NoArgsConstructor
public class PostComment extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserProfile author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "content", length = 1000)
    private String content;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
