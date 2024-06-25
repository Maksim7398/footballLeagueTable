package com.football.persist.seq_gen;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import static jakarta.persistence.DiscriminatorType.CHAR;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "others")
//@DiscriminatorValue("C") @DiscriminatorColumn(name="subclass", discriminatorType=CHAR)
public class Others {


    @Id
    @GenericGenerator(
            name = "assigned-sequence",
            strategy = "com.football.persist.seq_gen.StringSequenceIdentifier",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name", value = "hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_prefix", value = "C"),
                    @org.hibernate.annotations.Parameter(
                            name = "increment_size", value = "1"),
            }
    )
    @GeneratedValue(
            generator = "assigned-sequence"
    )
    private String id;

    private String name;
}


