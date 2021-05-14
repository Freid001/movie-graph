# Query

```bash
CREATE (n:Person {personId:"",firstName:"",lastName:""}) return n
CREATE (n:Person {personId:"",firstName:"",lastName:""}) return n
CREATE (n:Person {personId:"",firstName:"",lastName:""}) return n
CREATE (n:Movie {movieId:"",title:""}) return n
CREATE (n:Movie {movieId:"",title:""}) return n
CREATE (n:Movie {movieId:"",title:""}) return n








// Create nodes
CREATE (n:Person {firstName:"John",lastName:"Smith",age:34,address:"",email:"johnsmith@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Sarah",lastName:"Smith",age:22,address:"",email:"sarahsmith@hotmail.com",phoneNumber:""}) return n

CREATE (n:Person {firstName:"Benjamin",lastName:"Taylor",age:42,address:"",email:"benjamintaylor@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Emma",lastName:"Taylor",age:39,address:"",email:"emmataylor@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Sophie",lastName:"Taylor",age:19,address:"",email:"sophietaylor@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Thomas",lastName:"Taylor",age:19,address:"",email:"thomastaylor@hotmail.com",phoneNumber:""}) return n

CREATE (n:Person {firstName:"Michael",lastName:"Williams",age:43,address:"",email:"michaelwilliams@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Liam",lastName:"Johnson",age:26,address:"",email:"liamjohnson@hotmail.com",phoneNumber:""}) return n
CREATE (n:Person {firstName:"Toby",lastName:"Jones",age:34,address:"",email:"tobyjones@hotmail.com",phoneNumber:""}) return n

CREATE (n:Company {name:"Amazon", address:"", email:"", phoneNumber:""}) return n
CREATE (n:Company {name:"Unilever", address:"", email:"", phoneNumber:""}) return n
CREATE (n:Company {name:"Walmart", address:"", email:"", phoneNumber:""}) return n

// List nodes
Match (n) return n
MATCH (n:Person {lastName:"Taylor"}) return n
MATCH (n:Person {firstName:"Sophie",lastName:"Taylor"}) return n

// Modify node properties
MATCH (n:Person {firstName:"Sophie",lastName:"Taylor"}) SET n.age=21
MATCH (n:Person {firstName:"Robert",lastName:"Taylor"}) REMOVE n.age

// Create Relationship
MATCH (a:Person {firstName:"John",lastName:"Smith"}),(b:Person {firstName:"Sarah",lastName:"Smith"}) MERGE (a)-[r:spouse {since:"2007"}]->(b)
MATCH (a:Person {firstName:"Benjamin",lastName:"Taylor"}),(b:Person {firstName:"Emma",lastName:"Taylor"}) MERGE (a)<-[r:spouse {since:"1993"}]->(b)

MATCH (a:Person {firstName:"Benjamin",lastName:"Taylor"}),(b:Person {firstName:"Robert",lastName:"Taylor"}) MERGE (a)-[r:sibling]->(b)
MATCH (a:Person {firstName:"Sophie",lastName:"Taylor"}),(b:Person {firstName:"Thomas",lastName:"Taylor"}) MERGE (a)-[r:sibling]->(b)

MATCH (a:Person {firstName:"Benjamin",lastName:"Taylor"}),(b:Person {firstName:"Thomas",lastName:"Taylor"}) MERGE (a)-[r:child]->(b)
MATCH (a:Person {firstName:"Benjamin",lastName:"Taylor"}),(b:Person {firstName:"Sophie",lastName:"Taylor"}) MERGE (a)-[r:child]->(b)
MATCH (a:Person {firstName:"Emma",lastName:"Taylor"}),(b:Person {firstName:"Thomas",lastName:"Taylor"}) MERGE (a)-[r:child]->(b)
MATCH (a:Person {firstName:"Emma",lastName:"Taylor"}),(b:Person {firstName:"Sophie",lastName:"Taylor"}) MERGE (a)-[r:child]->(b)

MATCH (a:Company {name:"Unilever"}),(b:Person {firstName:"Toby",lastName:"Jones"}) MERGE (a)-[r:employee {since:"2007"}]->(b)
MATCH (a:Company {name:"Unilever"}),(b:Person {firstName:"Michael",lastName:"Williams"}) MERGE (a)-[r:employee {since:"1992"}]->(b)
MATCH (a:Company {name:"Unilever"}),(b:Person {firstName:"Emma",lastName:"Taylor"}) MERGE (a)-[r:employee {since:"1999"}]->(b)
MATCH (a:Company {name:"Unilever"}),(b:Person {firstName:"John",lastName:"Smith"}) MERGE (a)-[r:employee {since:"2003"}]->(b)

MATCH (a:Company {name:"Walmart"}),(b:Person {firstName:"Sophie",lastName:"Taylor"}) MERGE (a)-[r:employee {since:"2019"}]->(b)
MATCH (a:Company {name:"Walmart"}),(b:Person {firstName:"Liam",lastName:"Johnson"}) MERGE (a)-[r:employee {since:"2017"}]->(b)

MATCH (a:Company {name:"Amazon"}),(b:Person {firstName:"Benjamin",lastName:"Taylor"}) MERGE (a)-[r:employee {since:"1993"}]->(b)

MATCH (a:Person {firstName:"Sophie",lastName:"Taylor"}),(b:Person {firstName:"Liam",lastName:"Johnson"}) MERGE (a)-[r:friend]->(b)
MATCH (a:Person {firstName:"Sarah",lastName:"Smith"}),(b:Person {firstName:"Emma",lastName:"Taylor"}) MERGE (a)-[r:friend]->(b)

// Remove Relationship
MATCH (a:Person {firstName:"Liam",lastName:"Johnson"})-[r:friend]-() DELETE r
MATCH (a:Person {firstName:"Liam",lastName:"Johnson"})-[r:employee]-() DELETE r

// Remove node
MATCH (a:Person {firstName:"Liam",lastName:"Johnson"}) DELETE a

// Query 
MATCH (n:Person) WHERE n.lastName='Taylor' RETURN n
MATCH (n:Person) WHERE n.lastName='Taylor' RETURN n.firstName, n.lastName

MATCH (n:Person {lastName:"Taylor"}) RETURN n
MATCH (n:Person {lastName:"Taylor"}) RETURN n.firstName, n.lastName

MATCH (a:Person {firstName:"Emma"})-[:child]->(b:Person) RETURN a,b
MATCH (a:Person {firstName:"Emma"})-[:child]->(b:Person) RETURN b

MATCH (a:Person {firstName:"Benjamin"})-[:spouse]->(b:Person) RETURN a,b
MATCH (a:Person {firstName:"Emma"})-[:spouse]-(b:Person) RETURN a,b
MATCH (a:Person) <-[:spouse]-(b:Person {firstName:"Benjamin"}) RETURN a,b
MATCH (a:Person) -[:spouse]- (b:Person) return a.firstName

MATCH (a:Person {firstName: 'Benjamin', lastName:'Taylor'})-[r]-(b:Person) RETURN b.firstName, b.lastName, type(r) as relationship
MATCH (a:Person {firstName: 'Benjamin', lastName:'Taylor'})-[r]-(b:Person) WHERE type(r) = 'child' RETURN b.firstName, b.lastName, type(r) as relationship

// Aggregation
MATCH (a:Company) -[:employee]-> (b:Person) return a.name,COUNT(b) order by COUNT(b) DESC

```