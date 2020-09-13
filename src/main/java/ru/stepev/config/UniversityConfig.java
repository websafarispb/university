package ru.stepev.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ru.stepev.dao")
@PropertySource("classpath:university.properties")
public class UniversityConfig {

	@Value("${driver}")
	private String driver;

	@Value("${url}")
	private String url;

	@Value("${user}")
	private String user;

	@Value("${pass}")
	private String pass;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTamplate(DataSource dateSourse) {
		return new JdbcTemplate(dateSourse);
	}
}
