package com.example.demo.DAO;

import com.example.demo.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentDAO extends JpaRepository<Document,Integer> {

}
