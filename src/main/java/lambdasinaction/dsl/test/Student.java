package lambdasinaction.dsl.test;

public class Student {

    String firstName;
    String lastName;
    Double grade;
    Double feeDiscount = 0.0;
    Double baseFee = 2000.0;

    public Student(String firstName, String lastName, Double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    public void printFee(){
        System.out.println(baseFee+"------"+feeDiscount);
        Double newFee = baseFee - ((baseFee * feeDiscount)/100);
        System.out.println("The fee after discount: " + newFee);
    }
}