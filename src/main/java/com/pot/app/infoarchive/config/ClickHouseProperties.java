package com.pot.app.infoarchive.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

@Validated
@ConfigurationPropertiesScan("clickhouse")
public record ClickHouseProperties(

        @NotNull
        String jdbcUrl,
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        String nativeJdbcUrl,
        @NotNull
        ClientType clientType,
        @NotNull
        DriverSettings driverSettings
) {
    public ClickHouseProperties {

        final var splitNativeJdbcUrl = nativeJdbcUrl.split("\\?", 2);
        if (splitNativeJdbcUrl.length == 2) {
            splitNativeJdbcUrl[1] = splitNativeJdbcUrl[1].replace("-|\\.","_");
            nativeJdbcUrl = String.join("?", splitNativeJdbcUrl);
        }
    }

    public enum ClientType{
        JDBC,
        NATIVE_JDBC
    }

    public record DriverSettings(
            @NotNull
            Properties jdbc
    ) {

    }
}
