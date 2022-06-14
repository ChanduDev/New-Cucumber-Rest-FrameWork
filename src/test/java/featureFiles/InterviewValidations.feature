Feature: Validating Interview API's

  Background: Sample BackGround

  @GET
  Scenario Outline: Verify if API Responds back
    Given Add Headers & Body for API
    When User calls "<EndPoint>" API with "<RequestType>"
    Then Verify 200 response status code
    Then Verify length of the response result is more than 0
    Examples:
      | EndPoint | RequestType |
      | VotesEP  | GET         |

  @GET
  Scenario Outline: Verify if API Responds back for Random Value
    Given Add Headers & Body for API with Random Value
    When User calls "<EndPoint>" API with "<RequestType>" with Random Value
    Then Verify 200 response status code
    And Verify response is not empty
    And Verify all the field in response object match the corresponding given object (by {id})
    Examples:
      | EndPoint      | RequestType |
      | RandomVotesEP | GET         |

  @POST
  Scenario Outline: Verify if User is able to create a new vote
    Given Add Headers & Body for API Payload with "<image_id>","<sub_id>","<value>"
    When User calls "<EndPoint>" API with "<RequestType>"
    Then Verify 200 response status code
    Then Verify body response match expected value
    Then Verify response is not empty
    Examples:
      | EndPoint | RequestType | image_id | sub_id | value |
      | VotesEP  | POST        | 743u     | test   | 1     |

  @GET @CREATED
  Scenario Outline: Use the new {id} created at previous point
    Given Add Headers & Body for API Payload with "<image_id>","<sub_id>","<value>"
    When User calls "<EndPoint>" API with "<RequestType>"
    Then Verify 200 response status code
    Then Verify new id response match existing id request
    Examples:
      | EndPoint | RequestType | image_id | sub_id | value |
      | VotesEP  | POST        | 743u     | test   | 1     |

  @DELETE
  Scenario Outline: Delete {id} at previous point
    Given Add Headers & Body for API Payload with "<image_id>","<sub_id>","<value>"
    When User calls "<EndPoint>" API with "<RequestType>"
    Then Verify 200 response status code
    Then Verify deleted body response match expected value
    Examples:
      | EndPoint | RequestType | image_id | sub_id | value |
      | VotesEP  | POST        | 743u     | test   | 1     |

  @DELETE @Validate
  Scenario Outline: Delete {id} at previous point
    Given Add Headers & Body for API Payload with "<image_id>","<sub_id>","<value>"
    When User calls "<EndPoint>" API with "<RequestType>"
    Then Verify 200 response status code
    Then Verify deleted body response match expected value
    Examples:
      | EndPoint | RequestType | image_id | sub_id | value |
      | VotesEP  | POST        | 743u     | test   | 1     |