package com.afossey.projects.educational.gists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class GistEntity {
  @Id
  private String id;
  private String name;

  GistValue toValue() {
    return new GistValue(this.id, this.name);
  }
}