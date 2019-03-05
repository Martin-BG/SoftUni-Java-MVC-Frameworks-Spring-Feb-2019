package org.softuni.realestate.service;

import org.softuni.realestate.domain.enities.Offer;
import org.softuni.realestate.domain.models.binding.Bindable;
import org.softuni.realestate.domain.models.binding.OfferFindBindingModel;
import org.softuni.realestate.domain.models.view.Viewable;

import java.util.Optional;
import java.util.UUID;

public interface OfferService extends Service<Offer, UUID> {

    <B extends Bindable<Offer>> boolean registerOffer(B bindingModel);

    <V extends Viewable<Offer>> Optional<V> findOffer(OfferFindBindingModel bindingModel, Class<V> viewModelClass);
}
