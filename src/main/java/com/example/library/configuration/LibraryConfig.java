package com.example.library.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "libraryEntityManager",
        transactionManagerRef = "libraryTransactionManager",
        basePackages = {"com.example.library.repository.library"})
public class LibraryConfig {

    @Value("${spring.datasource.host}")
    String host;

    @Value("${spring.datasource.port}")
    String port;

    @Value("${spring.datasource.name.library}")
    String dataSourceName;

    @Bean(name = "libraryEntityManager")
    public LocalContainerEntityManagerFactoryBean getLibraryEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("libraryDataSource") DataSource libraryDataSource) {
        return builder
                .dataSource(libraryDataSource)
                .packages("com.example.library.model.library")
                .persistenceUnit("books")
                .persistenceUnit("categories")
                .properties(additionalJpaProperties())
                .build();
    }

    Map<String,?> additionalJpaProperties(){
        Map<String,String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "update");
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        map.put("hibernate.show_sql", "true");

        return map;
    }

    @Bean("libraryDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource libraryDataSource() {
        DataSourceProperties dataSource = new DataSourceProperties();
        dataSource.setUrl("jdbc:postgresql://" + host + ":" + port + "/" + dataSourceName);
        return dataSource.initializeDataSourceBuilder().build();
    }

    @Bean(name = "libraryTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("libraryEntityManager") EntityManagerFactory serversEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);

        return transactionManager;
    }
}
