package com.namung.cazinou.seq.service;

import com.mongodb.MongoTimeoutException;
import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.exception.CazinouExceptionCode;
import com.namung.cazinou.seq.model.CustomSequence;
import com.namung.cazinou.seq.repository.CustomSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomSequenceServiceImpl implements CustomSequenceService {

  private final CustomSequenceRepository repository;

  @Override
  public String createSequence(String sequenceName)
    throws MongoTimeoutException, DataAccessException {
    repository.save(
      CustomSequence.builder().id(sequenceName).number(1L).build()
    );
    return "Success to create sequence";
  }

  @Override
  public Long getSequenceNumber(String sequenceName) {
    return getSequence(sequenceName).getNumber();
  }

  @Override
  public String setSequenceNumber(String sequenceName)
    throws MongoTimeoutException, DataAccessException {
    CustomSequence sequence = getSequence(sequenceName);
    sequence.setNumber(sequence.getNumber() + 1);
    repository.save(sequence);
    return "Success to next set sequence number";
  }

  // 코드 단순화 작업
  private CustomSequence getSequence(String sequenceName) {
    return repository
      .findById(sequenceName)
      .orElseThrow(() ->
        new CazinouException(
          HttpStatus.BAD_REQUEST,
          CazinouExceptionCode.NOT_FOUND_SEQUENCE
        )
      );
  }
}
