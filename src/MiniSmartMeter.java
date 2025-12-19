import com.smartmeter.controller.AdminController;
import com.smartmeter.controller.UserController;
import com.smartmeter.view.MainView;


public class MiniSmartMeter {

    public static void main(String[] args) {

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
