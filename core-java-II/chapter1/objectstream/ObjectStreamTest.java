package chapter1.ObjectStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ObjectStreamTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];

        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.data"));
            out.writeObject(staff);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.data"));
            Employee[] newStaff = (Employee[]) in.readObject();
            in.close();

            newStaff[1].raiseSalary(10);
            
            for (Employee e : newStaff)
                System.out.println(e);

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

class Employee implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9054936701613839181L;

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

    private String name;
    private double salary;
    private Date hireDay;

}

class Manager extends Employee {

    /**
     * 
     */
    private static final long serialVersionUID = -3181382114530999870L;

    public Manager(String n, double s, int year, int month, int day) {
        super(n, s, year, month, day);
        secretary = null;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return super.toString() + "[secretary=" + secretary + "]";
    }

    private Employee secretary;

}