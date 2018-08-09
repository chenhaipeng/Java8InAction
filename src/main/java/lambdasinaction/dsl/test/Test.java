package lambdasinaction.dsl.test;

import static lambdasinaction.dsl.test.PredicateConsumerDemo.updateStudentFee;

public class Test {
    public static void main(String[] args) {
        Student student1 = new Student("Ashok","Kumar", 9.5);

        student1 = updateStudentFee(student1,
                //Lambda expression for Predicate interface
                student -> student.grade > 8.5,
                //Lambda expression for Consumer inerface
                student -> student.feeDiscount = 30.0);
        student1.printFee(); //The fee after discount: 1400.0

        Student student2 = new Student("Rajat","Verma", 8.0);
        student2 = updateStudentFee(student2,
                //Lambda expression for Predicate interface
                student -> student.grade >= 8,

                //Lambda expression for Consumer inerface
            //student -> student.feeDiscount = 30.0);
            System.out::println);
        student2.printFee();//The fee after discount: 1600.0




    }
}