package com.symbio.imageService;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;



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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
public class imageController {

    @Value("${search.api_key}")
    String API_KEY;

    @Value("${search.cx_key}")
    String CX_KEY;

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

    //Endpoint to get an image from the google images AP.
@CrossOrigin
@GetMapping("/")
public String getImages(@RequestParam String name) 
{   
    StringBuilder nameReq = new StringBuilder(name);
    nameReq.append(" logo");
    System.out.println(nameReq.toString());
    //PULL THIS INTO ITS OWN FUNCTION
    StringBuilder req = new StringBuilder("https://www.googleapis.com/customsearch/v1?");
    req.append("key=");
    req.append(API_KEY);
    req.append("&cx=");
    req.append(CX_KEY);
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
}
