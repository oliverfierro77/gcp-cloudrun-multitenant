package com.pudulabs.cloudrun.dataclean.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantDataCleanIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static String DEFAULT_TENANT_ID = "CL";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = DataCleanTenantContext.getTenantId();
        return (currentTenantId != null) ? currentTenantId : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
