package com.poo0054.redisson.test;

import com.poo0054.redisson.BaseTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;

/**
 * @author zhangzhi
 * @date 2023/3/3
 */
public class RedissonBaseTest extends BaseTest {

    @Test
    void test() {
        RLock lock = redisson.getLock("lock");
        try {
            Thread.sleep(1000);
            lock.tryLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
