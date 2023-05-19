/**
 * Copyright (c) 2013-2022 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.connection;

import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.ReadMode;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.SubscriptionMode;

/**
 * 
 * @author Nikita Koksharov
 *
 */
public class SingleConnectionManager extends MasterSlaveConnectionManager {

    public SingleConnectionManager(SingleServerConfig cfg, ServiceManager serviceManager) {
        super(create(cfg), serviceManager);
    }

    private static MasterSlaveServersConfig create(SingleServerConfig cfg) {
        MasterSlaveServersConfig newconfig = new MasterSlaveServersConfig();

        //定义PING命令向Redis的每个连接发送间隔。0表示禁用。  - 30000
        newconfig.setPingConnectionInterval(cfg.getPingConnectionInterval());
        //启用SSL端点标识。
        newconfig.setSslEnableEndpointIdentification(cfg.isSslEnableEndpointIdentification());
        newconfig.setSslProvider(cfg.getSslProvider());
        newconfig.setSslTruststore(cfg.getSslTruststore());
        newconfig.setSslTruststorePassword(cfg.getSslTruststorePassword());
        newconfig.setSslKeystore(cfg.getSslKeystore());
        newconfig.setSslKeystorePassword(cfg.getSslKeystorePassword());
        newconfig.setSslProtocols(cfg.getSslProtocols());

        //如果retrytrists后无法将Redis命令发送到Redis服务器，则会引发错误。但如果发送成功，则将启动超时。  默认为3尝试
        newconfig.setRetryAttempts(cfg.getRetryAttempts());
        //定义另一个尝试发送Redis命令 (如果尚未发送) 的时间间隔。 -  1500
        newconfig.setRetryInterval(cfg.getRetryInterval());
        //Redis服务器响应超时。当Redis命令已成功发送时开始倒计时。
        newconfig.setTimeout(cfg.getTimeout());
        newconfig.setPassword(cfg.getPassword());
        newconfig.setUsername(cfg.getUsername());
        newconfig.setDatabase(cfg.getDatabase());

        //通过客户端SETNAME命令在连接init期间设置连接名称
        newconfig.setClientName(cfg.getClientName());
        //设置Redis主服务器地址。使用跟随格式 -- 主机: 端口
        newconfig.setMasterAddress(cfg.getAddress());
        //Redis '主' 服务器连接池大小
        newconfig.setMasterConnectionPoolSize(cfg.getConnectionPoolSize());
        //每个Redis连接限制的订阅
        newconfig.setSubscriptionsPerConnection(cfg.getSubscriptionsPerConnection());
        //订阅 (发布/子) 频道的最大连接池大小
        newconfig.setSubscriptionConnectionPoolSize(cfg.getSubscriptionConnectionPoolSize());
        //连接到任何Redis服务器时超时。
        newconfig.setConnectTimeout(cfg.getConnectTimeout());
        //如果在超时时间内未使用池化连接，并且当前连接数大于最小空闲连接池大小，则它将关闭并从池中删除。
        newconfig.setIdleConnectionTimeout(cfg.getIdleConnectionTimeout());
        //检查端点的DNS的间隔 (以毫秒为单位)
        //应用程序必须确保JVM DNS缓存TTL足够低以支持此功能。
        newconfig.setDnsMonitoringInterval(cfg.getDnsMonitoringInterval());

        //Redis 'master' 节点每个从节点的最小空闲连接量
        newconfig.setMasterConnectionMinimumIdleSize(cfg.getConnectionMinimumIdleSize());
        //每个从节点的Redis 'slave' 节点最小空闲订阅 (pub/sub) 连接量。
        newconfig.setSubscriptionConnectionMinimumIdleSize(cfg.getSubscriptionConnectionMinimumIdleSize());
        //设置用于读取操作的节点类型
        newconfig.setReadMode(ReadMode.MASTER);
        //设置用于订阅操作的节点类型。
        newconfig.setSubscriptionMode(SubscriptionMode.MASTER);
        //为连接启用TCP保持活动
        newconfig.setKeepAlive(cfg.isKeepAlive());
        //启用连接的TCP noDelay
        newconfig.setTcpNoDelay(cfg.isTcpNoDelay());
        newconfig.setNameMapper(cfg.getNameMapper());
        //定义在连接期间为Redis服务器身份验证调用的凭据解析器。它可以指定动态更改的Redis凭据。
        newconfig.setCredentialsResolver(cfg.getCredentialsResolver());
        
        return newconfig;
    }

}
