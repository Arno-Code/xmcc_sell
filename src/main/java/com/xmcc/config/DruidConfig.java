package com.xmcc.config;


import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration   //表示为配置类
public class DruidConfig {

    //value:bean的名字
    @Bean(value = "druidDataSource" ,initMethod = "init" ,destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.druid")//对应yml中的前缀
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));//guava创建集合过滤器配置
        return druidDataSource;
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);//慢查询记录日志
        statFilter.setSlowSqlMillis(5);//慢查询时间s
        statFilter.setMergeSql(true);//格式化sql
        return statFilter;
    }


    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        //durid监控平台，输入http://localhost:8888/druid即可访问
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
