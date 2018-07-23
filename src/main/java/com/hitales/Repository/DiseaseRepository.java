package com.hitales.Repository;

import com.hitales.entity.Disease;
import com.hitales.entity.Origin;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhubo on 2018/7/23.
 */

public interface DiseaseRepository extends MongoRepository<Disease,String>{

}
