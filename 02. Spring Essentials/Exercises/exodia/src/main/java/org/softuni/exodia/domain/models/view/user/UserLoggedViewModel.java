package org.softuni.exodia.domain.models.view.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.softuni.exodia.domain.api.Viewable;
import org.softuni.exodia.domain.entities.User;

@Getter
@NoArgsConstructor
public class UserLoggedViewModel implements Viewable<User> {

    private String id;
    private String username;
}
