package com.afossey.projects.educational.gists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GistService {

  private GistRepository gistRepository;

  @PostConstruct
  public void populateGistData() {
    gistRepository.deleteAll();
    Stream.of("Foo", "FooTwo")
        .map(name -> new GistEntity(null, name))
        .map(gistRepository::save)
        .forEach(gist -> log.info("{}", gist));

  }

  List<GistValue> findGists() {
    return gistRepository.findAll().stream().map(GistEntity::toValue).collect(Collectors.toList());
  }

  Optional<GistValue> findGistById(String id) {
    return gistRepository.findById(id).map(GistEntity::toValue);
  }

  GistValue createGist(GistValue gistValue) {
    GistEntity gistEntity = gistRepository.save(gistValue.toEntity());
    return gistEntity.toValue();
  }
}
