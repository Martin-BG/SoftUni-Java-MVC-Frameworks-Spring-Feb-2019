package org.softuni.realestate.service;

import org.modelmapper.ModelMapper;
import org.softuni.realestate.domain.enities.Offer;
import org.softuni.realestate.domain.models.binding.Bindable;
import org.softuni.realestate.domain.models.binding.OfferFindBindingModel;
import org.softuni.realestate.domain.models.view.Viewable;
import org.softuni.realestate.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class OfferServiceImpl extends BaseService<Offer, UUID, OfferRepository> implements OfferService {

    private static final Logger LOG = Logger.getLogger(OfferServiceImpl.class.getName());

    @Autowired
    public OfferServiceImpl(OfferRepository repository,
                            Validator validator,
                            ModelMapper mapper) {
        super(repository, validator, mapper);
    }

    private static BigDecimal calcCost(Offer offer) {
        BigDecimal commission = offer.getAgencyCommission().multiply(BigDecimal.valueOf(0.01));
        return offer.getApartmentRent().multiply(BigDecimal.ONE.add(commission));
    }

    @Override
    protected Logger logger() {
        return LOG;
    }

    @Override
    public <B extends Bindable<Offer>> boolean registerOffer(B bindingModel) {
        return create(bindingModel);
    }

    @Override
    public <V extends Viewable<Offer>> Optional<V>
    findOffer(OfferFindBindingModel bindingModel, Class<V> viewModelClass) {
        if (validateModel(bindingModel)) {
            return repository
                    .findAllByApartmentTypeEquals(bindingModel.getApartmentType())
                    .stream()
                    .filter(o -> bindingModel.getFamilyBudget().compareTo(calcCost(o)) >= 0)
                    .findFirst()
                    .map(o -> {
                        repository.delete(o);
                        return mapper.map(o, viewModelClass);
                    });
        }
        return Optional.empty();
    }
}
