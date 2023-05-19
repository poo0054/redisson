package org.github.poo0054.redisson;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

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
    void runAsync() throws ExecutionException, InterruptedException {
        CompletionStage<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("我开始睡");
            LockSupport.parkNanos(1000);
            System.out.println("我睡够了");
            throw new RuntimeException("我是一个参数错误");

        });
        CompletionStage<String> thenCompose = runAsync.thenCompose(unused -> {
            System.out.println("我是 thenCompose");
            return CompletableFuture.supplyAsync(() -> "你好");
        });

        CompletionStage<String> thenApply = thenCompose.thenApply(s -> {
            System.out.println(s + ",我是thenapply");
            return s + ",我是thenapply";
        });
        //..........................等..................

    /*    //exceptionally 和 handle 优先级是谁在前面
        CompletionStage<String> exceptionally = thenApply.exceptionally(throwable -> {
            System.out.println("哈哈,我exceptionally报错了" + throwable.getMessage());
            return "";
        });

        CompletionStage<String> handle = exceptionally.handle((s, throwable) -> {
            if (null != throwable) {
                System.out.println("哈哈,我handle报错了" + throwable.getMessage());
                return null;
            }
            return s;
        });*/

        /*
        CompletionException 错误
        java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         */
        thenApply.toCompletableFuture().join();
        /*
        ExecutionException 错误
        java.util.concurrent.ExecutionException: java.lang.ArithmeticException: / by zero
         */
//        thenApply.toCompletableFuture().get();
        System.out.println("i is main ");
    }
}
