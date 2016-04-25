package chapter1.RandomAccessFile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RandomAccessTest {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO Auto-generated method stub

        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.data"))) {
            for (Employee ee : staff) {
                // System.out.println(ee);
                ee.writeData(out);
            }
            out.close();
        }

        try (RandomAccessFile in = new RandomAccessFile("employee.data", "r")) {
            int n = (int) (in.length() / Employee.RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            for (int i = n - 1; i >= 0; i--) {
                newStaff[i] = new Employee();
                in.seek(i * Employee.RECORD_SIZE);
                newStaff[i].readData(in);
            }

            for (Employee ee : newStaff)
                System.out.println(ee);
        }

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

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    public void writeData(DataOutput out) throws IOException {
        DataIO.writeFixedString(name, NAME_SIZE, out);
        out.writeDouble(salary);

        Calendar cal = Calendar.getInstance();
        cal.setTime(hireDay);

        out.writeInt(cal.get(Calendar.YEAR));
        out.writeInt(cal.get(Calendar.MONDAY) + 1);
        out.writeInt(cal.get(Calendar.DAY_OF_MONTH));
    }

    public void readData(DataInput in) throws IOException {
        name = DataIO.readFixedString(NAME_SIZE, in);
        salary = in.readDouble();
        int y = in.readInt();
        int m = in.readInt();
        int d = in.readInt();
        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, d);
        hireDay = cal.getTime();
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", salary=" + salary + ", hireDay=" + hireDay + "]";
    }

    public static final int NAME_SIZE = 40;
    public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 * 3;

    private String name;
    private double salary;
    private Date hireDay;

}

class DataIO {

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length())
                ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in) throws IOException {

        StringBuilder b = new StringBuilder(size);

        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0)
                more = false;
            else
                b.append(ch);
        }
        in.skipBytes(2 * (size - i));
        return b.toString();
    }

}
