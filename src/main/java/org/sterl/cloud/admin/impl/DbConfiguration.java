package org.sterl.cloud.admin.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DbConfiguration {
    private static final Logger dbLogger = LoggerFactory.getLogger("org.hsqldb.embedded");
    private boolean useInfo = true;
    
    @Bean
    public DataSource dataSource(Server hsqlServer) {
        return DataSourceBuilder.create()
            .username("sa")
            .url("jdbc:hsqldb:hsql://localhost:9955/cloudAdmin")
            .driverClassName(JDBCDriver.class.getName())
            .build();
    }
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server hsqlServer() {
        final Server server = new Server();
        useInfo = true;
        server.setLogWriter(slf4jPrintWriter(false));
        server.setErrWriter(slf4jPrintWriter(true));
        server.setDatabaseName(0, "cloudAdmin");
        server.setDatabasePath(0, "./cloudAdminDb/hsql.db");
        server.setPort(9955);
        server.setNoSystemExit(true);
        server.setDaemon(true);
        //server.start();
        useInfo = true;
        return server;
    }
    
    private PrintWriter slf4jPrintWriter(final boolean error) {
        PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream()) {
            @Override
            public void println(final String x) {
                if (error) {
                    dbLogger.error(x);
                } else if (useInfo) {
                    dbLogger.info(x);
                } else {
                    dbLogger.debug(x);
                }
            }
        };
        return printWriter;
    }
}