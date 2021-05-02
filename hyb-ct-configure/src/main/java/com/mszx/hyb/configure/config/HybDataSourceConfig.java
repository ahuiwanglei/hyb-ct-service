package com.mszx.hyb.configure.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mszx.hyb.configure.model.db.hyb.HybConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@EntityScan(basePackages = "com.mszx.hyb.configure.model.db.hyb")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.mszx.hyb.configure.dao.hyb",
        entityManagerFactoryRef = "hybEntityManager",
        transactionManagerRef = "hybTransactionManager")        // 2
public class HybDataSourceConfig {

//    @Bean
//
//    @ConfigurationProperties("spring.datasource.hyb")
//    public DataSource hybDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Value("${spring.datasource.hyb.url}")
    private String dbUrl;

    @Value("${spring.datasource.hyb.username}")
    private String username;

    @Value("${spring.datasource.hyb.password}")
    private String password;

    @Value("${spring.datasource.hyb.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Primary
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource hybDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        return datasource;
    }


    @PersistenceContext(unitName = "hyb")
    @Primary
    @Bean(name = "hybEntityManager")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(hybDataSource())
                .packages(HybConfiguration.class)
                .persistenceUnit("hybDB")
                .build();
    }

    @Bean(name = "hybTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager tm = new JpaTransactionManager();
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =customerEntityManagerFactory(builder);
        tm.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
//        Properties props = new Properties();
//        props.put("hibernate.show_sql", "true");
//        props.put("hibernate.format_sql", "true");
//        props.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
////        props.put("hibernate.ejb.naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
//        props.put("hibernate.connection.charSet", "UTF-8");
//        props.put("hibernate.current_session_context_class", "jta");
//        props.put("hibernate.archive.autodetection", "class");
//        props.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        props.put("hibernate.hbm2ddl.auto", "update");
//
//        tm.setJpaProperties(props);
        tm.setDataSource(hybDataSource());
        return tm;
    }


}
