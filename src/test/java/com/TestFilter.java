package test.java.com;

import main.java.com.filter.ApacheFilter;
import main.java.com.filter.Filter;
import main.java.com.filter.GoogleFilter;
import main.java.com.filter.JavaFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Mihan.Liyanage on 12/30/2014.
 */
public class TestFilter {

    private static final int MAX_OBJECTS = 10000;

    public ArrayList<Employee> employeeList = new ArrayList<Employee>();
    public Collection<Employee> result      = new ArrayList<Employee>();
    //public ArrayList<Employee> expected     = new ArrayList<Employee>();

    public Map<Method, Object> mappingList = new HashMap<Method, Object>();

    public Method firstName, lastName, age, dateOfJoin;
    long startTime, endTime;

    Date myDate = new Date(System.currentTimeMillis());
    Date oneDayBefore = new Date(myDate.getTime() - 10);
    Date oneday = new Date(myDate.getTime() - 1100);

    @BeforeMethod
    public void insertData(){
        String fName, lName;
        int ageofEmp, max=40, min=20;

        Employee employeeOne = new Employee("Mihan", "ABC", 30, oneDayBefore);
        Employee employeeTwo = new Employee("liyanage", "ABC", 20, oneDayBefore);
        Employee employeeThr = new Employee("cricket", "sd", 10, null);
        Employee employeeFou = new Employee("sun", "ABC", 30, oneday);

        Random rand = new Random();

        for(int i=1; i < MAX_OBJECTS ; i++){
            fName = RandomStringUtils.random(3, true, true);
            lName = RandomStringUtils.random(5, true, true);
            ageofEmp = rand.nextInt((max - min) + 1) + min;

            if(i==72)
                employeeList.add(employeeOne);
            else if(i==2564){
                employeeList.add(employeeTwo);
            }else if(i==8700) {
                employeeList.add(employeeThr);
            }else if(i==9000){
                employeeList.add(employeeFou);
            }
            else
                employeeList.add(new Employee(fName,lName,ageofEmp,oneday));
        }

//        expected.add(employeeOne);
//        expected.add(employeeTwo);
//        expected.add(employeeThr);
//        expected.add(employeeFou);

        try {
            firstName  = Employee.class.getMethod("getFirstName");
            lastName   = Employee.class.getMethod("getLastName");
            age        = Employee.class.getMethod("getAge");
            dateOfJoin = Employee.class.getMethod("getJoinDate");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        mappingList.put(dateOfJoin, oneday);
    }

    @Test(sequential = true)
    public void testJavaFilter(){

        Filter javaFilter= new JavaFilter();

        startTime = System.currentTimeMillis();
        result = javaFilter.find(employeeList, mappingList, false, age);
        endTime = System.currentTimeMillis();

        System.out.println("Java result = " + (double)(endTime-startTime)/1000);
    }

    @Test(sequential = true)
    public void testGoogleFilter(){

        Filter googleFilter= new GoogleFilter();

        startTime = System.currentTimeMillis();
        result = googleFilter.find(employeeList, mappingList, false, age);
        endTime = System.currentTimeMillis();

        System.out.println("Google result = " + (double)(endTime-startTime)/1000);
    }

    @Test(sequential = true)
    public void testApacheFilter(){

        Filter apacheFilter = new ApacheFilter();

        startTime = System.currentTimeMillis();
        result = apacheFilter.find(employeeList, mappingList, false, age);
        endTime = System.currentTimeMillis();

        System.out.println("Apache result = " + (double)(endTime-startTime)/1000);

    }

    @AfterMethod
    public void print(){

//        for(Employee employee : result){
//            System.out.println("result employee = " + employee.getFirstName());
//        }
        System.out.println("result = " + result.size());
        result.clear();

//        for(Employee employee : expected){
//            System.out.println("employee = " + employee.getFirstName());
//        }
    }
}
