//package ua.ksa.english.core.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Collection;
//import java.util.UUID;
//
//@Entity
//@Table(name = "USERS")
//@NoArgsConstructor @Getter @Setter @EqualsAndHashCode(exclude = {"roles","groups"})
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private UUID id;
//    @Column(name = "USER_NAME")
//    private String name;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "USERS_ROLES",
//            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_ID"))} ,
//            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ROLES_ID"))})
//    private Collection<Role> roles;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "USERS_GROUPS",
//            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_ID"))} ,
//            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_GROUPS_ID"))})
//    private Collection<Group> groups;
//}
