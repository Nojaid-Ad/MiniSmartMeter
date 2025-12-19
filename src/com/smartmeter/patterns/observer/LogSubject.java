package com.smartmeter.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class LogSubject {

    private static final LogSubject INSTANCE = new LogSubject();
    private final List<Observer> observers = new ArrayList<>();

    private LogSubject() {
    }

    public static LogSubject getInstance() {
        return INSTANCE;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message, Integer userId) {
        for (Observer o : observers) {
            o.update(message, userId);
        }
    }
}
