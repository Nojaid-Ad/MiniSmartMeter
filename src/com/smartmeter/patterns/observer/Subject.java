package com.smartmeter.patterns.observer;

public interface Subject {

    void attach(Observer observer);

    void notifyObservers(String message, Integer userId);
}
