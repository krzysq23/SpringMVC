Feature: Testing a REST API

  Scenario: Testing valid GET endpoint
    Given url 'http://192.168.56.101:8080/api/hello'
    When method GET
    Then status 200
    And match $ == 'Hello World!'

  Scenario: Testing a POST endpoint with request body
    Given url 'http://192.168.56.101:8080/api/addBook'
    And request { "title": "Test", "author": "TestAuthor", "isbn": "ISBNTEST"}
    When method POST
    Then status 200

  Scenario: Testing that GET AND DELETE response contains specific field
    Given url 'http://192.168.56.101:8080/api/findByIsbn-ISBNTEST'
    When method GET
    Then status 200
    And match $ contains { "title": "Test", "author": "TestAuthor"}
    And def book = response

    Given url 'http://192.168.56.101:8080/api/removeBook/' + book.id
    When method DELETE
    Then status 200
