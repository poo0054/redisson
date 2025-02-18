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

import org.redisson.api.NameMapper;
import org.redisson.client.DefaultCredentialsResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @param <T> config type
 * @author Nikita Koksharov
 */
public class BaseConfig<T extends BaseConfig<T>> {

    private static final Logger log = LoggerFactory.getLogger("config");

    /**
     * If pooled connection not used for a <code>timeout</code> time
     * and current connections amount bigger than minimum idle connections pool size,
     * then it will closed and removed from pool.
     * Value in milliseconds.
     */
    private int idleConnectionTimeout = 10000;

    /**
     * Timeout during connecting to any Redis server.
     * Value in milliseconds.
     */
    private int connectTimeout = 10000;

    /**
     * Redis server response timeout. Starts to countdown when Redis command was succesfully sent.
     * Value in milliseconds.
     */
    private int timeout = 3000;

    private int retryAttempts = 3;

    private int retryInterval = 1500;

    /**
     * Password for Redis authentication. Should be null if not needed
     */
    private String password;

    private String username;

    private CredentialsResolver credentialsResolver = new DefaultCredentialsResolver();

    /**
     * Subscriptions per Redis connection limit
     */
    private int subscriptionsPerConnection = 5;

    /**
     * Name of client connection
     */
    private String clientName;

    private boolean sslEnableEndpointIdentification = true;

    private SslProvider sslProvider = SslProvider.JDK;

    private URL sslTruststore;

    private String sslTruststorePassword;

    private URL sslKeystore;

    private String sslKeystorePassword;

    private String[] sslProtocols;

    private int pingConnectionInterval = 30000;

    private boolean keepAlive;

    private boolean tcpNoDelay = true;

    private NameMapper nameMapper = NameMapper.direct();


    BaseConfig() {
    }

    BaseConfig(T config) {
        //账号密码
        setPassword(config.getPassword());
        setUsername(config.getUsername());
        //互联网订阅 - 5
        setSubscriptionsPerConnection(config.getSubscriptionsPerConnection());
        //  重试 - 3
        setRetryAttempts(config.getRetryAttempts());
        // 重试间隔 - 1500
        setRetryInterval(config.getRetryInterval());
        // Redis服务器响应超时。成功发送Redis命令后开始倒计时。以毫秒为单位的值。 - 3000
        setTimeout(config.getTimeout());
        //客户端链接名称
        setClientName(config.getClientName());
        // 连接到任何Redis服务器时超时。以毫秒为单位的值。 - 10000
        setConnectTimeout(config.getConnectTimeout());
        // 如果在超时时间内未使用池连接，并且当前连接数大于最小空闲连接池大小，则它将关闭并从池中删除。以毫秒为单位的值。 - 10000
        setIdleConnectionTimeout(config.getIdleConnectionTimeout());
        // sslEnableEndpointIdentification  开启ssl端口识别  - true
        setSslEnableEndpointIdentification(config.isSslEnableEndpointIdentification());
        // 使用什么来处理ssl链接 OPENSSL或者jdk
        setSslProvider(config.getSslProvider());
        //定义SSL信任存储的路径
        setSslTruststore(config.getSslTruststore());
        //为SSL信任存储定义密码。每次创建新连接时都会读取SSL truststore，并且可以动态重新加载。
        setSslTruststorePassword(config.getSslTruststorePassword());
        //定义SSL密钥库的路径。在每次创建新连接时都会读取SSL密钥库，并且可以动态重新加载。
        setSslKeystore(config.getSslKeystore());
        //定义SSL密钥库的密码
        setSslKeystorePassword(config.getSslKeystorePassword());
        // 定义SSL协议。示例值: TLSv1.3、TLSv1.2、TLSv1.1、TLSv1
        setSslProtocols(config.getSslProtocols());
        //Ping连接间隔 30000
        setPingConnectionInterval(config.getPingConnectionInterval());
        //为连接启用TCP保持活动
        setKeepAlive(config.isKeepAlive());
        //启用连接的TCP noDelay
        setTcpNoDelay(config.isTcpNoDelay());
        //定义映射对象名称的名称映射器。应用于所有Redisson对象。
        setNameMapper(config.getNameMapper());
        //定义在连接期间为Redis服务器身份验证调用的凭据解析器。它可以指定动态更改的Redis凭据。
        setCredentialsResolver(config.getCredentialsResolver());
    }

    /**
     * Subscriptions per Redis connection limit
     * <p>
     * Default is <code>5</code>
     *
     * @param subscriptionsPerConnection amount
     * @return config
     */
    public T setSubscriptionsPerConnection(int subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
        return (T) this;
    }

    public int getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }

    /**
     * Password for Redis authentication. Should be null if not needed.
     * <p>
     * Default is <code>null</code>
     *
     * @param password for connection
     * @return config
     */
    public T setPassword(String password) {
        this.password = password;
        return (T) this;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Username for Redis authentication. Should be null if not needed
     * <p>
     * Default is <code>null</code>
     * <p>
     * Requires Redis 6.0+
     *
     * @param username for connection
     * @return config
     */
    public T setUsername(String username) {
        this.username = username;
        return (T) this;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Error will be thrown if Redis command can't be sent to Redis server after <code>retryAttempts</code>.
     * But if it sent successfully then <code>timeout</code> will be started.
     * <p>
     * Default is <code>3</code> attempts
     *
     * @param retryAttempts - retry attempts
     * @return config
     * @see #timeout
     */
    public T setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
        return (T) this;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    /**
     * Defines time interval for another one attempt send Redis command
     * if it hasn't been sent already.
     * <p>
     * Default is <code>1500</code> milliseconds
     *
     * @param retryInterval - time in milliseconds
     * @return config
     */
    public T setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
        return (T) this;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    /**
     * Redis server response timeout. Starts to countdown when Redis command has been successfully sent.
     * <p>
     * Default is <code>3000</code> milliseconds
     *
     * @param timeout in milliseconds
     * @return config
     */
    public T setTimeout(int timeout) {
        this.timeout = timeout;
        return (T) this;
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * Setup connection name during connection init
     * via CLIENT SETNAME command
     * <p>
     * Default is <code>null</code>
     *
     * @param clientName - name of client
     * @return config
     */
    public T setClientName(String clientName) {
        this.clientName = clientName;
        return (T) this;
    }

    public String getClientName() {
        return clientName;
    }

    /**
     * Timeout during connecting to any Redis server.
     * <p>
     * Default is <code>10000</code> milliseconds.
     *
     * @param connectTimeout - timeout in milliseconds
     * @return config
     */
    public T setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (T) this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * If pooled connection not used for a <code>timeout</code> time
     * and current connections amount bigger than minimum idle connections pool size,
     * then it will closed and removed from pool.
     * <p>
     * Default is <code>10000</code> milliseconds.
     *
     * @param idleConnectionTimeout - timeout in milliseconds
     * @return config
     */
    public T setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
        return (T) this;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public boolean isSslEnableEndpointIdentification() {
        return sslEnableEndpointIdentification;
    }

    /**
     * Enables SSL endpoint identification.
     * <p>
     * Default is <code>true</code>
     *
     * @param sslEnableEndpointIdentification - boolean value
     * @return config
     */
    public T setSslEnableEndpointIdentification(boolean sslEnableEndpointIdentification) {
        this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
        return (T) this;
    }

    public SslProvider getSslProvider() {
        return sslProvider;
    }

    /**
     * Defines SSL provider used to handle SSL connections.
     * <p>
     * Default is <code>JDK</code>
     *
     * @param sslProvider - ssl provider
     * @return config
     */
    public T setSslProvider(SslProvider sslProvider) {
        this.sslProvider = sslProvider;
        return (T) this;
    }

    public URL getSslTruststore() {
        return sslTruststore;
    }

    /**
     * Defines path to SSL truststore
     * <p>
     * Default is <code>null</code>
     *
     * @param sslTruststore - path
     * @return config
     */
    public T setSslTruststore(URL sslTruststore) {
        this.sslTruststore = sslTruststore;
        return (T) this;
    }

    public String getSslTruststorePassword() {
        return sslTruststorePassword;
    }

    /**
     * Defines password for SSL truststore.
     * SSL truststore is read on each new connection creation and can be dynamically reloaded.
     * <p>
     * Default is <code>null</code>
     *
     * @param sslTruststorePassword - password
     * @return config
     */
    public T setSslTruststorePassword(String sslTruststorePassword) {
        this.sslTruststorePassword = sslTruststorePassword;
        return (T) this;
    }

    public URL getSslKeystore() {
        return sslKeystore;
    }

    /**
     * Defines path to SSL keystore.
     * SSL keystore is read on each new connection creation and can be dynamically reloaded.
     * <p>
     * Default is <code>null</code>
     *
     * @param sslKeystore - path to keystore
     * @return config
     */
    public T setSslKeystore(URL sslKeystore) {
        this.sslKeystore = sslKeystore;
        return (T) this;
    }

    public String getSslKeystorePassword() {
        return sslKeystorePassword;
    }

    /**
     * Defines password for SSL keystore
     * <p>
     * Default is <code>null</code>
     *
     * @param sslKeystorePassword - password
     * @return config
     */
    public T setSslKeystorePassword(String sslKeystorePassword) {
        this.sslKeystorePassword = sslKeystorePassword;
        return (T) this;
    }

    public String[] getSslProtocols() {
        return sslProtocols;
    }

    /**
     * Defines SSL protocols.
     * Example values: TLSv1.3, TLSv1.2, TLSv1.1, TLSv1
     * <p>
     * Default is <code>null</code>
     *
     * @param sslProtocols - protocols
     * @return config
     */
    public T setSslProtocols(String[] sslProtocols) {
        this.sslProtocols = sslProtocols;
        return (T) this;
    }

    public int getPingConnectionInterval() {
        return pingConnectionInterval;
    }

    /**
     * Defines PING command sending interval per connection to Redis.
     * <code>0</code> means disable.
     * <p>
     * Default is <code>30000</code>
     *
     * @param pingConnectionInterval - time in milliseconds
     * @return config
     */
    public T setPingConnectionInterval(int pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
        return (T) this;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    /**
     * Enables TCP keepAlive for connection
     * <p>
     * Default is <code>false</code>
     *
     * @param keepAlive - boolean value
     * @return config
     */
    public T setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return (T) this;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * Enables TCP noDelay for connection
     * <p>
     * Default is <code>true</code>
     *
     * @param tcpNoDelay - boolean value
     * @return config
     */
    public T setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
        return (T) this;
    }


    public NameMapper getNameMapper() {
        return nameMapper;
    }

    /**
     * Defines Name mapper which maps Redisson object name.
     * Applied to all Redisson objects.
     *
     * @param nameMapper - name mapper object
     * @return config
     */
    public T setNameMapper(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
        return (T) this;
    }

    public CredentialsResolver getCredentialsResolver() {
        return credentialsResolver;
    }

    /**
     * Defines Credentials resolver which is invoked during connection for Redis server authentication.
     * It makes possible to specify dynamically changing Redis credentials.
     *
     * @param credentialsResolver Credentials resolver object
     * @return config
     */
    public T setCredentialsResolver(CredentialsResolver credentialsResolver) {
        this.credentialsResolver = credentialsResolver;
        return (T) this;
    }
}
