package com.smartmeter.patterns.facade;

import com.smartmeter.dao.impl.BillDAOImpl;
import com.smartmeter.model.Bill;

public class BillingFacade {

    private final BillDAOImpl billDAO = new BillDAOImpl();

    public Bill getCurrentBill(int userId) {
        return billDAO.getUnpaidBillByUser(userId);
    }

    public boolean hasUnpaidBill(int userId) {
        return getCurrentBill(userId) != null;
    }
}
