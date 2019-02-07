package com.afossey.projects.educational.gists;

import java.io.IOException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class GistSchema {

  private static final String GIST_SCHEMA_PATH = "schemas/gist.schema.json";
  private Schema gistSchema;

  public GistSchema() throws IOException {
    Resource schema = new ClassPathResource(GIST_SCHEMA_PATH);
    JSONObject rawSchema = new JSONObject(new JSONTokener(schema.getInputStream()));
    this.gistSchema = SchemaLoader.load(rawSchema);
  }

  void validate(String rawJsonGist) {
    this.gistSchema.validate(new JSONObject(rawJsonGist));
  }
}
