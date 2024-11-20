package com.pot.app.infoarchive.testUtil;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.pot.app.infoarchive.config.ClickHouseProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.sql.SQLException;

@TestComponent
@RequiredArgsConstructor
public class TestBDFacade {

    private final Jdbi jdbi;

    @Autowired
    @SneakyThrows
    public TestBDFacade(ClickHouseProperties clickHouseProperties) {
        this.jdbi = Jdbi.create(new ClickHouseDataSource(
                clickHouseProperties.jdbcUrl(),
                clickHouseProperties.driverSettings().jdbc()
        ) {
            @Override
            public ClickHouseConnection getConnection() throws SQLException {
                return getConnection(
                        clickHouseProperties.username(),
                        clickHouseProperties.password()
                );
            }
        }).setSqlLogger(new Slf4JSqlLogger());
    }

    public void createMyTable() {
        jdbi.useHandle(handle -> {
            handle.execute("create database if not exists info");
            handle.execute("""
                    create table if not exist info.article
                    (
                        'article_id' UUID,
                    )
                    ENGINE = tinylog
                    """);

        });
    }

    public void cleanDatabase() {
        jdbi.useHandle(handle -> {
            handle.execute("truncate table if exists info.article");
        });
    }

    public void executeSql(String sql) {
        jdbi.useHandle(handle -> handle.execute(sql));
    }
}























