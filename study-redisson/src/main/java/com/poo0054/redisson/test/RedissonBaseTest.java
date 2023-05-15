package com.poo0054.redisson.test;

import com.poo0054.redisson.BaseTest;
import org.junit.Test;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangzhi
 * @date 2023/3/3
 */
public class RedissonBaseTest extends BaseTest {

    @Test
    public void test() {
        //获取锁
        RLock lock = redisson.getLock("lock");
        try {
            Thread.sleep(1000);
            //上锁
            if (lock.tryLock(100, 100, TimeUnit.SECONDS)) {
                //上锁成功
                System.out.println("当前线程id : " + Thread.currentThread().getId());
            }
            //锁失败
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
