package chapter1.SerialClone;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ZHEN on 4/25/2016.
 */
public class SerialCloneTest {
    public static void main(String[] args) {
        Employee harry = new Employee("Harry Hacker", 35000, 1989, 10, 1);
        //  Clone Harry
        Employee harry2 = (Employee) harry.clone();
        harry2.raiseSalary(10);

        System.out.println(harry);
        System.out.println(harry2);
    }
}

class SerialCloneable implements Cloneable, Serializable {
    @Override
    protected Object clone() {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(this);
            out.close();

            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            Object ret = in.readObject();
            in.close();

            return ret;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Employee extends SerialCloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(year, month, day);
        this.hireDay = cal.getTime();
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double percent) {
        salary += salary * percent / 100;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}