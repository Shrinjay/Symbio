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


import com.models.sponsors;
import com.repository.sponsorsRepo;
import com.models.actions;
import com.models.mailReq;

import java.util.List;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.Properties;
import java.util.Date;


import io.github.cdimascio.dotenv.Dotenv;
import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
import jdk.tools.jlink.internal.plugins.ExcludePlugin;

import  org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.BodyPart;
import javax.mail.Part;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.io.IOException;

import org.jsoup.Jsoup;





@RestController //RestController sets this up as a controller for a REST API
public class Controller { 

    //Function used to turn MIME protocol messages into plain-text.
    private boolean textIsHtml = false;

    private String getText(Part p) throws
                MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = Jsoup.parse(getText(bp)).text();
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

    //Function used to encode URL strings
    private static String encoder(String toEncode) {
        try {
            return URLEncoder.encode(toEncode, StandardCharsets.UTF_8.toString());
        }
        catch(Exception e)
        {
            return "Fail";
        }
    }
    //Load dotenv to access .env file
    Dotenv dotenv = Dotenv.load();
    //Inject sponsors repository
    @Autowired 
    private sponsorsRepo repository;
    
    //Endpoint to get all sponsors
    @CrossOrigin
    @GetMapping("/api/sponsors") //Maps GET requests to the /greeting endpoint to the greeting() function
    public List<sponsors> greeting() { 
        return repository.findAll();
    }

    //Endpoint to add a new sponsor.
    @CrossOrigin
    @PostMapping("/api/add")
    public sponsors addSponsor(@RequestBody sponsors newSponsor)
    {
       
        repository.save(newSponsor);
        return newSponsor;
    }

    //Endpoint to modify a sponsor.
    @CrossOrigin
    @PutMapping("/api/modify")
    public sponsors modifySponsors(@RequestBody sponsors newSponsor)
    {   
        newSponsor.set_id(newSponsor._id);
        repository.save(newSponsor);
        return newSponsor;
            
    }

    //Endpoint to add an action to a sponsor
    @CrossOrigin
    @PostMapping("/api/addAction")
    public sponsors newAction(@RequestBody actions newAction)
    {
        sponsors sponsor = repository.findBy_id(newAction._id);
        
        List<actions> existingActions = new ArrayList<actions>();
        if (sponsor.get_actions()!=null) existingActions = sponsor.get_actions();
        existingActions.add(new actions(new AtomicLong().longValue(), newAction.actiontype, newAction.actiondate, newAction.actionuser, newAction.actiondetails));
        sponsor.set_actions(existingActions);
        repository.saveAndFlush(sponsor);
        return sponsor;
    }

    //Endpoint to get an image from the google images AP.
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

    
    //Endpoint to get email from user's inbox. 
    @CrossOrigin
    @PostMapping("/api/mail")
    public HashMap<Date, String> getMail(@RequestBody mailReq req) {

        
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
            store.connect(req.email, req.pass);
    
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm condition = new SubjectTerm(req.keyword);

            Message[] found = inbox.search(condition);
            

            for(int i=0; i<found.length; i++)
            {
                Message message = found[i];
                String body = getText(message).substring(0, 300);

                res.put(message.getReceivedDate(), body);
                
            }
            
 
        }

        catch(Exception e) {
            System.out.println("Store error"+e);
            
        }

        return res;

    }
}