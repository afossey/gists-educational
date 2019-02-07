package com.afossey.projects.educational.gists;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(
    value = "/gists",
    produces = APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class GistController {

  private static final String DESERIALIZATION_FAILED = "Request body deserialization failed.";
  private final GistService gistService;
  private final GistSchema gistSchema;

  @GetMapping
  public List<GistValue> getGists() {
    return gistService.findGists();
  }

  @GetMapping("/{id}")
  public Optional<GistValue> getGist(@PathVariable String id) {
    return gistService.findGistById(id);
  }

  @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
  public GistValue createGist(@RequestBody String rawGist) {
    this.gistSchema.validate(rawGist);
    GistValue gist = SerializationUtils
        .deserialize(rawGist, GistValue.class)
        .orElseThrow(
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, DESERIALIZATION_FAILED)
        );
    return gistService.createGist(gist);
  }

}
