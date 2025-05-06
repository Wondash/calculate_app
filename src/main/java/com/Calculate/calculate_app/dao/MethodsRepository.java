package com.Calculate.calculate_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodsRepository extends JpaRepository<Methods, Integer> {
    Methods findByName(String name);
}
