package com.smartmeter.patterns.factory;

public class PaymentFactory {

    public static PaymentMethod create(String type) {

        switch (type.toLowerCase()) {
            case "visa":
                return new VisaPayment();

            case "paypal":
                return new PayPalPayment();

            case "libipay":
                return new LibiPayPayment();

            case "mobicash":
                return new MobiCashPayment();

            default:
                throw new IllegalArgumentException("Invalid payment method: " + type);
        }
    }
}
