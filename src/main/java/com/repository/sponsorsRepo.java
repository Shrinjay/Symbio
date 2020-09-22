package com.repository;

import com.models.sponsors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sponsorsRepo extends JpaRepository<sponsors, Long>{
    sponsors findBy_id(long _id);
    sponsors findOneBysponsorname(String sponsorname);
}