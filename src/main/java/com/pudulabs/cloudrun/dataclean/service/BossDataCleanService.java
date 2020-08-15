package com.pudulabs.cloudrun.dataclean.service;

import com.pudulabs.cloudrun.dataclean.dto.DataCleanResponse;

public interface BossDataCleanService {

    DataCleanResponse cleanDataMultitenant(String tenant);

}
