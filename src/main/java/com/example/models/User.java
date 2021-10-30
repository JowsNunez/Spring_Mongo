package com.example.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Document(collection = "Users")

public class User {
    @Id
    private @Getter @Setter String _id;

    private @Getter @Setter
    String first_name;

    private @Getter @Setter String last_name;

    private @Getter @Setter String nick_name;


}
