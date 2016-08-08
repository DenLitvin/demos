package com.getgo.test;

import com.citrix.jmx.monitor.SystemStatusMonitor;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) throws Exception {

        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet(), "/rest/v1/*");
        registrationBean
                .setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    @Bean
    public SystemStatusMonitor systemStatusMonitor() {
        SystemStatusMonitor monitor = new SystemStatusMonitor();
        //@todo make it configurable
        monitor.setSystemVIP("arl");
        return monitor;
    }


    @Bean
    public HealthCheckServlet healthCheckServlet() {
        return new HealthCheckServlet();
    }

    @Bean
    public ServletRegistrationBean registerHealthCheckServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(healthCheckServlet(), "/healthcheck");
        return registrationBean;
    }

    @Bean
    public MetricsServlet metricsServlet() {
        MetricsServlet servlet = new MetricsServlet();
        return servlet;
    }

    @Bean
    public ServletRegistrationBean registerMetricsServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(metricsServlet(), "/metrics");
        //  registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }


}