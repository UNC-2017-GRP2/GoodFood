package com.netcracker.controller;

import com.netcracker.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.netcracker.model.User;
import com.netcracker.service.UserService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    /*
    +List<User> getAllUsers();
    +User getByUsername(String username);
    +void saveUser(User user);
    +void updateUser(User oldUser, User newUser);
    +void changeRole(BigInteger userId, String role);
    +boolean isLoginExist(String login);
    +boolean isEmailExist(String email);
    +boolean isYourLoginForUpdateUser(String login, String password);
    +boolean isYourEmailForUpdateUser(String email, String password);
    +boolean isEqualsPassword(String password, BigInteger userId);
    +void updatePassword(BigInteger userId, String password);
    +void updateAddresses(BigInteger userId, List<Address> addresses);
     */
    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public  User getUserByUsername(@PathVariable( "username" ) String username){
        return userService.getByUsername(username);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public  User getUserById(@PathVariable( "id" ) BigInteger id){
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        //TODO: сделать проверку на то, что юзер существует
        userService.saveUser(user);
        //TODO: поменять в сервисе void методы на Long, чтобы можно было возвращать код результата/ошибки
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "username" ) String username, @RequestBody User user) {
        //TODO:аналогично с методом сверху
        User oldUser = userService.getByUsername(username);
        userService.updateUser(oldUser,user);
    }

    @RequestMapping(value = "/id/{id}/update/password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable( "id" ) BigInteger userId, @RequestBody String password) {
        //TODO:аналогично с методом сверху
        userService.updatePassword(userId, password);
    }
    @RequestMapping(value = "/id/{id}/update/addresses", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateAddresses(@PathVariable( "id" ) BigInteger userId, @RequestBody List<Address> addresses) {
        //TODO:аналогично с методом сверху
        userService.updateAddresses( userId, addresses);
    }

    @RequestMapping(value = "/id/{id}/update/role", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeRole(@PathVariable("id")BigInteger userId, @RequestBody String role){
        userService.changeRole(userId, role);
    }

    @RequestMapping(value = "/validate/login/{login}",method = RequestMethod.GET)
    public boolean isLoginExist(@PathVariable("login") String login)
    {return userService.isLoginExist(login);}

    @RequestMapping(value = "/validate/email/{email}",method = RequestMethod.GET)
    public boolean isEmailExist(@PathVariable("email") String email)
    {return userService.isEmailExist(email);}

    @RequestMapping(value = "/validate/login/{login}",method = RequestMethod.POST)
    public boolean sYourLoginForUpdateUser(@PathVariable("login") String login, @RequestBody(required = true) String password)
    {return userService.isYourLoginForUpdateUser(login, password);}

    @RequestMapping(value = "/validate/email/{email}",method = RequestMethod.POST)
    public boolean isYourEmailForUpdateUser(@PathVariable("email") String email, @RequestBody(required = true) String password)
    {
        return userService.isYourEmailForUpdateUser(email,password);
    }

    @RequestMapping(value = "/validate/password/id/{id}", method = RequestMethod.POST)
    public boolean isEqualsPassword(@PathVariable("id") BigInteger id, @RequestBody String password){
        return userService.isEqualsPassword(password,id);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void renoveUserById(@PathVariable("id")BigInteger userId){
        userService.removeUserById(userId);
    }

    @RequestMapping(value = "/object/id", method = RequestMethod.GET)
    public BigInteger getObjectId(){
        return  userService.getObjectId();
    }

    @RequestMapping(value = "/id/{id}/save/imageName", method = RequestMethod.POST)
    public void saveUserImage(@PathVariable( "id" ) BigInteger userId, @RequestBody String imageName) throws SQLException {
        userService.saveUserImage(userId, imageName);
    }
}
