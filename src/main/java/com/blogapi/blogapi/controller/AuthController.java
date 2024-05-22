package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final Jaxb2Marshaller marshaller;

    @Autowired
    public AuthController(AuthenticationService authenticationService, Jaxb2Marshaller marshaller) {
        this.authenticationService = authenticationService;
        this.marshaller = marshaller;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> login(@RequestBody String xml) {
        try {
            User user = (User) marshaller.unmarshal(new StreamSource(new StringReader(xml)));

            String authToken = authenticationService.authenticate(user.getUsername(), user.getPassword());
            Map<String, Object> response = new HashMap<>();
            if (authToken != null) {
                response.put("token", authToken);
                return ResponseEntity.ok(marshal(response));
            } else {
                response.put("message", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(marshal(response));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("<message>Internal Server Error</message>");
        }
    }

    @GetMapping(value = "/isUserLoggedIn", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> isUserLoggedIn(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        if (authToken != null) {
            if (authenticationService.isValidToken(authToken)) {
                response.put("message", "User is logged in");
                return ResponseEntity.ok(marshal(response));
            }
        }
        response.put("message", "User is not logged in");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(marshal(response));
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        if (authToken != null && authenticationService.invalidateToken(authToken)) {
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(marshal(response));
        } else {
            response.put("message", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(marshal(response));
        }
    }

    private String marshal(Object obj) {
        try {
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "<message>Error processing XML</message>";
        }
    }
}
