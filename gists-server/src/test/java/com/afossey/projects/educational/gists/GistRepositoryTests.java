package com.afossey.projects.educational.gists;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class GistRepositoryTests {

  @Autowired
  private GistRepository gistRepository;

  @Test
  public void should_retrieve_gists() {
    GistEntity aGist = GistEntity.builder().name("Foo").build();
    gistRepository.save(aGist);

    List<GistEntity> gists = gistRepository.findAll();
    // Useless assertions to play with AssertJ
    assertThat(gists).isInstanceOf(List.class);
    assertThat(gists).isNotEmpty();
    assertThat(gists).isEqualTo(Collections.singletonList(aGist));
    assertThat(gists).filteredOn(g -> aGist.getName().equals(g.getName()))
        .first()
        .as(aGist.getName() + "'s GistEntity")
        .hasFieldOrProperty("id");

    gistRepository.deleteAll();
  }

  @Test
  public void should_create_gist() {
    GistEntity aGist = GistEntity.builder().name("FooTwo").build();
    GistEntity created = gistRepository.save(aGist);

    assertThat(created).isNotNull();
    assertThat(created.getName()).isEqualTo("FooTwo");
  }
}
