package app.fitness.controllers;


import app.fitness.entities.UserReg;
import app.fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @Autowired
    private UserService userService;



    @GetMapping("/logout/{token}")
    public void logout(@PathVariable String token){
        userService.logout(token);
    }


    @PostMapping("/register")
    public String register(@RequestBody UserReg userReg) {
        return userService.register(userReg);
    }


}
