package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    CredentialRepository credentialRepository;

    @GetMapping("/")
    public String getLandingPage()
    {
        return "landingpage";
    }
    public String saveCredential(){
        Credential cr=new Credential();
        cr.setUsername("abhinav");
        cr.setPassword("1234");
        credentialRepository.save(cr);
        return "New Credential Saved";

    }
    @PostMapping("/signup")
    public String signup(@RequestParam("username")String username,
                         @RequestParam("password")String password)
    {
        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);

        credentialRepository.save(credential);
        return "dashboard";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password, HttpSession session)
    {
        Optional<Credential> matchedCredential= credentialRepository.findById(username);
        if(matchedCredential.isPresent())
        {
            if(matchedCredential.get().getPassword().equals(password))
            {
                session.setAttribute("username",username);
                return "dashboard";
            }
            else{
                return "landingpage";
            }
        }
        else{
            return "landingpage";
        }
    }




}