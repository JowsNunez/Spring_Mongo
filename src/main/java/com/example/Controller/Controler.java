package com.example.Controller;


import com.example.models.User;
import com.example.repositories.IUserDAO;
import com.example.services.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

@RestController()
@RequestMapping("/api/v1/user")
public class Controler {

    @Autowired
    private UserService repository;

    @PostMapping(path="/insert",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createUser(@Validated  @RequestBody User u){
        try{
            User user  = repository.insertOne(u);
         return    ResponseEntity.ok().body(new HashMap<String, Object>(){{
                put("msg","* * * Agregado correctamente * * *");
                put("user",u);
                put("status","Succes");

            }});
        }catch (RuntimeException e){
            e.printStackTrace();
            return   ResponseEntity.status(500).body(new HashMap<String, Object>(){{
                put("msg","* * * Server Error * * *");
                put("Status",500);
            }});
        }

    }

    @GetMapping(path = "/find/",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
   public  ResponseEntity findOne(@RequestHeader("Authenticacion") String header,@RequestParam String name, @RequestParam String nickname){

        try {


        List<User> user = repository.getUserByName(name,nickname);

            System.out.println(user);

        if(user!= null){
            return  ResponseEntity.ok().body(user);
        }else {
            return ResponseEntity.status(404).body(new HashMap<String, Object>(){{
                put("msg","* * * User don't Exists * * *");
                put("Status",404);
            }});
        }

        }  catch (Exception e){
            return ResponseEntity.status(500).body(new HashMap<String, Object>(){{
                put("msg","* * * Server Error * * *");
                put("Status",500);
            }});
        }


    }
    @GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getAllUser(){
        try {
            List<User> users = repository.getAll();
            return ResponseEntity.status(200).body(users);
        }catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new HashMap<String ,Object>(){{
                put("msg","* * * Server Error * * *");
                put("Status",500);
            }});

        }
    }


    @GetMapping(path = "/paginate",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity paginacion(@RequestParam int page){

        try{

        Page pages = repository.UserPaginate(page);
        if(pages.toList().isEmpty()){
         return ResponseEntity.status(404).body(new HashMap<>(){{
                put("msg","* * * Page Not Found * * *");
                put("Status","Error");
            }});
        }
        return ResponseEntity.status(200).body(new HashMap<String, Object>(){{

            put("next_page", pages.nextPageable().toString()!="INSTANCE"? pages.nextPageable().getPageNumber() +1: "");
            put("previous_page", pages.previousPageable().toString()!="INSTANCE"?pages.previousPageable().getPageNumber()+1:"");
            put("total_elements",pages.getTotalElements());
            put("total_pages",pages.getTotalPages());
            put("data", pages.toList());
            put("status","success");

        }});
        } catch (RuntimeException ex){
            return ResponseEntity.status(500).body(new HashMap<String, Object>(){{
                put("msg","* * * Server Error * * *");
                put("status","error");
                put("data",new HashMap<>(){{
                    put("error",ex.toString());
                }});
            }});
        }

    }



}
