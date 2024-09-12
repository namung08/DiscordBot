package com.namung.cazinou.seq.repository;

import com.namung.cazinou.seq.model.CustomSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomSequenceRepository
  extends MongoRepository<CustomSequence, String> {}
