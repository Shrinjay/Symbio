package com.repository;

import com.models.sponsors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface sponsorsRepo extends MongoRepository<sponsors, String>{
    sponsors findBy_id(ObjectId _id);
    sponsors findBySponsorName(String sponsorName);
}