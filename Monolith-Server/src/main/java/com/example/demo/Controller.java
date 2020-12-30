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
import java.util.Collections;


import io.github.cdimascio.dotenv.Dotenv;
//import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
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




//PULL OUT JAVAMAIL AND GOOGLE IMAGES API CODE TO OBEY DEPENDENCY INVERSION, RATHER THAN HAVING IT DIRECTLY LINKED TO
//THE GOOGLE IMAGES OR JAVAMAIL API, USE A FUNCTION TO ISOLATE HIGH-LEVEL CODE FROM API CALLS.
@CrossOrigin
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
   

    //Endpoint to get an image from the google images AP.
    @CrossOrigin
    @GetMapping("/api/images")
    public String getImages(@RequestParam String name) 
    {   
        StringBuilder nameReq = new StringBuilder(name);
        nameReq.append(" logo");
        System.out.println(nameReq.toString());
        //PULL THIS INTO ITS OWN FUNCTION
        StringBuilder req = new StringBuilder("https://www.googleapis.com/customsearch/v1?");
        req.append("key=");
        req.append(dotenv.get("API_KEY"));
        req.append("&cx=");
        req.append(dotenv.get("CX_KEY"));
        req.append("&q=");
        req.append(encoder(nameReq.toString()));
        req.append("&searchType=image&alt=json");
        String finalReq = req.toString();

        //ALSO PULL THIS INTO ITS OWN FUNCTION
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(finalReq))
            .build();
        //PUT API CALLS TO IMAGE API INTO ITS OWN FUNCTION/INTERFACE WHATEVER
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
        //PUT THIS INTO ITS OWN FUNCTION AS INIT MAILPROPS, PASS IN STUFF INSTEAD. 
        Properties mailProps = new Properties();

        mailProps.put("mail.imap.host", "imap.gmail.com");
        mailProps.put("mail.imap.port", "993");

        mailProps.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.setProperty("mail.imap.socketFactory.fallback", "false");
        mailProps.setProperty("mail.imap.socketFactory.port", String.valueOf("993"));

        //ABSTRACT THIS OUT INTO A CLASS THAT ALLOWS YOU TO SEARCH AN INBOX WITH ANY API
        Session session = Session.getDefaultInstance(mailProps);
        HashMap<Date, String> res = new HashMap<Date, String>();

        try {
            Store store = session.getStore("imap");
            store.connect(req.get_email(), req.get_pass());
    
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm condition = new SubjectTerm(req.get_keyword());

            Message[] found = inbox.search(condition);
            

            for(int i=0; i<found.length; i++)
            {
                Message message = found[i];
                String body = getText(message);
                res.put(message.getReceivedDate(), body);
            }
            
 
        }

        catch(Exception e) {
            System.out.println("Store error"+e);
            
        }

        return res;

    }
}