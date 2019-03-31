package org.softuni.residentevil.web.controllers.user;

import org.softuni.residentevil.config.WebConfig;
import org.softuni.residentevil.domain.models.binding.user.UserRoleBindingModel;
import org.softuni.residentevil.domain.models.view.user.UserViewModel;
import org.softuni.residentevil.service.UserService;
import org.softuni.residentevil.web.annotations.Layout;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Layout
@Controller
@RequestMapping(WebConfig.URL_USER_ALL)
public class AllUserController extends BaseController {

    public static final String USERS = "users";

    private static final String VIEW_ALL = "user/all";

    private final UserService service;

    public AllUserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String get(Model model) {
        List<UserViewModel> users = service.allUsers();

        model.addAttribute(USERS, users);

        return VIEW_ALL;
    }

    @PatchMapping
    public String patch(@ModelAttribute UserRoleBindingModel userRoleBindingModel) {
        service.updateRole(userRoleBindingModel);

        return redirect(WebConfig.URL_USER_ALL);
    }
}
