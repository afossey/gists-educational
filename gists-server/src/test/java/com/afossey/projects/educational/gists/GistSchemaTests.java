package com.afossey.projects.educational.gists;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.everit.json.schema.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GistSchemaTests {

  private GistSchema gistSchema;

  public GistSchemaTests() throws IOException {
    this.gistSchema = new GistSchema();
  }

  @Test(expected = ValidationException.class)
  public void should_throw_validation_exception() {
    String validGist = "{\n"
        + "  \"id\": \"1\"\n"
        + "}";
    gistSchema.validate(validGist);
  }

  @Test
  public void should_validate_against_schema_ko() {
    String validGist = "{\n"
        + "  \"id\": \"1\",\n"
        + "  \"name\": 1\n"
        + "}";
    try {
      gistSchema.validate(validGist);
    } catch (ValidationException e) {
      assertThat(e.getSchemaLocation()).isEqualTo("#/properties/name");
    }
  }

  @Test
  public void should_validate_against_schema_ok() {
    String validGist = "{\n"
        + "  \"id\": \"1\",\n"
        + "  \"name\": \"Foo\"\n"
        + "}";
    gistSchema.validate(validGist);
  }


}
