package concurrent;

import java.util.concurrent.CountDownLatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Title: 并发简单测试 <br>
 * Description: <br>
 * Date: 2021/12/7 14:59<br>
 * Copyright (c) <br>
 *
 * @author lvm
 */
public class TestCase {
  private static final Logger log = LogManager.getLogger(TestCase.class);

  public static void main(String[] args) throws InterruptedException {
    //
    Config config = new Config();
    String redisUrl = String.format("redis://%s:%s", "127.0.0.1", "6379");
    config.useSingleServer().setAddress(redisUrl);
    config.useSingleServer().setDatabase(3);
    RedissonClient redisson = Redisson.create(config);

    Runnable taskTemp =
        new Runnable() {
          // 注意，此处是非线程安全的，留坑
          private int iCounter;

          @Override
          public void run() {
            log.info(Thread.currentThread().getName());
              //
              //              iCounter++;
              //              System.out.println(
              //                  System.nanoTime()
              //                      + " ["
              //                      + Thread.currentThread().getName()
              //                      + "] iCounter = "
              //                      + iCounter);
              RLock lock = redisson.getLock("uploadFileLock");
              while (!lock.isLocked()) {
                lock.lock();
                try {
                  RBucket<String> bucket = redisson.getBucket("anyObject");
                  if (StringUtils.isNotBlank(bucket.get())) {
                    bucket.set(bucket.get() + "我是第" + Thread.currentThread().getName() + "号线程;");
                  } else {
                    bucket.set("我是第" + Thread.currentThread().getName() + "号线程;");
                  }
                  Thread.sleep(100);
                } catch (InterruptedException e) {
                  log.error(e.getMessage());
                } finally {
                  lock.unlock();
                  break;
                }
              }

          }
        };

    TestCase testCase = new TestCase();
    testCase.startTaskAllInOnce(5, taskTemp);

    RBucket<String> result = redisson.getBucket("anyObject");
    log.info("结果是:" + result.get());
    System.exit(0);
  }

  public long startTaskAllInOnce(int threadNums, final Runnable task) throws InterruptedException {
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(threadNums);
    for (int i = 0; i < threadNums; i++) {
      Thread t =
          new Thread(() -> {
            try {
              // 使线程在此等待，当开始门打开时，一起涌入门中
              startGate.await();
              try {
                task.run();
              } finally {
                // 将结束门减1，减到0时，就可以开启结束门了
                endGate.countDown();
              }
            } catch (InterruptedException ie) {
              log.error(ie.getMessage());
            }
          });
      t.start();
    }
    long startTime = System.nanoTime();
    System.out.println(
        startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
    // 因开启门只需一个开关，所以立马就开启开始门
    startGate.countDown();
    // 等等结束门开启
    endGate.await();
    long endTime = System.nanoTime();
    System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
    return endTime - startTime;
  }
}
