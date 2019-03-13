package org.softuni.exodia.domain.entities;

import lombok.NoArgsConstructor;
import org.softuni.exodia.annotations.validation.composite.ValidDocumentContent;
import org.softuni.exodia.annotations.validation.composite.ValidDocumentTitle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document extends BaseUuidEntity {

    @ValidDocumentTitle
    @Column(nullable = false)
    private String title;

    @ValidDocumentContent
    @Lob
    @Column(nullable = false)
    private String content;
}
