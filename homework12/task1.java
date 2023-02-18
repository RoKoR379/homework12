package homework12;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class task1 {
    public static void main(String[] args) throws InterruptedException{
        Thread first = new Thread() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Time: " + java.time.LocalTime.now());
                }
            }
        };
        Thread second = new Thread() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.print("5 seconds ");
                }
            }
        };

        first.start();
        second.start();
    }




}
