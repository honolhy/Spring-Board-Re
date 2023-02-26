package com.study.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.yml")
public class DatabaseConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    // 마이바티스와 스프링의 연동 모듈로 사용
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // factoryBean - 데이터소스를 참조하여 xml mapper의 경로와 설정파일 경로 등의 정보를 갖는 객체
        factoryBean.setDataSource(dataSource());
        // 해당 메서드에 XML Mapper의 경로를 선언해 주어야 스프링이 XML Mapper를 인식할 수 있다
        factoryBean.setMapperLocations(context.getResources("classpath:/mappers/**/*Mapper.xml"));
        // mybatisConfig() 빈(Bean)을 이용해서 MyBatis 옵션을 설정한다
        factoryBean.setConfiguration(mybatisConfig());

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    // application.yml에서 mybatis.configuration으로 시작하는 모든 설정을 읽어 스프링 컨테이너에 빈(Bean)으로 등록한다
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }
}