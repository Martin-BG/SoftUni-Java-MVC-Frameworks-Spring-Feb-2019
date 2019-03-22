package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.binding.virus.VirusBindingModel;
import org.softuni.residentevil.domain.models.projections.virus.VirusReleasedOnProjection;
import org.softuni.residentevil.domain.models.view.virus.VirusSimpleViewModel;
import org.softuni.residentevil.repository.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Log
@Service
@Validated
@Transactional
public class VirusServiceImpl extends BaseService<Virus, UUID, VirusRepository> implements VirusService {

    private static final String ALL_VIRUSES = "allVirusesCache";
    private static final String VIRUSES = "virusesCache";

    @Autowired
    public VirusServiceImpl(VirusRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ALL_VIRUSES, sync = true)
    public List<VirusSimpleViewModel> getViruses() {
        return repository.findAllSimpleView();
    }

    @Override
    @CacheEvict(cacheNames = ALL_VIRUSES, allEntries = true)
    public void createVirus(@NotNull VirusBindingModel virus) {
        create(virus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = VIRUSES, key = "#id")
    public Optional<VirusBindingModel> readVirus(@NotNull UUID id) {
        return findById(id, VirusBindingModel.class);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = ALL_VIRUSES, allEntries = true),
            @CacheEvict(cacheNames = VIRUSES, key = "#virus.id")})
    public void updateVirus(@NotNull VirusBindingModel virus) {
        // Ensure virus with that ID exists and releasedOn date is not modified
        repository
                .findProjectedById(virus.getId(), VirusReleasedOnProjection.class)
                .map(view -> {
                    virus.setReleasedOn(view.getReleasedOn());
                    return view;
                })
                .orElseThrow();

        create(virus);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = ALL_VIRUSES, allEntries = true),
            @CacheEvict(cacheNames = VIRUSES, key = "#id")})
    public void deleteVirus(@NotNull UUID id) {
        deleteById(id);
    }
}
