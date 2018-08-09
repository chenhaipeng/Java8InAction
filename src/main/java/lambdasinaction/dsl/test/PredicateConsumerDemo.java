package lambdasinaction.dsl.test;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PredicateConsumerDemo {

    public static Student updateStudentFee(Student student, Predicate<Student> predicate, Consumer<Student> consumer){
        if (predicate.test(student)){
            consumer.accept(student);
        }
        return student;
    }

}