package com.hc.datasource;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

/****
 * 配置数据源
 * 没有@Primary注解
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "test2EntityManagerFactory",//实体管理
        transactionManagerRef = "test2TransactionManager", //事务管理
        basePackages = "com.hc.test2.dao") //设置Repository所在位置
public class Test2DataSourceConfig {
    //数据源
    @Resource(name = "test2DataSource")
    private DataSource dataSource;

    // JPA其它参数设置
    // JpaProperties是系统提供的一个实例，里边的数据是application.properties中配置的jpa相关的配置
    @Resource
    private JpaProperties jpaProperties;

    //实体管理工厂builder
    @Resource
    private EntityManagerFactoryBuilder builder;

    //配置实体管理工厂的Bean
    @Bean(name = "test2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean test2EntityManagerFactoryBean() {
        return builder.dataSource(dataSource)
                //加入jpa的其他配置参数比如（ddl-auto: update等）
                .properties(jpaProperties.getProperties())
                //相当于给这个配置取一个别名
                .persistenceUnit("test2PersistenceUnit")
                //设置这个数据源对应的实体类所在位置
                .packages("com.hc.bean")
                .build();
    }

    /**
     * EntityManager不过解释，用过jpa的应该都了解
     *
     * @return
     */
    @Bean(name = "test2EntityManager")
    public EntityManager entityManager() {
        return test2EntityManagerFactoryBean().getObject().createEntityManager();
    }


    @Bean(name = "test2TransactionManager")
    public PlatformTransactionManager test2TransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(test2EntityManagerFactoryBean().getObject());
    }

    ///**
    // * jpa事务管理
    // *
    // * @return
    // */
    //@Bean(name = "test2TransactionManager")
    //public JpaTransactionManager transactionManager() {
    //    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    //    jpaTransactionManager.setEntityManagerFactory(test2EntityManagerFactoryBean().getObject());
    //    return jpaTransactionManager;
    //}
}