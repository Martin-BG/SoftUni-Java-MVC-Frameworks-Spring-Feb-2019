package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;
import org.softuni.residentevil.repository.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Log
@Service
@Validated
@Transactional
@CacheConfig(cacheNames = "viruses")
public class VirusServiceImpl extends BaseService<Virus, UUID, VirusRepository> implements VirusService {

    @Autowired
    public VirusServiceImpl(VirusRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @Override
    public List<VirusSimpleViewModel> getViruses() {
        return repository.findAllSimpleView();
    }

    @Override
    public void createVirus(@NotNull VirusBindingModel virus) {
        create(virus);
    }

    @Override
    public void updateVirus(@NotNull VirusBindingModel virus) {
        if (repository.getOne(virus.getId()) != null) {
            create(virus);
        }
    }
}
