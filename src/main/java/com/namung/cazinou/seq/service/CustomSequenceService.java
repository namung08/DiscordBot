package com.namung.cazinou.seq.service;

import com.mongodb.MongoTimeoutException;
import com.namung.cazinou.infrastructure.exception.CazinouException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public interface CustomSequenceService {
  /**
   * 새로운 sequence를 생성한다.
   * @param sequenceName {@code sequenceName} 을 id 로 사용하여 sequence를 생성한다.
   * @throws MongoTimeoutException if a timeout occurs while waiting for a pooled connection
   * @throws DataAccessException if a data access exception occurs while performing the operation
   */
  String createSequence(String sequenceName);

  /**
   * 현재 사용이 가능한 sequence number를 반환한다.
   * @param sequenceName the name of the sequence
   * @return the sequence number
   * @throws CazinouException if the sequence is not found
   */
  Long getSequenceNumber(String sequenceName);

  /**
   * 이 메서드는 sequence number 를 이용항 저장이 일어날 경우에만 작동이 되야함.<br>
   * 만약 sequence number 를 사용하여 특정 collection에 저장하였다면, 해당 sequence number를 증가시킨다.<br>
   * @param sequenceName the name of the sequence
   * @throws MongoTimeoutException if a timeout occurs while waiting for a pooled connection
   * @throws DataAccessException if a data access exception occurs while performing the operation
   */
  String setSequenceNumber(String sequenceName);
}
