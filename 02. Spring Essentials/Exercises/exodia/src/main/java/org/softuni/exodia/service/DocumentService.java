package org.softuni.exodia.service;

import org.softuni.exodia.domain.entities.Document;
import org.softuni.exodia.domain.models.binding.document.DocumentScheduleBindingModel;
import org.softuni.exodia.domain.models.view.document.DocumentTitleAndIdViewModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentService extends Service<Document, UUID> {

    Optional<String> schedule(DocumentScheduleBindingModel bindingModel);

    boolean print(String id);

    List<DocumentTitleAndIdViewModel> findAllShortView();
}
