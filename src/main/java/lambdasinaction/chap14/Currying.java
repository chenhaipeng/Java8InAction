package lambdasinaction.chap14;

import java.util.function.DoubleUnaryOperator;

/**
 * 科里化
 * 科里化1是一种将具备2个参数(比如，x和y)的函数f转化为使用一个参数的函数g，并 且这个函数的返回值也是一个函数，它会作为新函数的一个参数。
 * 后者的返回值和初始函数的 返回值相同，即f(x,y) = (g(x))(y)。
 */
public class Currying {

    public static void main(String[] args) {
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        System.out.println(convertCtoF.applyAsDouble(24));
        System.out.println(convertUSDtoGBP.applyAsDouble(100));
        System.out.println(convertKmtoMi.applyAsDouble(20));

        DoubleUnaryOperator convertFtoC = expandedCurriedConverter(-32, 5.0/9, 0);
        System.out.println(convertFtoC.applyAsDouble(98.6));
    }

    static double converter(double x, double y, double z) {
        return x * y + z;
    }

    static DoubleUnaryOperator curriedConverter(double y, double z) {
        return (double x) -> x * y + z;
    }

    static DoubleUnaryOperator expandedCurriedConverter(double w, double y, double z) {
        return (double x) -> (x + w) * y + z;
    }
}
