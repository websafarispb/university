package ru.stepev.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ru.stepev.dao.*;

@Configuration
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
	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		return dataSource;
	}

	@Bean
	public JdbcTemplate getJdbcTamplate() {
		return new JdbcTemplate(mysqlDataSource());
	}
	
	@Bean
	public CourseDao getCourseDao() {
		return new CourseDao(getJdbcTamplate());
	}
	
	@Bean
	public StudentDao getStudentDao() {
		return new StudentDao(getJdbcTamplate());
	}
	
	@Bean
	public TeacherDao getTeacherDao() {
		return new TeacherDao(getJdbcTamplate());
	}
	
	@Bean
	public GroupDao getGroupDao() {
		return new GroupDao(getJdbcTamplate());
	}
	
	@Bean
	public ClassroomDao getClassroomDao() {
		return new ClassroomDao(getJdbcTamplate());
	}
	
	@Bean
	public LectureDao getLectureDao() {
		return new LectureDao(getJdbcTamplate());
	}
	
	@Bean
	public DailyScheduleDao getDailyScheduleDao() {
		return new DailyScheduleDao(getJdbcTamplate());
	}
}
