/**
 * Copyright (c) 2013-2022 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.config;

/**
 * @author Nikita Koksharov
 */
public class SingleServerConfig extends BaseConfig<SingleServerConfig> {

    /**
     * Redis server address
     */
    private String address;

    /**
     * Minimum idle subscription connection amount
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * Redis subscription connection maximum pool size
     */
    private int subscriptionConnectionPoolSize = 50;

    /**
     * Minimum idle Redis connection amount
     */
    private int connectionMinimumIdleSize = 24;

    /**
     * Redis connection maximum pool size
     */
    private int connectionPoolSize = 64;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    /**
     * Interval in milliseconds to check DNS
     */
    private long dnsMonitoringInterval = 5000;

    SingleServerConfig() {
    }

    SingleServerConfig(SingleServerConfig config) {
        super(config);
        //设置服务器地址。使用跟随格式 -- 主机: 端口
        setAddress(config.getAddress());
        //Redis连接池大小 - 64
        setConnectionPoolSize(config.getConnectionPoolSize());
        // Redis订阅-连接池大小限制 - 50
        setSubscriptionConnectionPoolSize(config.getSubscriptionConnectionPoolSize());
        // 检查端点的DNS的间隔 (以毫秒为单位) - 5000
        setDnsMonitoringInterval(config.getDnsMonitoringInterval());
        //最小空闲订阅连接金额。 - 1
        setSubscriptionConnectionMinimumIdleSize(config.getSubscriptionConnectionMinimumIdleSize());
        // 最小空闲Redis连接量。
        setConnectionMinimumIdleSize(config.getConnectionMinimumIdleSize());
        //数据库
        setDatabase(config.getDatabase());
    }

    /**
     * Redis connection pool size
     * <p>
     * Default is <code>64</code>
     *
     * @param connectionPoolSize - pool size
     * @return config
     */
    public SingleServerConfig setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
        return this;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    /**
     * Redis subscription-connection pool size limit
     * <p>
     * Default is <code>50</code>
     *
     * @param subscriptionConnectionPoolSize - pool size
     * @return config
     */
    public SingleServerConfig setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        return this;
    }

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    /**
     * Set server address. Use follow format -- host:port
     *
     * @param address of Redis
     * @return config
     */
    public SingleServerConfig setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
        return this;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Interval in milliseconds to check the endpoint's DNS<p>
     * Applications must ensure the JVM DNS cache TTL is low enough to support this.<p>
     * Set <code>-1</code> to disable.
     * <p>
     * Default is <code>5000</code>.
     *
     * @param dnsMonitoringInterval time
     * @return config
     */
    public SingleServerConfig setDnsMonitoringInterval(long dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
        return this;
    }

    public long getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }

    /**
     * Minimum idle subscription connection amount.
     * <p>
     * Default is <code>1</code>
     *
     * @param subscriptionConnectionMinimumIdleSize - connections amount
     * @return config
     */
    public SingleServerConfig setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        return this;
    }

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    /**
     * Minimum idle Redis connection amount.
     * <p>
     * Default is <code>24</code>
     *
     * @param connectionMinimumIdleSize - connections amount
     * @return config
     */
    public SingleServerConfig setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        return this;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    /**
     * Database index used for Redis connection
     * Default is <code>0</code>
     *
     * @param database index
     * @return config
     */
    public SingleServerConfig setDatabase(int database) {
        this.database = database;
        return this;
    }

    public int getDatabase() {
        return database;
    }

}
