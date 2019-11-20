Feature: Testing a REST API

  Background:
    * url 'http://192.168.56.101:8082/api/'

  Scenario Outline: Testing valid GET endpoint
    Given path 'hello'
    When method GET
    Then status 200
    And match $ == <name>

    Examples:
      | name |
      |'Hello World!'|

  Scenario Outline: Testing that GET response contains specific field
    Given url 'http://192.168.56.101:8082/api/findByIsbn-' + <id>
    When method GET
    Then status 200
    And match $ contains { "title": <title> }

    Examples:
      | id              | title               |
      | 'ISBN-73423423' | 'Ogniem i Mieczem'  |
      | 'ISBN98734222'  | 'Lalka'             |

