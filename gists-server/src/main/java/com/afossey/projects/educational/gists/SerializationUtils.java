package com.afossey.projects.educational.gists;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SerializationUtils {

  private static final ObjectMapper om = new ObjectMapper();
  private static final String JACKSON_DESERIALIZATION_FAILED = "Jackson deserialization failed.";

  static <T> Optional<T> deserialize(String json, Class<T> tClass) {
    try {
      T obj = om.readValue(json, tClass);
      return obj != null ? Optional.of(obj) : Optional.empty();
    } catch (IOException e) {
      log.error(JACKSON_DESERIALIZATION_FAILED, e);
    }
    return Optional.empty();
  }
}
