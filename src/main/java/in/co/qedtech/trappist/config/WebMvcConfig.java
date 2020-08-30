package in.co.qedtech.trappist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/test/**").addResourceLocations("/test/").setCachePeriod(0);
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(0);
//        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(0);
//        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(0);
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//        WebMvcConfigurer.super.configureMessageConverters(converters);
//    }
}
