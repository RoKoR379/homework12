package homework12;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class task2 {
    private final static int n = 20;
    private static boolean notTheEnd = true;
    public static volatile AtomicInteger number = new AtomicInteger(1);
    public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    public synchronized void fizz() {
        while (number.get() < n) {
            if (number.get() % 3 == 0 && number.get() % 5 != 0) {
                queue.add("fizz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void buzz() {
        while (number.get() < n) {
            if (number.get() % 3 != 0 && number.get() % 5 == 0) {
                queue.add("buzz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void fizzbuzz () {
        while (number.get() < n) {
            if (number.get() % 3 == 0 && number.get() % 5 == 0) {
                queue.add("fizzbuzz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public synchronized void number() {
        while (number.get() < n) {
            if (number.get() % 3 != 0 && number.get() % 5 != 0) {
                queue.add(String.valueOf(number));
                number.incrementAndGet();
                notifyAll();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


        public static void main(String[] args) {

            ExecutorService service = Executors.newFixedThreadPool(5);

            task2 fizzBuzzService = new task2();
            service.submit(fizzBuzzService::fizz);
            service.submit(fizzBuzzService::buzz);
            service.submit(fizzBuzzService::fizzbuzz);
            service.submit(fizzBuzzService::number);

            service.submit(() -> {
                while (notTheEnd) {
                    if(queue.isEmpty()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    while (!queue.isEmpty()) {
                        System.out.println(queue.poll());
                    }
                    if(queue.isEmpty() && number.get() == n) notTheEnd = false;
                }
            });

            service.shutdown();
        }
    }

