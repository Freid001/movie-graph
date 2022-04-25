# Movies

## About
* A small api which uses neo4j to store movies, directors, producers, writers, actors & critics. 
* The purpose of this project was to gain some experience using [neo4j](https://neo4j.com/).

### Requirements
* [Docker](https://www.docker.com/) 

## Usage 

### App

```bash
// Start app with docker-compose
./gradlew start

// Spot bugs
./gradlew check

// Run component tests
./gradlew cucumber

// Stop app
./gradlew stop
```

### Neo4j

## Documentation 
* http://localhost:9000/swagger-ui.html
* https://docs.spring.io/spring-data/neo4j/docs/6.0.6/reference/html/#reference

## Todo
- [x] Api 
- [x] Neo4j
- [x] SDN (Spring data Neo4j)
    - [x] Create
    - [x] Delete
- [x] Prometheus
- [ ] Cucumber
- [x] Spotbugs
- [x] Unit tests
- [x] Code coverage
- [ ] Advance Neo4j search
