package org.softuni.exodia.domain.models.binding.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.exodia.domain.api.Bindable;
import org.softuni.exodia.domain.entities.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class DocumentScheduleBindingModel implements Bindable<Document> {

    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    private String content;
}
