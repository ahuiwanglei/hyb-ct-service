package com.micro.seller.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.micro.seller.entity.db.HybBalance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.micro.seller",
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager")
@Configuration
public class DruidDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @PersistenceContext(unitName = "primary")
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(druidDataSource())
                .packages(HybBalance.class)
                .persistenceUnit("cbDB")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(customerEntityManagerFactory(builder).getObject());
    }




}
