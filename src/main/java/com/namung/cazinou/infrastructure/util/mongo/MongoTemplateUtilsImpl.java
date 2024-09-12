package com.namung.cazinou.infrastructure.util.mongo;

import com.mongodb.client.result.UpdateResult;
import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.exception.CazinouExceptionCode;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MongoTemplateUtilsImpl implements MongoTemplateUtils {

  @Override
  public Query getQueryByField(String field, Object value) {
    return new Query(Criteria.where(field).is(value));
  }

  @Override
  public Sort getSort(String field, boolean isAsc) {
    return Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC);
  }

  @Override
  public String updateResultMessage(UpdateResult result, String message) {
    if (updateResultSuccess(result)) {
      return message;
    } else {
      throw new CazinouException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        CazinouExceptionCode.UPDATE_FAIL
      );
    }
  }

  @Override
  public Boolean updateResultSuccess(UpdateResult result) {
    return result.getMatchedCount() == 1 && result.getModifiedCount() == 1;
  }
}
