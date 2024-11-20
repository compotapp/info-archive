package com.pot.app.infoarchive.testUtil;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.setProperty;
import static org.testcontainers.containers.ClickHouseContainer.NATIVE_PORT;

@Slf4j
public class ClickHouseExtension implements BeforeAllCallback, CloseableResource {

    public static final Network CH_NETWORK = Network.newNetwork();
    private static final Lock LOCK = new ReentrantLock();
    private static final AtomicBoolean STARTED = new AtomicBoolean(false);
    private static final String DOCKER_IMAGE_NAME = "clickhouse/clickhouse-server:22.10";
    private static final ClickHouseContainer CLICK_HOUSE =
            new ClickHouseContainer(DOCKER_IMAGE_NAME)
                    .withNetwork(CH_NETWORK)
                    .withNetworkAliases("CH")
                    .withEnv("CLICKHOUSE_DEFAULT_ACCESS_MANAGEMENT", "1")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("clickhouse-config.yaml"),
                            "/etc/clickhouse-server/config.d/config.yaml");

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        LOCK.lock();
        try {
            log.info("start ch container");
            Startables.deepStart(CLICK_HOUSE).join();
            setProperty(
                    "clickhouse.native-jdbc-url",
                    "jdbc:clickhouse://" + CLICK_HOUSE.getHost() + ":" + CLICK_HOUSE.getMappedPort(NATIVE_PORT) + "/default"
            );
            setProperty("clickhouse.jdbc-url", CLICK_HOUSE.getJdbcUrl());
            setProperty("clickhouse.username", CLICK_HOUSE.getUsername());
            setProperty("clickhouse.password", CLICK_HOUSE.getPassword());

            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("ch container", this);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void close() throws Throwable {
        log.info("close ch container");
        CLICK_HOUSE.close();
    }
}





















