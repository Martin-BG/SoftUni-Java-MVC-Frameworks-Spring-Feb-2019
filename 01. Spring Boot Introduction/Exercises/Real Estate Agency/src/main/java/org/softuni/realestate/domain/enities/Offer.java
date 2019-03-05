package org.softuni.realestate.domain.enities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseUuidEntity {

    @NotNull
    @Positive
    @DecimalMax("999999.99")
    @Digits(integer = 8, fraction = 2)
    @Column(nullable = false, columnDefinition = "DECIMAL(8, 2)")
    private BigDecimal apartmentRent;


    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String apartmentType;

    @NotNull
    @PositiveOrZero
    @DecimalMax("100.00")
    @Digits(integer = 3, fraction = 2)
    @Column(nullable = false, columnDefinition = "DECIMAL(5, 2)")
    private BigDecimal agencyCommission;
}
