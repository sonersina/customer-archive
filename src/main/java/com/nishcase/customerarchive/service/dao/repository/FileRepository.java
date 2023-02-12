package com.nishcase.customerarchive.service.dao.repository;

import com.nishcase.customerarchive.service.dao.entity.File;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
  @Transactional
  @Modifying
  @Query("UPDATE File f SET f.deletedAt = CURRENT_TIMESTAMP WHERE f.customer.id = :id")
  Integer deleteByCustomerId(@Param("id") Long id);
}

