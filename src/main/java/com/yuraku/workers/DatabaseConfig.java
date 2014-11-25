package com.yuraku.workers;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {
	
	// 別Datasourceの定義を書けるか？⇒問題なし。但し２つ以上になる時はJdbcTemplateに@Autowired,@Qualifierが必要
	@Bean(name = "dsGenbu")
	@ConfigurationProperties(prefix="subdatasource.genbumysql")
	public DataSource genbuDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcGenbu")
	@Autowired
	public JdbcTemplate genbuJdbcTemplate(@Qualifier("dsGenbu") DataSource dsGenbu) {
	    return new JdbcTemplate(dsGenbu);
	}

	@Bean(name = "dsAir")
	@Primary
	@ConfigurationProperties(prefix="subdatasource.airmysql")
	public DataSource airDataSource() {
	    return DataSourceBuilder.create().build();
	}


	@Bean(name = "jdbcAir") 
	@Autowired
	@Qualifier("dsAir")
    public JdbcTemplate airJdbcTemplate(DataSource dsAir) { 
        return new JdbcTemplate(dsAir); 
    } 
}
