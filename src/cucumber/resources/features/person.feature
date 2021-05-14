Feature: Person

Scenario Outline: can create persons
  Given the request body is:
  """
  {
	"firstName": "<firstName>",
	"lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then the response code is 201
  Then the response body "firstName" = "<firstName>"
  Then the response body "lastName" = "<lastName>"
  Examples:
    | firstName   | lastName    |
    | Harrison    | Ford        |

Scenario Outline: can create and retrieve person
  Given the request body is:
  """
  {
	"firstName": "<firstName>",
	"lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  When I request "/api/v1/person/:person-placeholderId" using HTTP GET
  Then the response code is 200
  Then the response body "firstName" = "<firstName>"
  Then the response body "lastName" = "<lastName>"
  Examples:
    | firstName   | lastName    |
    | Meryl       | Streep      |

#Scenario Outline: can retrieve persons
#  Given the request body is:
#  """
#  {
#	"firstName": "<firstName>",
#	"lastName": "<lastName>"
#  }
#  """
#  When I request "/api/v1/person" using HTTP POST
#  Given I request "/api/v1/persons" using HTTP GET
#  Then the response code is 200
#  Then the response body "[0].firstName" = "<firstName>"
#  Then the response body "[0].lastName" = "<lastName>"
#  Examples:
#    | firstName   | lastName |
#    | Jennifer    | Lawrence |

Scenario Outline: can create and delete director
  Given the request body is:
  """
  {
	"title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  Given the request body is:
  """
  {
	"firstName": "<firstName>",
	"lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  When I request "/api/v1/person/:person-placeholderId/directed/:movie-placeholderId" using HTTP POST
  Then the response code is 200
  Then the response body "directed[0].movie.title" = "<title>"
  When I request "/api/v1/person/:person-placeholderId/directed/:movie-placeholderId" using HTTP DELETE
  Then the response code is 200
  Examples:
    | firstName | lastName  | title                      |
    | Steven    | Spielberg | E.T. the Extra-Terrestrial |

Scenario Outline: can create and delete writer
  Given the request body is:
  """
  {
    "title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  Given the request body is:
  """
  {
    "firstName": "<firstName>",
    "lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  When I request "/api/v1/person/:person-placeholderId/wrote/:movie-placeholderId" using HTTP POST
  Then the response code is 200
  Then the response body "wrote[0].movie.title" = "<title>"
  When I request "/api/v1/person/:person-placeholderId/wrote/:movie-placeholderId" using HTTP DELETE
  Then the response code is 200
  Examples:
    | firstName | lastName  | title            |
    | Dean      | Devlin    | Independence Day |

Scenario Outline: can create and delete producer
  Given the request body is:
  """
  {
    "title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  Given the request body is:
  """
  {
    "firstName": "<firstName>",
    "lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  When I request "/api/v1/person/:person-placeholderId/produced/:movie-placeholderId" using HTTP POST
  Then the response code is 200
  Then the response body "produced[0].movie.title" = "<title>"
  When I request "/api/v1/person/:person-placeholderId/produced/:movie-placeholderId" using HTTP DELETE
  Then the response code is 200
  Examples:
    | firstName | lastName  | title                    |
    | Mace      | Neufeld   | The Hunt for Red October |

Scenario Outline: can create and delete actor
  Given the request body is:
  """
  {
    "title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  Given the request body is:
  """
  {
    "firstName": "<firstName>",
    "lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  Given the request body is:
  """
  {
    "character": "<character>"
  }
  """
  When I request "/api/v1/person/:person-placeholderId/actedIn/:movie-placeholderId" using HTTP POST
  Then the response code is 200
  Then the response body "actedIn[0].movie.title" = "<title>"
  When I request "/api/v1/person/:person-placeholderId/actedIn/:movie-placeholderId" using HTTP DELETE
  Then the response code is 200
  Examples:
    | firstName | lastName  | title    | character |
    | Tom       | Cruise    | Top Gun  | Maverick  |

Scenario Outline: can create and delete critic
  Given the request body is:
  """
  {
    "title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  Given the request body is:
  """
  {
    "firstName": "<firstName>",
    "lastName": "<lastName>"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":person-placeholderId"
  Given the request body is:
  """
  {
    "rating": <rating>
  }
  """
  When I request "/api/v1/person/:person-placeholderId/reviewed/:movie-placeholderId" using HTTP POST
  Then the response code is 200
  Then the response body "reviewed[0].movie.title" = "<title>"
  When I request "/api/v1/person/:person-placeholderId/reviewed/:movie-placeholderId" using HTTP DELETE
  Then the response code is 200
  Examples:
    | firstName | lastName  | title | rating |
    | Roger     | Ebert     | Jaws  | 4      |