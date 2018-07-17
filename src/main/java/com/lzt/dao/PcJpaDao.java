package com.lzt.dao;

import com.lzt.entity.PC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Repository
public interface PcJpaDao extends CrudRepository<PC,Long> {
}
