package test.java.com;

import java.util.Date;

/**
 * Created by Mihan.Liyanage on 12/30/2014.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private int age;
    private Date joinDate;


    public Employee(String firstName, String lastName, int age, Date joinDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.joinDate = joinDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Date getJoinDate() {
        return joinDate;
    }

}
