package chapter1.TextFile;

import org.omg.CORBA.PUBLIC_MEMBER;
import sun.management.counter.perf.PerfInstrumentation;

import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ZHEN on 4/29/2016.
 */
public class TextFileTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 80000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        //try (PrintWriter out = new PrintWriter("../../resource/employee.dat", "utf-8")) {
        final String path = "./target/production/books-resource-code/resource/";
//        try(PrintWriter out = new PrintWriter(path+"employee.dat", "utf-8")) {
//            writeData(staff, out);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        try (Scanner in = new Scanner(new FileInputStream(path + "employee.dat"), "utf-8")) {
            Employee[] newStaff = readData(in);
            for (Employee ee : newStaff) {
                System.out.println(ee.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void writeData(Employee[] employees, PrintWriter out) {
        out.println(employees.length);
        for (Employee ee : employees) {
            ee.writeEmployee(out);
        }
    }

    public static Employee[] readData(Scanner in) {
        int n = in.nextInt();
        in.nextLine();

        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    public static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[3]) - 1;
        int day = Integer.parseInt(tokens[4]);
        return new Employee(name, salary, year, month, day);
    }
}


class Employee {
    public Employee() {
    }

    public Employee(String name, double salary, int year, int month, int day) {
        super();
        this.name = name;
        this.salary = salary;
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(year, month - 1, day);
        this.hireDay = cal.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", salary=" + salary + ", hireDay=" + hireDay + "]";
    }

    public void writeEmployee(PrintWriter out) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(hireDay);
        out.println(name + "|" + salary + "|" + cal.get(Calendar.YEAR) + "|" + (cal.get(Calendar.MONTH) + 1) + "|" + cal.get(Calendar.DAY_OF_MONTH));
    }

    private String name;
    private double salary;
    private Date hireDay;

}