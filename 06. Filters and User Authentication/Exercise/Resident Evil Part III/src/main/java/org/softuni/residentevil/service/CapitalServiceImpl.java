package org.softuni.residentevil.service;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Capital;
import org.softuni.residentevil.domain.models.projections.capital.CapitalAllProjection;
import org.softuni.residentevil.domain.models.view.capital.CapitalSimpleViewModel;
import org.softuni.residentevil.repository.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.logging.Logger;

@Log
@Service
@Validated
@Transactional
@CacheConfig(cacheNames = CapitalServiceImpl.CAPITALS)
public class CapitalServiceImpl extends BaseService<Capital, Long, CapitalRepository> implements CapitalService {

    public static final String CAPITALS = "capitals";
    private static final Sort SORT_ASC_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    @Autowired
    public CapitalServiceImpl(CapitalRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Logger logger() {
        return log;
    }

    @Override
    @Cacheable(key = "#root.method", sync = true)
    @Transactional(readOnly = true)
    public List<CapitalSimpleViewModel> getCapitals() {
        return repository.findAllSimpleView();
    }

    @Override
    @Cacheable(key = "#root.method", sync = true)
    @Transactional(readOnly = true)
    public List<CapitalAllProjection> getCapitalsDetailed() {
        return repository.findAllProjectedBy(CapitalAllProjection.class, SORT_ASC_BY_NAME);
    }
}
