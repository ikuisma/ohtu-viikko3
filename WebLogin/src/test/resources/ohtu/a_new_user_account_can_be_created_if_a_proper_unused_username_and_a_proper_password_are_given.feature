Feature: A new user account can be created if a proper unused username and password are given

  Scenario: creation is successful with valid username and password
    Given command new user is selected
    When  a valid username "liisa" and password "salainen1" and matching password confirmation are entered
    Then  a new user is created

  Scenario: creation fails with too short username and valid password
    Given command new user is selected
    When  a short username "a" and a valid password "salainen1" are entered
    Then user is not created and error "username should have at least 3 characters" is reported

  Scenario: creation fails with correct username and too short password
    Given command new user is selected
    When  a valid username "paavo" and a password "a" that is too short are entered
    Then user is not created and error "password should have at least 8 characters" is reported

  Scenario: creation fails with already taken username and valid password
    Given command new user is selected
    When  an already existing username "jukka" and a valid password "Newpassword1" are entered
    Then user is not created and error "username is already taken" is reported

  Scenario: creation fails when password and password confirmation do not match
    Given command new user is selected
    When  a valid username "newuser" and password "Validpass1" but a nonmatching confirmation "Validpass" are entered
    Then user is not created and error "password and password confirmation do not match" is reported

  Scenario: user can login with successfully generated account
    Given user with username "lea" with password "salainen1" is successfully created
    And   login is selected
    When  created user with username "lea" and password "salainen1" logs in
    Then  user is logged in

  Scenario: user can not login with account that is not successfully created
    Given user with username "aa" and password "bad" is tried to be created
    And   login is selected
    When  username "aa" that was not created tries to login with password "bad"
    Then  user is not logged in and error message is given
