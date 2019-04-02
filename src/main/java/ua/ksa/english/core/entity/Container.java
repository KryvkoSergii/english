package ua.ksa.english.core.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WORDS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Container {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    protected UUID containerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DICTIONARY_ID", foreignKey = @ForeignKey(name = "FK_CONTAINS_DICTIONARY"))
    protected Dictionary dictionary;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WORD_ID", foreignKey = @ForeignKey(name = "FK_CONTAINS_WORD"))
    protected Word word;
    @CreatedDate
    @Column(name = "CREATED")
    protected Date created;
    //some criteria to be used in frequencies show
    @Column(name = "IN_INDEX")
    protected Integer index;

    public Container(Dictionary dictionary, Word word) {
        this.dictionary = dictionary;
        this.word = word;
    }
}
