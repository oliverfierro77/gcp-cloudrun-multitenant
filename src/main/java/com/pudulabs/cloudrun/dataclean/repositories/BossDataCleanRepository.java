package com.pudulabs.cloudrun.dataclean.repositories;

import com.pudulabs.cloudrun.dataclean.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BossDataCleanRepository extends JpaRepository<Order, String> {

  @Query(nativeQuery = true)
  @Modifying
  @Transactional
  int cleanData();

  @Query(value = "SELECT order_country_description FROM \"order\" WHERE order_number=:id", nativeQuery = true)
  String selectDataMultitenant(@Param("id") String id);
}
