package com.brotoryapp.brotory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "History")
public class History {

    @Id
    private String id;
    private String username;
    private String url;
    private String date;

}
