package com.symbio.sponsorService;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.symbio.sponsorService.models.sponsors;
import com.symbio.sponsorService.repository.sponsorsRepo;
import com.symbio.sponsorService.models.actions;
import com.symbio.sponsorService.models.mailReq;


import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import java.util.ArrayList;

import  org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@CrossOrigin
@RestController
@SpringBootApplication
public class sponsorController {

 //Load dotenv to access .env file
 Dotenv dotenv = Dotenv.load();
 //Inject sponsors repository
 @Autowired 
 private sponsorsRepo repository;
 
 //Endpoint to get all sponsors
 @CrossOrigin
 @GetMapping("/")//Maps GET requests to the /greeting endpoint to the greeting() function
 public List<sponsors> greeting(@RequestParam(name="_status", required=false) String[] _status) { 
     
     if (_status==null) return repository.findAll();
     else {
         List<sponsors> res = new ArrayList<sponsors>();
         for (String i: _status)
         {
             res.addAll(repository.findBystatus(i));
         }
         return res;
     }
 }

 @CrossOrigin
 @GetMapping("/numsponsors/")
 public Integer numSponsors(@RequestParam String _status) {
     return repository.findBystatus(_status).size();
 }

 //Endpoint to add a new sponsor.
 @CrossOrigin
 @PostMapping("/add/")
 public sponsors addSponsor(@RequestBody sponsors newSponsor)
 {
    
     repository.save(newSponsor);
     return newSponsor;
 }

 //Endpoint to modify a sponsor.
 @CrossOrigin
 @PutMapping("/modify/")
 public sponsors modifySponsors(@RequestBody sponsors newSponsor)
 {   
     newSponsor.set_id(newSponsor.get_id());
     repository.save(newSponsor);
     return newSponsor;
         
 }

 //Endpoint to add an action to a sponsor
 @CrossOrigin
 @PostMapping("/addAction/")
 public sponsors newAction(@RequestBody actions newAction)
 {
     sponsors sponsor = repository.findBy_id(newAction.get_id());
     
     List<actions> existingActions = new ArrayList<actions>();
     if (sponsor.get_actions()!=null) existingActions = sponsor.get_actions();
     existingActions.add(new actions(new AtomicLong().longValue(), newAction.get_actiontype(), newAction.get_actiondate(), newAction.get_actionuser(), newAction.get_actiondetails()));
     sponsor.set_actions(existingActions);
     repository.saveAndFlush(sponsor);
     return sponsor;
 }
}