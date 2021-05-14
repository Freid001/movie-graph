Feature: Movies

Scenario Outline: can create movie
  Given the request body is:
  """
  {
	"title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then the response code is 201
  Then the response body "title" = "<title>"
  Examples:
    | title                                 |
    | Star Wars: A New Hope                 |
    | Star Wars: The Empire Strikes Back    |
    | Star Wars: Return of the Jedi         |

Scenario Outline: can create and retrieve movie
  Given the request body is:
  """
  {
	"title": "<title>"
  }
  """
  When I request "/api/v1/movie" using HTTP POST
  Then extract "movieId" as placeholder ":movie-placeholderId"
  When I request "/api/v1/movie/:movie-placeholderId" using HTTP GET
  Then the response code is 200
  Then the response body "title" = "<title>"
  Examples:
    | title                                 |
    | Indiana Jones raiders of the Lost Ark |
    | Indiana Jones and the Temple of Doom  |
    | Indiana Jones and the Last Crusade    |

Scenario Outline: can retrieve movie ratings
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
    "firstName": "critic",
    "lastName": "A"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":critic-a"
  Given the request body is:
  """
  {
    "rating": <criticA>
  }
  """
  When I request "/api/v1/person/:critic-a/reviewed/:movie-placeholderId" using HTTP POST
  Given the request body is:
  """
  {
    "firstName": "critic",
    "lastName": "B"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":critic-b"
  Given the request body is:
  """
  {
    "rating": <criticB>
  }
  """
  When I request "/api/v1/person/:critic-b/reviewed/:movie-placeholderId" using HTTP POST
  Given the request body is:
  """
  {
    "firstName": "critic",
    "lastName": "C"
  }
  """
  When I request "/api/v1/person" using HTTP POST
  Then extract "personId" as placeholder ":critic-c"
  Given the request body is:
  """
  {
    "rating": <criticC>
  }
  """
  When I request "/api/v1/person/:critic-c/reviewed/:movie-placeholderId" using HTTP POST
  When I request "/api/v1/movie/:movie-placeholderId" using HTTP GET
  Then the response code is 200
  Then the response body "rating" = <rating> as float
  Then the response body "reviews" = <reviews>
  When I request "/api/v1/movie/:movie-placeholderId" using HTTP GET
  Then the response code is 200
  Then the response body "title" = "<title>"
  Examples:
    | criticA | criticB | criticC | title       | rating            | reviews |
    | 4       | 7       | 6       | Forest Gump | 5.666666666666667 | 3       |
