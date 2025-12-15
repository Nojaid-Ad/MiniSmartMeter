package com.smartmeter.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class LogSubject implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message) {
        observers.forEach(o -> o.update(message));
    }
}
