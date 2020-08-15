package com.pudulabs.cloudrun.dataclean.service;

import com.pudulabs.cloudrun.dataclean.dto.DataCleanResponse;
import com.pudulabs.cloudrun.dataclean.model.Order;
import com.pudulabs.cloudrun.dataclean.repositories.BossDataCleanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * DataClean Multitenant Service
 *
 * @author Oliver Fierro V.
 */
@Slf4j
@Service
public class BossDataCleanServiceImpl implements BossDataCleanService {

  // private final JdbcTemplate jdbcTemplate;

  @Autowired private BossDataCleanRepository repository;

  /**
   * Clean Data By Table Name
   *
   * @return DataCleanResponse
   */
  public DataCleanResponse cleanDataMultitenant(String tenant) {

    DataCleanResponse response = new DataCleanResponse();

    try {
      //To check your DB data. Set any of your order number below.
      String orderNum = "0000000001";
      String orderDesc = repository.selectDataMultitenant(orderNum);
      log.info("My order number country: " + orderDesc);

      //Deleting Order Number older than x days
      int rowCount = repository.cleanData();

      log.info("Clean Data Multitenant | Tenant: " + tenant + " Rows Affected: " + rowCount);

      response.setStatus(true);
      response.setRows(rowCount);
      response.setCountry(tenant);
    } catch (Exception ex) {
      log.info("Clean Data Multitenant Error {}", ex.getMessage());
      response.setStatus(false);
    }
    return response;
  }

}
