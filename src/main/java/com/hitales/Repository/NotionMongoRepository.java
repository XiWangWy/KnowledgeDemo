package com.hitales.Repository;

import com.hitales.entity.Origin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotionMongoRepository extends MongoRepository<Origin, String> {

}
