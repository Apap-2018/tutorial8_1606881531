package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserRoleController {
    @Autowired
    UserRoleService userService;

    @RequestMapping(value = "/user/updatePass", method = RequestMethod.GET)
    public String updatePassword() {
        return "update-password";
    }

    @RequestMapping(value = "/user/updatePass", method = RequestMethod.POST)
    public String updatePasswordForm(@RequestParam("username") String username,
                                 @RequestParam("passLama") String passLama,
                                 @RequestParam("passBaru") String passBaru,
                                 @RequestParam("passBaruConfirm") String passBaruConfirm) {
        BCryptPasswordEncoder bcrpyt = new BCryptPasswordEncoder();
        UserRoleModel user = userService.getUserByUsername(username);
        if (bcrpyt.matches(passLama, user.getPassword())) {
            if (passBaru.equals(passBaruConfirm)) {
                user.setPassword(passBaru);
                userService.addUser(user);
            }
        }
        return "update";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUserSubmit(@ModelAttribute UserRoleModel user) {
        userService.addUser(user);
        return "home";
    }

}
