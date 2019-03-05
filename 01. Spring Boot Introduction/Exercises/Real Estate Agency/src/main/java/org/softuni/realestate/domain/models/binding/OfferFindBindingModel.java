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
public class OfferFindBindingModel implements Bindable<Offer> {

    @NotNull
    @Positive
    @DecimalMax("9999999.99")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal familyBudget;


    @NotBlank
    @Size(min = 1, max = 255)
    private String apartmentType;

    @NotBlank
    @Size(min = 1, max = 255)
    private String familyName;
}
