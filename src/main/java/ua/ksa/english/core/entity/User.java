package ua.ksa.english.core.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    protected UUID userId;
    //username identity
    @Column(name = "USERNAME")
    protected String username;
    @Column(name = "ENABLED")
    protected Boolean enabled;
}
