package com.shortify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for generating unique counters for Base62 URL encoding.
 */
@Entity
@Table(name = "url_counter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private char dummy; // placeholder column
}
