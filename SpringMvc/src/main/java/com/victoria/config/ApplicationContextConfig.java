package com.victoria.config;

import com.victoria.repository.ItemRepository;
import com.victoria.repository.OrderRepository;
import com.victoria.repository.UserRepository;
import com.victoria.repository.impl.ItemRepositoryImpl;
import com.victoria.repository.impl.OrderRepositoryImpl;
import com.victoria.repository.impl.UserRepositoryImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "com.victoria")
@EnableWebMvc
@PropertySource("classpath:jdbc.properties")
@Import({SecurityConfig.class})
public class ApplicationContextConfig  extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Bean(name = "dataSource")
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return  dataSource;
    }


    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Bean
    public UserRepository getUserRepository() throws ClassNotFoundException, SQLException {
        return new UserRepositoryImpl(dataSource());
    }

    @Bean
    public ItemRepository getItemRepository() throws ClassNotFoundException, SQLException {
        return new ItemRepositoryImpl(dataSource());
    }

    @Bean
    public OrderRepository getOrderRepository() throws ClassNotFoundException, SQLException {
        return new OrderRepositoryImpl(dataSource());
    }



}
