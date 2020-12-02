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
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@ToString(exclude = "systemUser")
@NoArgsConstructor
public class LogoutToken extends AEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SystemUser systemUser;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

}
