package org.apache.EcoMotto.web.config;

import org.apache.EcoMotto.web.component.IPersonService;
import org.apache.EcoMotto.web.component.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@ComponentScan("org.apache.EcoMotto.web")
@EnableWebMvc
public class EcoMottoWebConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api/**").addResourceLocations("/api/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Bean
	public ServletContextTemplateResolver thymeleafTemplateResolver() {
	    ServletContextTemplateResolver resolver = 
	        new ServletContextTemplateResolver();
	    resolver.setPrefix("/api/");
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode("HTML5");
	    resolver.setCacheable(true);
	    return resolver;
	}
	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine() {
	    SpringTemplateEngine engine = new SpringTemplateEngine();
	    engine.setTemplateResolver(thymeleafTemplateResolver());
	    return engine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
	    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	    resolver.setTemplateEngine(thymeleafTemplateEngine());
	    return resolver;
	}
//	@Bean
//	public UrlBasedViewResolver setupViewResolver() {
//		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//		resolver.setPrefix("/api/");
//		resolver.setSuffix(".jsp");
//		resolver.setViewClass(ServletContextTemplateResolver.class);
//		return resolver;
//	}

	@Bean
	public IPersonService personService() {
		return new PersonService();
	}
}
