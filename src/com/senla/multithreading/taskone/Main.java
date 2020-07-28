package com.senla.multithreading.taskone;

public class Main {
    public static void main(String[] args) {
        try {
            Thread thread = new ThreadWaitingStateTest();
            System.out.println("1: " + thread.getState());
            thread.start();
            System.out.println("2: " + thread.getState());
            Thread.sleep(10);
            System.out.println("3: " + thread.getState());
            thread.join();
            System.out.println("4: " + thread.getState());
        } catch (InterruptedException e) {
            System.err.print("ошибка потока");
        }
    }
}
//        ObservedStream observedStream = new ObservedStream();
//        Thread thread = new Thread(observedStream);
//        System.out.println(thread.getState());
//        thread.start();
//        System.out.println(thread.getState());
//        Thread.yield();
//        System.out.println(thread.getState());
//        try {
//            thread.join();
//            System.out.println(thread.getState());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread.interrupt();
//        System.out.println(thread.getState());
//        System.out.println(thread.getState());
//    }
//}