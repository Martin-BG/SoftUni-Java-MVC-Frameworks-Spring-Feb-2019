package org.softuni.cardealer.service;

import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartServiceModel savePart(PartServiceModel partServiceModel) {

        Part part = this.modelMapper.map(partServiceModel, Part.class);
        this.partRepository.saveAndFlush(part);

        return this.modelMapper.map(part, PartServiceModel.class);

    }

    @Override
    public PartServiceModel editPart(PartServiceModel partServiceModel) {
        Part part = this.partRepository.findById(partServiceModel.getId()).orElse(null);
        part.setName(partServiceModel.getName());
        part.setPrice(partServiceModel.getPrice());

        Part edited = this.partRepository.saveAndFlush(part);

        return this.modelMapper.map(edited, PartServiceModel.class);

    }

    @Override
    public PartServiceModel deletePart(String id) {
        Part part = this.partRepository.findById(id).orElseThrow();

        this.partRepository.delete(part);
        return this.modelMapper.map(part, PartServiceModel.class);

    }

    @Override
    public PartServiceModel findPartById(String id) {
        Part part = this.partRepository.findById(id).orElse(null);

        return this.modelMapper.map(part, PartServiceModel.class);

    }
}
