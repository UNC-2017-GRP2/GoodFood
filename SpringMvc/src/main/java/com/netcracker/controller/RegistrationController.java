package com.netcracker.controller;

import com.netcracker.config.AuthManager;
import com.netcracker.form.MyUserAccountForm;
import com.netcracker.model.User;
import com.netcracker.service.UserService;
import com.netcracker.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository connectionRepository;

    public static AuthenticationManager am = new AuthManager();

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registrationPost(@ModelAttribute("userForm") @Validated User userForm) {
        ModelAndView model = new ModelAndView();
        try {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            userForm.setPasswordHash(encoder.encodePassword(userForm.getPasswordHash(), null));
            userForm.setRole("ROLE_USER");
            userService.saveUser(userForm);

            Authentication request = new UsernamePasswordAuthenticationToken(userForm.getLogin(), userForm.getPasswordHash());
            Authentication authResult = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            model.setViewName("redirect:/profile");
            return model;
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
        finally {
            model.setViewName("redirect:/login");
            return model;
        }
    }

    // User login via Social,
    // but not allow access basic info.
    // webapp will redirect to /signin.
    @RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
    public String signInPage(Model model) {
        return "redirect:/login";
    }

//   @RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
//   public String signupPage(WebRequest request, Model model) {
//
//       ProviderSignInUtils providerSignInUtils //
//               = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//
//       Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
//
//       //
//       MyUserAccountForm myForm = null;
//       //
//       if (connection != null) {
//           myForm = new MyUserAccountForm(connection);
//       } else {
//           myForm = new MyUserAccountForm();
//       }
//       model.addAttribute("myForm", myForm);
//       return "signup";
//   }
//
//   @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
//   public String signupSave(WebRequest request, //
//                            Model model, //
//                            @ModelAttribute("userForm") @Validated MyUserAccountForm userForm, //
//                            BindingResult result, //
//                            final RedirectAttributes redirectAttributes) {
//
//       // If validation has error.
//       if (result.hasErrors()) {
//           return "signup";
//       }
//
//
//       try {
//           userService.registerNewUserAccount(userForm);
//       } catch (Exception ex) {
//           ex.printStackTrace();
//           model.addAttribute("errorMessage", "Error " + ex.getMessage());
//           return "signup";
//       }
//
//       User registered = userService.getByUsername(userForm.getUserName());
//
//       if (userForm.getSignInProvider() != null) {
//           ProviderSignInUtils providerSignInUtils //
//                   = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//
//           // If the user is signing in by using a social provider, this method
//           // call stores the connection to the UserConnection table.
//           // Otherwise, this method does not do anything.
//           providerSignInUtils.doPostSignUp(registered.getUserId().toString(), request);
//       }
//       // After register, Logs the user in.
//       SecurityUtil.logInUser(registered);
//
//       return "redirect:/home";
//   }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            model.setViewName("redirect:/home");
            return model;
        }
        try {
            if (error != null) {
                model.addObject("error", "Invalid Credentials provided.");
            }
            model.setViewName("login");
            model.addObject("userForm", new User());
            return model;
        } catch (Exception e) {
            System.out.println("method loginPage:" + e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    public @ResponseBody
    String checkUsername(@RequestParam String username) {
        if (userService.isLoginExist(username)) {
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public @ResponseBody
    String checkEmail(@RequestParam String email) {
        if (userService.isEmailExist(email)) {
            return "true";
        } else {
            return "false";
        }
    }


}

