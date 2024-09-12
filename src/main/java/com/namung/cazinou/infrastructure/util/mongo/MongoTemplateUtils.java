package com.namung.cazinou.infrastructure.util.mongo;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

/**
 * MongoTemplate 유틸리티 인터페이스 <br>
 * MongoTemplate 관련 각종 유틸리티 메서드를 정의한다.<br>
 * @since 2024.09.12
 * @version 1.0.0
 * @author namung
 */
public interface MongoTemplateUtils {
  /**
   * 필드명과 값으로 Query 생성 <br>
   * {@code field} 와 {@code value} 를 받아서 Query 를 생성한다.<br>
   * {@code field}는 필드명, {@code value}는 값이다.<br>
   * @param field 필드명 {@link String}
   * @param value 값 {@link Object}
   * @return {@link Query}
   */
  Query getQueryByField(String field, Object value);

  /**
   * 필드명과 값으로 Query 생성 <br>
   * {@code isAsc} 값에 따라 정렬 방향이 달라진다.<br>
   * {@code isAsc} 가 {@code true} 이면 오름차순, {@code false} 이면 내림차순이다.<br>
   * @param field 필드명 {@link String}
   * @param isAsc 정렬 방향 {@link Boolean}
   * @return {@link Sort} 오름차순 또는 내림차순
   */
  Sort getSort(String field, boolean isAsc);

  /**
   * 업데이트를 성공했을 때 메시지 반환 <br>
   * {@code result} 가 성공했을 때 {@code message} 를 반환한다.<br>
   * @param result {@link UpdateResult} 업데이트 결과
   * @param message 성공 메시지 {@link String}
   * @return {@link String} 성공 메시지
   */
  String updateResultMessage(UpdateResult result, String message);

  /**
   * 업데이트 성공 여부 반환 <br>
   * {@code result} 가 성공했을 때 {@code true} 를 반환한다.<br>
   * @param result {@link UpdateResult} 업데이트 결과
   * @return {@link Boolean} 성공 여부
   */
  Boolean updateResultSuccess(UpdateResult result);
}
