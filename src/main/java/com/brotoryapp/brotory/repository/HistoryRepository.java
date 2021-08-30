package com.brotoryapp.brotory.repository;

import com.brotoryapp.brotory.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HistoryRepository extends MongoRepository<History, String> {

    @Query("{'username' : ?0}")
    List<History> findUsername(String username);

}
