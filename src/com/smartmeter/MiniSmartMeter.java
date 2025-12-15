import com.smartmeter.patterns.strategy.*;
import com.smartmeter.service.BillingService;

public class MiniSmartMeter {

    public static void main(String[] args) {

        BillingService service = new BillingService();

        service.setStrategy(new NormalBillingStrategy());
        System.out.println(service.calculateBill(170));

        service.setStrategy(new PeakBillingStrategy());
        System.out.println(service.calculateBill(100));

        service.setStrategy(new WeekendBillingStrategy());
        System.out.println(service.calculateBill(100));
    }
}
