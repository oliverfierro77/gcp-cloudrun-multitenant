package com.pudulabs.cloudrun.dataclean.config.multitenant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.pudulabs.cloudrun.dataclean.config.Secret;
import com.pudulabs.cloudrun.dataclean.model.Order;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties({ MultiTenantDataCleanProperties.class, JpaProperties.class })
@EnableJpaRepositories(basePackages = { "com.pudulabs.cloudrun.dataclean.repositories" }, transactionManagerRef = "txManager")
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private MultiTenantDataCleanProperties multiTenantDataCleanProperties;

    // Secret Manager secrets list
    @Autowired private Secret secrets;

  /*@Bean(name = "dataSourcesDataClean" )
  public Map<String, DataSource> dataSourcesDataClean() {
      Map<String, DataSource> result = new HashMap<>();
      for (DataSourceProperties dsProperties : this.multiTenantDataCleanProperties.getDataSources()) {
          DataSourceBuilder factory = DataSourceBuilder
                  .create()
                  .url(dsProperties.getUrl())
                  .username(dsProperties.getUsername())
                  .password(dsProperties.getPassword())
                  .driverClassName(dsProperties.getDriverClassName());
          result.put(dsProperties.getTenantId(), factory.build());
      }
      return result;
  }*/

  @Bean(name = "dataSourcesDataClean")
  public Map<String, DataSource> dataSourcesDataClean() {
    Map<String, DataSource> result = new HashMap<>();
    for (MultiTenantDataCleanProperties.DataSourceProperties dsProperties : this.multiTenantDataCleanProperties.getDataSources()) {
        DataSourceBuilder factory =
            DataSourceBuilder.create()
                .url((String) secrets.getSecretTenantAllCountries().get(dsProperties.getTenantId()).get(dsProperties.getTenantId() + "_DB_URL"))
                .username((String) secrets.getSecretTenantAllCountries().get(dsProperties.getTenantId()).get(dsProperties.getTenantId() + "_DB_USER"))
                .password((String) secrets.getSecretTenantAllCountries().get(dsProperties.getTenantId()).get(dsProperties.getTenantId() + "_DB_PWD"))
                .driverClassName(dsProperties.getDriverClassName());
        result.put(dsProperties.getTenantId(), factory.build());
    }
    return result;
  }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new DataCleanDataSourceMultiTenantConnectionProviderImpl();
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new TenantDataCleanIdentifierResolverImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
                                                                           CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        // No dataSource is set to resulting entityManagerFactoryBean
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan(new String[] { Order.class.getPackage().getName() });
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    //@Primary
    @Bean
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
