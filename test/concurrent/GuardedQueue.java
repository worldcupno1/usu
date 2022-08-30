package concurrent;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class GuardedQueue {
    private final Queue<Integer> sourceList;

    public GuardedQueue() {
        this.sourceList = new LinkedBlockingQueue<>();
    }

    public synchronized Integer get() {
        while (sourceList.isEmpty()) {
            try {
//                System.out.println("开始等待了");
                wait();    // <--- 如果队列为null，等待
//                System.out.println("结束等待了");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sourceList.poll();
    }

    public synchronized void put(Integer e) {
        sourceList.add(e);
//        System.out.println("通知继续执行");
        notifyAll();  //<--- 通知，继续执行    }
    }

    public static void main(String[] args) throws InterruptedException {

        GuardedQueue guardedQueue = new GuardedQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable runnable = () -> {
            Integer i = guardedQueue.get();
            System.out.println(i);
        };
        executorService.execute(runnable);
        executorService.execute(runnable);

        executorService.execute(() -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        guardedQueue.put(i);

                    }
                }
        );

    }

}