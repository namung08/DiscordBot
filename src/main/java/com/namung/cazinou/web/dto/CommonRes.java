package com.namung.cazinou.web.dto;

public record CommonRes<T>(Boolean aSuccess, T data) {
  public static <T> CommonRes<T> success(T data) {
    return new CommonRes<>(true, data);
  }

  public static <T> CommonRes<T> fail(T data) {
    return new CommonRes<>(false, data);
  }
}
