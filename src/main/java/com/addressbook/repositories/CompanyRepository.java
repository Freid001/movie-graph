package com.addressbook.repositories;

import com.addressbook.model.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CompanyRepository extends Neo4jRepository<Company, Long> {
    Company findByName(String name);
}