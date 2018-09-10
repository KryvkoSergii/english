//package ua.ksa.english.core.entity;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Entity
//@Table(name = "ROLES", uniqueConstraints = @UniqueConstraint(name = "UK_ROLE_NAME", columnNames = "ROLE_NAME"))
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private UUID id;
//    @Column(name = "ROLE_NAME")
//    @Enumerated(value = EnumType.STRING)
//    private RoleName roleName;
//
//    public enum RoleName {
//        ADMINISTRATOR, USER
//    }
//
//}
