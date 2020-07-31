// для учебных проектов имя пакета типа task1 - норм, я не против
package com.senla.multithreading.taskone;

// после запуска получил статусы:
/*
NEW
RUNNABLE
WAITING
BLOCKED
RUNNABLE
TERMINATED
 */
// не вижу таймед_вейтинг
public class Main {
    public static void main(String[] args) {
        Config.run();
    }
}