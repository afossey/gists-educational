package com.afossey.projects.educational.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GistServiceTests {

  private static final GistEntity gist = new GistEntity("1", "Foo");

  private GistService gistService;

  @MockBean
  private GistRepository gistRepository;

  @Before
  public void init() {
    when(gistRepository.findAll()).thenReturn(Collections.singletonList(gist));
    when(gistRepository.findById(anyString())).thenReturn(Optional.of(gist));
    when(gistRepository.save(any(GistEntity.class))).thenReturn(gist);

    gistService = new GistService(gistRepository);
  }

  @Test
  public void should_retrieve_gists_values() {
    List<GistValue> results = gistService.findGists();
    Assertions.assertThat(results).isNotEmpty();
  }

  @Test
  public void should_retrieve_a_gist_value() {
    Optional<GistValue> result = gistService.findGistById("1");
    assertThat(result.isPresent()).isTrue();
    Assertions.assertThat(result).get().isEqualTo(gist.toValue());
  }

  @Test
  public void should_create_gist_and_return_value() {
    GistValue gistValue = gist.toValue();
    GistValue result = gistService.createGist(gistValue);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(gistValue);
  }
}
