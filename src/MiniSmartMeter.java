
import com.smartmeter.controller.AdminController;
import com.smartmeter.controller.UserController;
import com.smartmeter.patterns.observer.LogObserver;
import com.smartmeter.patterns.observer.LogSubject;
import com.smartmeter.view.MainView;

public class MiniSmartMeter {

    public static void main(String[] args) {
        LogSubject logSubject = LogSubject.getInstance();
        logSubject.attach(new LogObserver());
        MainView view = new MainView();

        while (true) {
            view.showMainMenu();
            int c = view.readChoice();

            switch (c) {
                case 1:
                    new UserController().start();
                case 2:
                    new AdminController().start();

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
