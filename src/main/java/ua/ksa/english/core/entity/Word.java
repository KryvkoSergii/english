package ua.ksa.english.core.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "WORDS",
        uniqueConstraints = @UniqueConstraint(name = "UK_WORDS_FOREIGN", columnNames = {"FOREIGN", "TRANSLATE"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Word {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    protected UUID wordId;
    @Column(name = "FOREIGN")
    protected String foreign;
    @Column(name = "TRANSLATE")
    protected String translate;

    public Word(String foreign, String translate) {
        this.foreign = foreign;
        this.translate = translate;
    }
}
