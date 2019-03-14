package org.softuni.exodia.domain.models.view.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.Document;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class DocumentDetailsViewModel implements Viewable<Document>, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String content;
}
