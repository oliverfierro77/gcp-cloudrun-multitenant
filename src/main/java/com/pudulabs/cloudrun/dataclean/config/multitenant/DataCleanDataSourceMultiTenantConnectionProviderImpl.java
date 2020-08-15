package com.pudulabs.cloudrun.dataclean.config.multitenant;

import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class DataCleanDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final long serialVersionUID = 1L;

    @Autowired
    private Map<String, DataSource> dataSourcesDataClean;

    @Override
    protected DataSource selectAnyDataSource() {
        return this.dataSourcesDataClean.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return this.dataSourcesDataClean.get(tenantIdentifier);
    }
}
