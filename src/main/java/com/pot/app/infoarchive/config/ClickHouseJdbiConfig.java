package com.pot.app.infoarchive.config;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.github.housepower.jdbc.BalancedClickhouseDataSource;
import com.github.housepower.settings.SettingKey;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class ClickHouseJdbiConfig {

    private final ClickHouseProperties clickHouseProperties;

    @Bean
    public Jdbi clickHouseJdbi() {
        var dataSource = clickHouseProperties.clientType().equals(ClickHouseProperties.ClientType.NATIVE_JDBC)
                ? nativeJdbcClickHouseDataSource() : jdbcClickHouseDataSource();
        return Jdbi.create(dataSource).setSqlLogger(new Slf4JSqlLogger());
    }

    @SneakyThrows
    private ClickHouseDataSource jdbcClickHouseDataSource() {
        return new ClickHouseDataSource(
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
        };
    }

    private BalancedClickhouseDataSource nativeJdbcClickHouseDataSource() {
        final var settings = new Properties();
        settings.putAll(clickHouseProperties.driverSettings().jdbc());
        settings.put(SettingKey.user.name(), clickHouseProperties.username());
        settings.put(SettingKey.password.name(), clickHouseProperties.password());
        return new BalancedClickhouseDataSource(clickHouseProperties.nativeJdbcUrl(), settings);
    }
}
