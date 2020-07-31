package com.senla.multithreading.tasktwo.thread;

import com.senla.multithreading.tasktwo.exception.ThreadException;

public class Printer implements Runnable {
    private final Integer numberOfCycles;
    private final Integer sleepTime;

    public Printer(Integer numberOfCycles, Integer sleepTime) {
        this.numberOfCycles = numberOfCycles;
        this.sleepTime = sleepTime;
    }

    // такая реализация не гарантирует, что первый поток первым напишет свое имя (я запустил программу
    // несколько раз и в итоге получил
    /*
    Thread-1
    Thread-0
     */
    // почитай про вейт и нотифай, такая реализация не требует паузы через слип - слип делает
    // программу медленной
    @Override
    public synchronized void run() {
        // вынести в метод валидации и/или использовать валидацию в конструкторе
        // иначе до запуска мы не будем знать, что мы создали плохой объект
        if (sleepTime == null) {
            throw new ThreadException("Sleep time error");
        }
        if (numberOfCycles == null) {
            throw new ThreadException("Repeat number error");
        }
        for (int i = 0; i < numberOfCycles; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new ThreadException("Error thread sleep");
            }
        }
    }
}