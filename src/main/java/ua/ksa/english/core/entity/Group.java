package ua.ksa.english.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group extends AbstactId{
    @Column(name = "GROUP_NAME")
    private String name;
}
