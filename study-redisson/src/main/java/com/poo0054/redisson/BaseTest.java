package com.poo0054.redisson;

import org.junit.After;
import org.junit.Before;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author poo00
 */
public abstract class BaseTest {

    protected static RedissonClient redisson;

    public static RedissonClient createInstance() {
        Config config = createConfig();
        return Redisson.create(config);
    }

    @Before
    public void beforeClass() {
        redisson = createInstance();
    }

    @After
    public void afterClass() {
        redisson.shutdown();
    }

    public static Config createConfig() {

//        String redisAddress = System.getProperty("redisAddress");
//        if (redisAddress == null) {
//            redisAddress = "127.0.0.1:6379";
//        }
        Config config = new Config();
//        config.setCodec(new MsgPackJacksonCodec());
//        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26379", "127.0.0.1:26389");
//        config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001", "127.0.0.1:7000");
        config.useSingleServer()
                .setAddress("redis://192.168.56.10:6379");
//        .setPassword("mypass1");
//        config.useMasterSlaveConnection()
//        .setMasterAddress("127.0.0.1:6379")
//        .addSlaveAddress("127.0.0.1:6399")
//        .addSlaveAddress("127.0.0.1:6389");
        return config;
    }


    public void before() {
        if (flushBetweenTests()) {
            redisson.getKeys().flushall();
        }
    }

    protected boolean flushBetweenTests() {
        return true;
    }
}
