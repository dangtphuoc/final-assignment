package springdata.jpa.config;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Web application Java configuration class. The usage of web application
 * initializer requires Spring Framework 3.1 and Servlet 3.0.
 * @author Petri Kainulainen
 */
public class DataJPAExampleInitializer implements WebApplicationInitializer {
    
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Loading application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationContext.class);

        //Dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

        //Sitemesh
        FilterRegistration.Dynamic sitemesh = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
        EnumSet<DispatcherType> sitemeshDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        sitemesh.addMappingForUrlPatterns(sitemeshDispatcherTypes, true, "*.jsp");

        //Context loader listener
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}
