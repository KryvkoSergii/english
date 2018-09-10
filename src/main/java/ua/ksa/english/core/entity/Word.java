package ua.ksa.english.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "URI")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Word extends AbstactId {
    @Column(name = "ENGLISH_WORD")
    public String english;
    @Column(name = "TRANSLATE")
    public String translate;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "WORDS_GROUPS",
//            joinColumns = {@JoinColumn(name = "WORD_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USERS_ID"))} ,
//            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_GROUPS_ID"))})
//    public Collection<Group> allowedGroups;

}
