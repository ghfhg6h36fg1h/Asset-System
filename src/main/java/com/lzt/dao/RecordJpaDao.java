package com.lzt.dao;

import com.lzt.entity.Record;
import com.lzt.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Enzo Cotter on 2018-6-20.
 */
@Repository
public interface RecordJpaDao extends CrudRepository<Record,Long> {

}
