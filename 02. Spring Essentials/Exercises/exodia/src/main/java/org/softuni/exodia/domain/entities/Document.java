package org.softuni.exodia.domain.entities;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document extends BaseUuidEntity {

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Lob
    @Column(nullable = false)
    private String content;
}
