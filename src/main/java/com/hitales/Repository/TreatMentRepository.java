package com.hitales.Repository;

import com.hitales.entity.Disease;
import com.hitales.entity.TreatMent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhubo on 2018/7/23.
 */

public interface TreatMentRepository extends MongoRepository<TreatMent,String>{
    TreatMent findByName(String name);
}
