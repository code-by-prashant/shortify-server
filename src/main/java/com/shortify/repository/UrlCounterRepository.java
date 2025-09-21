package com.shortify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shortify.entity.UrlCounter;

public interface UrlCounterRepository extends JpaRepository<UrlCounter, Long>{}
