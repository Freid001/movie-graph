package com.movies;


import lombok.Builder;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

class Helper {
    private static final String HOST = System.getenv("cucumber.server");

    /**
     * Configures the REST assured static properties.
     */
    public static void configureRestAssured() {
        baseURI = HOST;
    }

    public static void resetNeo4j() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687");

        Neo4jClient.create(driver).query("MATCH (n) DETACH DELETE n").run();
    }


    /**
     * Replaces placeholder values in string with actual values.
     */
    public static String replacePlaceholders(HashMap<String, Object> attributes, String string)
    {
        for (Map.Entry attr : attributes.entrySet()) {
            string = string.replaceAll(attr.getKey().toString(), attr.getValue().toString());
        }

        return string;
    }
}