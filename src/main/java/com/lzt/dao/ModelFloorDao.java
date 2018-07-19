package com.lzt.dao;

import com.lzt.entity.ModelFloor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Enzo Cotter on 2018-7-19.
 */
@Repository
public interface ModelFloorDao extends CrudRepository<ModelFloor,Long> {
}
