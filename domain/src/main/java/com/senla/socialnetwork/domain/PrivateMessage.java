package com.senla.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "private_messages")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PrivateMessage extends AEntity {
    @Column(name = "departure_date", nullable = false)
    private Date departureDate;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserProfile sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserProfile recipient;
    @Column(name = "content")
    private String content;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "is_deleted")
    private boolean isDeleted;

}
