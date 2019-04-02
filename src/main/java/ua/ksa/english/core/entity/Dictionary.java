package ua.ksa.english.core.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ua.ksa.english.core.utils.ProtectedResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "DICTIONARIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Dictionary implements ProtectedResource {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    protected UUID dictionaryId;
    //user-frendly name
    @Column(name = "NAME", nullable = false)
    protected String name;
    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Collection<Container> containers;
    @ManyToOne
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_DICTIONARIES_USERS"))
    protected User owner;

    @Override
    public boolean isAccessDeniedToResourceForUser(User loggedUser) {
        return Objects.isNull(owner) || Objects.isNull(loggedUser) || !owner.equals(loggedUser);
    }
}
