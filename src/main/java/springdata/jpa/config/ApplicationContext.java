package springdata.jpa.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * An application context Java configuration class.
 * @author Petri Kainulainen
 */
@Configuration
@ComponentScan(basePackages = {
        "springdata.jpa.controller",
        "springdata.jpa.service",
        "springdata.jpa.exception"
})
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories("springdata.jpa.repository")
@PropertySource("classpath:application.properties")
public class ApplicationContext extends WebMvcConfigurerAdapter {
    
    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    private static final String PROPERTY_NAME_MESSAGESOURCE_BASENAME = "message.source.basename";
    private static final String PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE = "message.source.use.code.as.default.message";

    @Resource
    private Environment environment;

    /**
     * Configures the location of static resources such as css files.
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * Ensures that dispatcher servlet can be mapped to '/' and static resources
     * are still served by the containers default servlet.
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Configures the data source.
     * @return
     */
    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        
        return dataSource;
    }

    /**
     * Configures the transaction manager.
     * @return
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    /**
     * Configures the entity manager factory.
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

        Properties jpaProterties = new Properties();
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        entityManagerFactoryBean.setJpaProperties(jpaProterties);

        return entityManagerFactoryBean;
    }

    /**
     * Configures the exception resolver.
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.packtpub.springdata.jpa.service.NotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    /**
     * Configures the message source bean.
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(environment.getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_BASENAME));
        messageSource.setUseCodeAsDefaultMessage(Boolean.parseBoolean(environment.getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE)));

        return messageSource;
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
    	ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
    	viewResolver.setOrder(1);
    	
    	//initialize content negotiation manager
    	Map<String, MediaType> map = new HashMap<String, MediaType>();
    	map.put("json", MediaType.APPLICATION_JSON);
    	map.put("xml", MediaType.APPLICATION_XML);
    	PathExtensionContentNegotiationStrategy path = new PathExtensionContentNegotiationStrategy(map);
    	ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager(path);
    	viewResolver.setContentNegotiationManager(contentNegotiationManager);
    	
    	//initialize default views
    	List<View> defaultViews = new ArrayList<View>();
    	MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
    	
    	defaultViews.add(jsonView);
    	Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    	jaxb2Marshaller.setPackagesToScan(new String[] {"springdata.jpa.dto"});
    	MarshallingView marshallingView = new MarshallingView(jaxb2Marshaller);
    	defaultViews.add(marshallingView);
    	
    	viewResolver.setDefaultViews(defaultViews);
    	
    	return viewResolver;
    }
    /**
     * Configures the view resolver.
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
}
