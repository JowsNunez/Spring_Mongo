package com.example.repositories;

import com.example.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDAO extends MongoRepository<User,String> {
}
