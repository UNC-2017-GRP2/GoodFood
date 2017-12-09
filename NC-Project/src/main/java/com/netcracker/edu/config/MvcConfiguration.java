package com.netcracker.edu.config;

import com.netcracker.edu.dao.UserDao;
import com.netcracker.edu.dao.UserDaoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages="com.netcracker.edu")
@EnableWebMvc
@PropertySource("classpath:db.properties")
@Import(value = { LoginSecurityConfig.class })
public class MvcConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    private Environment env;

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public Connection getConnection() throws ClassNotFoundException {
	    System.out.println("fuck1");
        Class.forName("org.postgresql.Driver");
        String url = env.getProperty("db.url");

        //  Database credentials
        String user = env.getProperty("db.user");
        String password = env.getProperty("db.password");

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return conn;
	}

	@Bean
	public UserDao getObjectDao() throws ClassNotFoundException {
        return new UserDaoImpl(getConnection());
	}
}
