package com.afossey.projects.educational.gists;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
class GistValue {
  String id;
  String name;

  // For Jackson to deserialize into an immutable gist
  @JsonCreator
  GistValue(@JsonProperty("id") String _id, @JsonProperty("name") String _name) {
    this.id = _id;
    this.name = _name;
  }

  GistEntity toEntity() {
    return new GistEntity(this.id, this.name);
  }
}
