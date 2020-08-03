package com.senla.multithreading.task1.runnable;

import com.senla.multithreading.common.exception.ThreadException;

// название класса не говорит ни о чем, по этой логике
// любой класс в проекте, который имплементит интерфейс раннабл,
// можно назвать RunnableImpl
// это как если бы завод выпустил машину с названием Машина, модель Модель
public class RunnableImpl implements Runnable {
    private final Object synchronizationObject;

    public RunnableImpl(Object object) {
        this.synchronizationObject = object;
    }

    @Override
    public void run() {
        waitLiberation();
        waitLiberationByTime();
    }

    private void waitLiberation() {
        // напоминаю, что кроме блоков synchronized есть еще методы synchronized
        synchronized (synchronizationObject) {
            try {
                synchronizationObject.wait();
            } catch (Exception e) {
                throw new ThreadException("Error wait thread");
            }
        }
    }

    private void waitLiberationByTime() {
        synchronized (synchronizationObject) {
            try {
                synchronizationObject.wait(1);
            } catch (Exception e) {
                throw new ThreadException("Error wait thread");
            }
        }
    }
}