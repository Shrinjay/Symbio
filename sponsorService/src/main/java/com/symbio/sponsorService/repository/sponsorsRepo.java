package com.symbio.sponsorService.repository;

import com.symbio.sponsorService.models.sponsors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Spring Data repository to access and modify items in the sponsors table
@Repository
public interface sponsorsRepo extends JpaRepository<sponsors, Long>{
    sponsors findBy_id(long _id);
    sponsors findOneBysponsorname(String sponsorname);
    List<sponsors> findBystatus(String status);
}