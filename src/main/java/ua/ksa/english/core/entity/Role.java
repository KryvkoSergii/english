package ua.ksa.english.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ROLES", uniqueConstraints = @UniqueConstraint(name = "UK_ROLE_NAME", columnNames = "ROLE_NAME"))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role extends AbstactId {
    @Column(name = "ROLE_NAME")
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    public enum RoleName {
        ADMINISTRATOR, USER
    }

}
