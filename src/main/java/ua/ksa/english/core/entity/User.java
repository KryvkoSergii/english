package ua.ksa.english.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"roles","groups"})
public class User extends AbstactId {
    @Column(name = "USER_NAME")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_ROLES_USERS_ID"))} ,
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_ROLES_ROLES_ID"))})
    private Collection<Role> roles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_GROUPS",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_GROUPS_USERS_ID"))} ,
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_GROUPS_GROUPS_ID"))})
    private Collection<Group> groups;
}
