package com.smartmeter.view;

import com.smartmeter.model.Bill;

public class BillView {

    public void showCurrentBill(Bill bill) {

        System.out.println("""
________ Current Bill _________
Consumption | %s kWh         
------------|------------------
Amount      | %s LYD         
------------|------------------
Status      | %s                
-------------------------------
    """.formatted(
                bill.getConsumption(),
                bill.getAmount(),
                bill.getStatus()
        ));
    }
}
