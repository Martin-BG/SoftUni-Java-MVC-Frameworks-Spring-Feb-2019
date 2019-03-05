package org.softuni.realestate.domain.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.realestate.domain.enities.Offer;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class OfferViewModel implements Viewable<Offer> {

    private String id;
    private BigDecimal apartmentRent;
    private String apartmentType;
    private BigDecimal agencyCommission;
}
