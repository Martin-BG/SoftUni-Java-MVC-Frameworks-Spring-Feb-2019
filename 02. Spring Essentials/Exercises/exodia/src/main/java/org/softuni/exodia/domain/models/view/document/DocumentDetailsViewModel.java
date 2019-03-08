package org.softuni.exodia.domain.models.view.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.Document;

@Getter
@NoArgsConstructor
public class DocumentDetailsViewModel implements Viewable<Document> {

    private String id;
    private String title;
    private String content;
}
