package com;

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

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.models.sponsors;
import com.repository.sponsorsRepo;
import com.models.actions;

import java.util.List;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.Properties;
import java.util.Date;

import org.bson.types.ObjectId;
import io.github.cdimascio.dotenv.Dotenv;
import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
import jdk.tools.jlink.internal.plugins.ExcludePlugin;

import  org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

/*This is a controller class. It controls the view based on models, which are accessed through repositories*/

@RestController //RestController sets this up as a controller for a REST API
public class Controller { 

    private static String encoder(String toEncode) {
        try {
            return URLEncoder.encode(toEncode, StandardCharsets.UTF_8.toString());
        }
        catch(Exception e)
        {
            return "Fail";
        }
    }

    Dotenv dotenv = Dotenv.load();

    @Autowired //Autowired means this is injected
    private sponsorsRepo repository;

    @CrossOrigin
    @GetMapping("/api/sponsors") //Maps GET requests to the /greeting endpoint to the greeting() function
    public List<sponsors> greeting() { 
        return repository.findAll();
    }

    @CrossOrigin
    @PostMapping("/api/add")
    public sponsors addSponsor(@RequestBody sponsors newSponsor)
    {
        newSponsor.set_id(ObjectId.get());
        repository.save(newSponsor);
        return newSponsor;
    }

    @CrossOrigin
    @PutMapping("/api/modify")
    public sponsors modifySponsors(@RequestBody sponsors newSponsor)
    {   if (newSponsor._id == null)
        {
            return newSponsor;
        }
        newSponsor.set_id(newSponsor._id);
        repository.save(newSponsor);
        return newSponsor;
            
    }

    @CrossOrigin
    @PostMapping("/api/addAction")
    public sponsors newAction(@RequestBody actions newAction)
    {
        sponsors sponsor = repository.findBy_id(newAction._id);
        
        List<actions> existingActions = new ArrayList<actions>();
        if (sponsor.get_actions()!=null)
        {
            existingActions = sponsor.get_actions();
        }
        existingActions.add(new actions(new ObjectId(), newAction.actionType, newAction.actionDate, newAction.actionUser, newAction.actionDetails));
        sponsor.set_actions(existingActions);
        repository.save(sponsor);
        return sponsor;
    }

    @CrossOrigin
    @GetMapping("/api/images")
    public String getImages(@RequestParam String name) 
    {
        
        
        StringBuilder req = new StringBuilder("https://www.googleapis.com/customsearch/v1?");
        req.append("key=");
        req.append(dotenv.get("API_KEY"));
        req.append("&cx=");
        req.append(dotenv.get("CX_KEY"));
        req.append("&q=");
        req.append(encoder(name));
        req.append("&searchType=image&alt=json");

        String finalReq = req.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(finalReq))
            .build();

           try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
           }
           catch(Exception e) {
               return "Fail";
           }       
    }

    @CrossOrigin
    @GetMapping("/api/mail")
    public HashMap<Date, String> getMail() {
        System.out.println("searching...");
        Properties mailProps = new Properties();

        mailProps.put("mail.imap.host", "imap.gmail.com");
        mailProps.put("mail.imap.port", "993");

        mailProps.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.setProperty("mail.imap.socketFactory.fallback", "false");
        mailProps.setProperty("mail.imap.socketFactory.port", String.valueOf("993"));

        Session session = Session.getDefaultInstance(mailProps);
        HashMap<Date, String> res = new HashMap<Date, String>();

        try {
            Store store = session.getStore("imap");
            store.connect(dotenv.get("test_email"), dotenv.get("email_pass"));
    
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm condition = new SubjectTerm("TKS");

            Message[] found = inbox.search(condition);
            

            for(int i=0; i<found.length; i++)
            {
                Message message = found[i];
                res.put(message.getReceivedDate(), message.getContent().toString());
                
            }
            
 
        }

        catch(Exception e) {
            System.out.println("Store error"+e);
            
        }

        return res;

    }
}