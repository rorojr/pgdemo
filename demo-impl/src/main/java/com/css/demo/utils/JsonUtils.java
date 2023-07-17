package com.css.demo.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JsonUtils {
  private static final ObjectMapper objectMapper =
          new ObjectMapper(new JsonFactory())
                  .registerModule(new Jdk8Module())
                  .registerModule(new GuavaModule())
                  .registerModule(new JavaTimeModule());


  public static String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Failed to serialize object to json: {}", object, e);
      throw new RuntimeException("Failed to serialize object to json");
    }
  }

  public static <T> Optional<T> deserialize(String jsonString, Class<T> tClass) {
    try {
      return Optional.of(objectMapper.readValue(jsonString, tClass));
    } catch (IOException e) {
      log.error("Failed to deserialize json to object: {}", jsonString, e);
      return Optional.empty();
    }
  }

  public static <T> Optional<T> deserialize(String jsonString, TypeReference<T> typeReference) {
    try {
      return Optional.of(objectMapper.readValue(jsonString, typeReference));
    } catch (IOException e) {
      log.error("Failed to deserialize json to object: {}", jsonString, e);
      return Optional.empty();
    }
  }
}
