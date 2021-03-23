package com.senla.socialnetwork.model;

import com.senla.socialnetwork.model.enumaration.CommunityType;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "communities")
@NamedEntityGraph(
    name = "graph.Community",
    attributeNodes = @NamedAttributeNode(value = "author", subgraph = "subgraph.author"),
    subgraphs = {
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "school", subgraph = "subgraph.school")),
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "location", subgraph = "subgraph.location")),
        @NamedSubgraph(name = "subgraph.author",
            attributeNodes = @NamedAttributeNode(value = "university", subgraph = "subgraph.university")) })

@Getter
@Setter
@ToString(exclude = {"posts", "subscribers"})
@NoArgsConstructor

public class Community extends AEntity {
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserProfile author;
    @Enumerated(EnumType.STRING)
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "type", length = 20)
    private CommunityType type = CommunityType.GENERAL;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "title", length = 100)
    private String title;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "information", length = 1000)
    private String information;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    @ManyToMany(mappedBy = "communitiesSubscribedTo", fetch = FetchType.LAZY)
    private List<UserProfile> subscribers;

}
