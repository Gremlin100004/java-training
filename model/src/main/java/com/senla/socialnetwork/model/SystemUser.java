package com.senla.socialnetwork.model;

import com.senla.socialnetwork.model.enumaration.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class SystemUser extends AEntity {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "password", nullable = false, length = 256)
    private String password;
    @Enumerated(EnumType.STRING)
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "role", nullable = false, length = 20)
    private RoleName role;
    @OneToOne(mappedBy = "systemUser", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;

    @Override
    public String toString() {
        return "SystemUser{"
               + "email='" + email + '\''
               + ", password='************'"
               + ", role=" + role
               + ", id=" + id
               + '}';
    }

}
