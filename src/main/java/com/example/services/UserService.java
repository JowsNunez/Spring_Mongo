package com.example.services;

import com.example.models.User;
import com.example.repositories.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserDAO repository;


    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * This method insert a user in to DataBase on Mongo Local or Mongo Atlas
     * @param u is a User
     * @return return user with your specified attributes
     */
    public User insertOne(User u){
        return repository.insert(u);
    }

    /**
     * This method get a List of all User in Data Base
     * @return return a List type of User
     */
    public List<User> getAll(){
        return repository.findAll();
    }

    /**
     * This method get a User by first name and by nickname, pd: is essential write the two attributes
     * @param name represents name of user
     * @param nickname represents nickname of user
     * @return return a List type of User with one User according to specified attributes
     */
    public List<User> getUserByName(String name, String nickname){

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("first_name").is(name)).
                    addCriteria(Criteria.where("nick_name").is(nickname));
            return mongoTemplate.find(query,User.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    /**
     * This method get a List of User using pagination for performance improve
     * @param page represents current page
     * @return return a Page type of User with List
     */
    public Page<User> UserPaginate(int page){
        Pageable pageable = PageRequest.of(page-1,3);
        return repository.findAll(pageable);
    }

    //TODO create Methods Edit and Delete
}
