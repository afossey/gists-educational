package com.afossey.projects.educational.gists;



import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GistIntegrationTests {

  @LocalServerPort
  private Integer localPort;

  @Before
  public void init() {
    RestAssured.port = localPort;
  }

  @Test
  public void should_respond_json_schema_validation_error() {
      given()
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body("{\"name\":1}")
      .when()
        .post("/gists")
      .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body(Matchers.is("[\"#/name: expected type: String, found: Integer\"]"));
  }
}
