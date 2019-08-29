package org.sterl.cloudadmin;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Controller
@EnableTransactionManagement // basically not needed anymore ...
public class CloudAdminApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CloudAdminApplication.class, args);
    }
    
    // https://www.vojtechruzicka.com/spring-boot-version/
    @Autowired private BuildProperties buildProperties;
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter((AbstractId<?> id) -> id.getValue());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // no caching for HTML pages
        registry.addResourceHandler("*.html")
                .addResourceLocations("classpath:/META-INF/resources/webjars/cloud-admin-frontend/" + buildProperties.getVersion() + "/");
        // the remaining stuff for 365 days
        // apply custom config as needed
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/cloud-admin-frontend/" + buildProperties.getVersion() + "/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        
    }
    
    /**
     * This is needed in case we have no index.html in the static folder.
     * In the JAR solution we don't even have the static folder ...
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
    // /{[path:[^\\\\.]*}
    @RequestMapping(value = "/{name:^(?!api).+}", method = RequestMethod.GET)
    public String redirect() {
        return "forward:/index.html";
    }
}
