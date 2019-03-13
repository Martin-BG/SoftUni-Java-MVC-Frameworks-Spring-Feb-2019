package org.softuni.exodia.domain.models.binding.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.exodia.annotations.validation.composite.ValidDocumentContent;
import org.softuni.exodia.annotations.validation.composite.ValidDocumentTitle;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.entities.Document;

@Getter
@Setter
@NoArgsConstructor
public class DocumentScheduleBindingModel implements Bindable<Document> {

    @ValidDocumentTitle
    private String title;

    @ValidDocumentContent
    private String content;
}
