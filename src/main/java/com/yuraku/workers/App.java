package com.yuraku.workers;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App  extends WebMvcConfigurerAdapter {

	public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }

	 @Bean(name = "localeResolver")
	 public CookieLocaleResolver localeResolver() {
		 CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		 Locale defaultLocale = new Locale("ja");
		 localeResolver.setDefaultLocale(defaultLocale);
		 return localeResolver;
	 }

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	// 別Datasourceの定義を書けるか？⇒問題なし
	@Bean(name="genbuDs")
	@ConfigurationProperties(prefix="subdatasource.genbumysql")
	public DataSource secDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcGenbu") 
    public JdbcTemplate jdbcTemplate(DataSource genbuDs) { 
        return new JdbcTemplate(genbuDs); 
    } 

}
