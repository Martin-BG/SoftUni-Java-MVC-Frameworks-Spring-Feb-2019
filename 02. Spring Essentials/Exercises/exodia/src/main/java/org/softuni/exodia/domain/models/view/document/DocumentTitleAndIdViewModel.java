package org.softuni.exodia.domain.models.view.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.Document;

@Getter
@NoArgsConstructor
public class DocumentTitleAndIdViewModel implements Viewable<Document> {

    private String id;
    private String title;

    public static DocumentTitleAndIdViewModel from(String id, String title) {
        DocumentTitleAndIdViewModel document = new DocumentTitleAndIdViewModel();
        document.id = id;
        document.title = title;
        return document;
    }
}
