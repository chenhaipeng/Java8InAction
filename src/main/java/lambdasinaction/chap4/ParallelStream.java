package lambdasinaction.chap4;

import java.util.Arrays;
import java.util.List;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2018/08/02 上午10:35
 */
public class ParallelStream {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream()
            .forEach(System.out::println);
    }
}
