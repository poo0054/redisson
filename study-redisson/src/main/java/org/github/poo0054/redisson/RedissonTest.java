package org.github.poo0054.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author poo00
 */
@Slf4j
public class RedissonTest {

//    public static Logger log = LoggerFactory.getLogger(RedissonTest.class);

    public static void main(String[] args) throws InterruptedException {
        RedissonClient redissonClient = createInstance();
        RLock lock = redissonClient.getLock("name-lock");
        try {
            if (lock.tryLock(1000, 1000, TimeUnit.SECONDS)) {
                log.info("我锁上了");
            }
        } finally {
            lock.unlock();
        }

        lock.lock();
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

    public static RedissonClient createInstance() {
        Config config = createConfig();
        //创建 Redisson
        return Redisson.create(config);
    }

    @Test
    void test() throws InterruptedException {
        RedissonClient instance = createInstance();
        RLock lock = instance.getLock("name::lock");
        try {
            System.out.println("我来了.准备上锁");
            if (lock.tryLock(1000, 1000, TimeUnit.SECONDS)) {
                log.info("我锁上了");
            }
        } finally {
            lock.unlock();
        }
    }
}