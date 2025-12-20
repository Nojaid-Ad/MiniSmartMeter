<h1 align="center">Mini Meter Reading and Billing System</h1>

<p align="center">
  <img 
    src="https://readme-typing-svg.herokuapp.com?size=36&duration=3000&color=1A81E2&center=true&vCenter=true&width=800&lines=Developed+By;Nojaid+Abdullah+Issa+Aummara;Ayoub+Ahfeeth+Aboulqasim+Abu+Isnaynah"
    alt="Developed By"
  />
</p>

## DESCRIPTION
This project is a Java Console application developed as part of the **Software Design Patterns** course.  
The system simulates a simplified electricity meter reading and billing environment where users can submit meter readings, generate bills, make payments, recharge balances, and submit service reports.  
The main focus of the project is to demonstrate the **practical application of software design patterns** in a small-scale real-world system.
Default admin:
username: admin
password: admin123
---

## REQUIREMENTS
- Java JDK 8 or higher  
- Any Java IDE (NetBeans recommended)  
- XAMPP (for MySQL database)  
- MySQL Server  

---

## HOW TO RUN THE PROJECT

1. **Set up the Database**
   - Open **XAMPP** and start **Apache** and **MySQL**.
   - Open **phpMyAdmin**.
   - Import the provided SQL file:
     - Open `schema.sql`
     - Import it.

2. **Configure Database Connection**
   - Open the project in your Java IDE.
   - Navigate to `DBConnection.java`.
   - Update database credentials if needed:
     - URL
     - Username
     - Password

3. **Run the Application**
   - Run the `MiniSmartMeter.java` file.
   - The console menu will appear.
   - Select your role (User or Admin) and follow the instructions.

---

## USER ROLES

### User
- Register and log in
- Submit meter readings
- Generate electricity bills
- Pay bills using different payment methods
- Recharge account balance
- Submit service or error reports

### Admin
- View and manage users
- Review meter readings
- Review submitted reports
- Monitor system logs

---

## FEATURES
- User authentication and role management  
- Meter reading submission  
- Electricity bill generation  
- Multiple billing calculation strategies (Normal, Peak, Weekend)  
- Multiple payment methods  
- Centralized logging system  
- Report submission and management  
- Console-based user interface  

---

## DESIGN PATTERNS USED

- **Singleton Pattern**  
  Used for managing the database connection and centralized logging subject.

- **Observer Pattern**  
  Used to implement the logging system where system events are recorded automatically.

- **Factory Pattern**  
  Used to create different payment method objects dynamically.

- **Strategy Pattern**  
  Used for flexible billing rate calculations (Normal, Peak, Weekend).

- **Template Method Pattern**  
  Used to define the standard workflow for bill generation while allowing variation in calculation steps.

- **Facade Pattern**  
  Used to simplify interactions between billing-related components.

---

## 📁 Project Structure

```text
MiniSmartMeter/
├── src/
│   └── com.smartmeter/
│       ├── controller/
│       │   ├── AdminController.java
│       │   ├── UserController.java
│       │   └── MiniSmartMeter.java
│       │
│       ├── dao/
│       │   ├── AdminDAO.java
│       │   ├── UserDAO.java
│       │   ├── BillDAO.java
│       │   ├── MeterReadingDAO.java
│       │   ├── PaymentDAO.java
│       │   └── ReportDAO.java
│       │
│       ├── dao/impl/
│       │   ├── AdminDAOImpl.java
│       │   ├── UserDAOImpl.java
│       │   ├── BillDAOImpl.java
│       │   ├── MeterReadingDAOImpl.java
│       │   ├── PaymentDAOImpl.java
│       │   └── ReportDAOImpl.java
│       │
│       ├── db/
│       │   └── DBConnection.java
│       │
│       ├── model/
│       │   ├── Admin.java
│       │   ├── User.java
│       │   ├── Bill.java
│       │   ├── MeterReading.java
│       │   └── Report.java
│       │
│       ├── service/
│       │   ├── AdminService.java
│       │   ├── UserService.java
│       │   ├── BillingService.java
│       │   ├── MeterReadingService.java
│       │   └── ReportService.java
│       │
│       ├── service/impl/
│       │   ├── AdminServiceImpl.java
│       │   ├── UserServiceImpl.java
│       │   ├── BillingServiceImpl.java
│       │   ├── MeterReadingServiceImpl.java
│       │   └── ReportServiceImpl.java
│       │
│       ├── patterns/
│       │   ├── singleton/
│       │   │   └── DBConnection.java
│       │   ├── observer/
│       │   │   ├── Subject.java
│       │   │   ├── Observer.java
│       │   │   ├── LogSubject.java
│       │   │   └── LogObserver.java
│       │   ├── factory/
│       │   │   ├── PaymentMethod.java
│       │   │   ├── PaymentFactory.java
│       │   │   ├── VisaPayment.java
│       │   │   ├── PayPalPayment.java
│       │   │   ├── LibiPayPayment.java
│       │   │   └── MobiCashPayment.java
│       │   ├── strategy/
│       │   │   ├── BillingStrategy.java
│       │   │   ├── BillingContext.java
│       │   │   ├── NormalBillingStrategy.java
│       │   │   ├── PeakBillingStrategy.java
│       │   │   └── WeekendBillingStrategy.java
│       │   ├── template/
│       │   │   ├── AbstractBillGenerator.java
│       │   │   ├── NormalBillGenerator.java
│       │   │   ├── PeakBillGenerator.java
│       │   │   └── WeekendBillGenerator.java
│       │   └── facade/
│       │       └── BillingFacade.java
│       │
│       ├── util/
│       │   └── BillTextGenerator.java
│       │
│       └── view/
│           ├── MainView.java
│           ├── AdminView.java
│           ├── UserView.java
│           ├── BillView.java
│           └── ReportView.java
│
├── schema.sql
└── README.md


```
---

## NOTES
- This project is a **simulation**, not a real production billing system.
- It is intended for **educational purposes only**.
- All interactions are performed through the console.
- Logs are automatically recorded using the Observer pattern.

---

**Course:** Software Design Patterns (ITSE424)  
**Faculty:** Faculty of Information Technology – Sabratha University  
**Semester:** Fall 2025  
**Submitted By:**  
- Nojaid Abdullah Issa Aummara  
- Ayoub Ahfeeth Aboulqasim Abu Isnaynah  

**Supervised By:** Dr. Mai Muftah Elbaabaa  
**Date:** December 2025
