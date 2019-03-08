package org.softuni.exodia.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.exodia.domain.entities.Document;
import org.softuni.exodia.domain.models.binding.document.DocumentScheduleBindingModel;
import org.softuni.exodia.domain.models.view.document.DocumentTitleAndIdViewModel;
import org.softuni.exodia.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
@Service
public class DocumentServiceImpl extends BaseService<Document, UUID, DocumentRepository> implements DocumentService {

    private static final int MAX_TITLE_LENGTH = 12;

    @Autowired
    public DocumentServiceImpl(DocumentRepository repository,
                               Validator validator,
                               ModelMapper mapper) {
        super(repository, validator, mapper);
    }

    private static String getShortTitle(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            return title.substring(0, MAX_TITLE_LENGTH) + "...";
        }
        return title;
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @Override
    public Optional<String> schedule(DocumentScheduleBindingModel bindingModel) {
        return createAndGet(bindingModel, DocumentTitleAndIdViewModel.class)
                .map(DocumentTitleAndIdViewModel::getId);
    }

    @Override
    public boolean print(String id) {
        return deleteById(UUID.fromString(id));
    }

    @Override
    public List<DocumentTitleAndIdViewModel> findAllShortView() {
        return repository
                .findAllShortView()
                .stream()
                .map(tuple -> DocumentTitleAndIdViewModel.from(
                        tuple.get("id").toString(),
                        getShortTitle((String) tuple.get("title")))
                )
                .collect(Collectors.toList());
    }
}
