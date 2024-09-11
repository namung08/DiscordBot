package com.namung.cazinou.infrastructure.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

  @Bean
  public MongoTemplate mongoTemplate(
    MongoDatabaseFactory mongoDatabaseFactory,
    MappingMongoConverter mappingMongoConverter
  ) {
    mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
  }

  @Bean
  public LocalValidatorFactoryBean validatorFactoryBean() {
    return new LocalValidatorFactoryBean();
  }
}
