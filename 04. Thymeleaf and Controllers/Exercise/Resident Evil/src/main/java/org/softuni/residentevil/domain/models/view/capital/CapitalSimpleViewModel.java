package org.softuni.residentevil.domain.models.view.capital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.residentevil.domain.api.Viewable;
import org.softuni.residentevil.domain.entities.Capital;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CapitalSimpleViewModel implements Viewable<Capital> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
}
