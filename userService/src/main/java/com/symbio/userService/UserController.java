package com.symbio.userService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import io.github.cdimascio.dotenv.DotenvException;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import io.github.cdimascio.dotenv.Dotenv;
import com.auth0.jwt.algorithms.Algorithm;


import com.symbio.userService.repository.UserRepo;
import com.symbio.userService.models.User;
import com.symbio.userService.models.LoginRequest;

@CrossOrigin
@RestController
@SpringBootApplication
public class UserController {


 //Inject sponsors repository
 @Autowired
 private UserRepo userRepo;

 private String jwt_secret;
 private String jwt_issuer;

 UserController() {
  try {
   Dotenv dotenv = Dotenv.load();
   jwt_secret = dotenv.get("jwt_secret");
   jwt_issuer = dotenv.get("jwt_issuer");
  }
  catch (DotenvException e) {
   jwt_secret = System.getenv("jwt_secret");
   jwt_issuer = System.getenv("jwt_issuer");
  }
 }

 @CrossOrigin
 @GetMapping("/wake/")
 public boolean wake() {
  return true;
 }

 //Endpoint to get all sponsors
 @CrossOrigin
 @PostMapping("/login")//Maps GET requests to the /greeting endpoint to the greeting() function
 public String login(@RequestBody LoginRequest body) {
  User user = userRepo.findByUsername(body.username);
  if (user == null) {
   return "User does not exist";
  }

  boolean valid = BCrypt.checkpw(body.password, user.getPassword());
  if (valid) {
   Algorithm algorithm = Algorithm.HMAC512(jwt_secret);
   Map<String, String> payload = new HashMap<String, String>();
   payload.put("user", user.getUsername());
   payload.put("role", user.getRole());
   return JWT.create()
           .withPayload(payload)
           .withIssuer(jwt_issuer)
           .sign(algorithm);
  } else {
   return "Invalid Password";
  }

 }

 @CrossOrigin
 @PostMapping("/user")
 public boolean createUser(@RequestBody User body) {
  String hashed_pw = BCrypt.hashpw(body.getPassword(), BCrypt.gensalt());
  body.setPassword(hashed_pw);

  userRepo.save(body);
  return true;
 }
}