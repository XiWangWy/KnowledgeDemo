package com.hitales.Repository;

import com.hitales.entity.GaiNianEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhubo on 2018/7/23.
 */

public interface AchieveDataRepository extends MongoRepository<GaiNianEntity,String>{

}
