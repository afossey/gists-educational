package com.afossey.projects.educational.gists;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(GistController.class)
public class GistControllerTests {

  @Autowired
  private GistController gistController;

  @MockBean
  private GistService gistService;

  @MockBean
  private GistSchema gistSchema;

  @Before
  public void init() {
    RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(gistController));
  }

  @Test
  public void should_get_gists() {
    when(gistService.findGists()).thenReturn(
        Collections.singletonList(new GistValue("1", "Foo")));

    when()
      .get("/gists")
    .then()
      .statusCode(HttpStatus.OK.value())
      .contentType(ContentType.JSON)
      .body("name", hasItems("Foo"));
  }
}
