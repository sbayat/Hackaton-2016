package ing.hackaton.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dutler on 11-12-15.
 */
@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {

    //@Value("${web.static-content.path}")
    private String STATIC_CONTENT_PATH = "/Users/hosinglee/workspace/hackaton2016/frontend/app/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + STATIC_CONTENT_PATH + "/");
    }
}
