package org.softuni.realestate.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.realestate.domain.enities.Offer;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OfferRegisterBindingModel implements Bindable<Offer> {

    @NotNull
    @Positive
    @DecimalMax("999999.99")
    private BigDecimal apartmentRent;


    @NotBlank
    @Size(min = 1, max = 255)
    private String apartmentType;

    @NotNull
    @PositiveOrZero
    @DecimalMax("100.00")
    private BigDecimal agencyCommission;
}
