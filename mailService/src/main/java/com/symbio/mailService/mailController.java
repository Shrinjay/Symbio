package com.symbio.mailService;

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



import com.symbio.mailService.models.mailReq;

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

@RestController
public class mailController {

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

    @CrossOrigin
    @GetMapping("/wake/")
    public boolean wake() {
        return true;
    }
 
    //Endpoint to get email from user's inbox. 
    @CrossOrigin
    @PostMapping("/")
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
