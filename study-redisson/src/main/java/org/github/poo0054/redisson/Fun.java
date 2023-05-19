package org.github.poo0054.redisson;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * 复习一下 CompletableFuture
 *
 * @author zhangzhi
 */
public class Fun {
    @Test
    void getAndJoin() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("我开始睡");
                Thread.sleep(3000);
                System.out.println("我睡够了");
                throw new RuntimeException("我是一个大异常");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        voidCompletableFuture.handle((unused, throwable) -> {
            System.out.println(throwable.getMessage());
            return unused;
        });
        voidCompletableFuture.join();
//        voidCompletableFuture.join();
        System.out.println("我来了");
    }

    @Test
    void runAsync() {
        CompletionStage<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("我开始睡");
                Thread.sleep(3000);
                System.out.println("我睡够了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
