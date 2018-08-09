/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lambdasinaction.dsl;

import lambdasinaction.dsl.model.Order;
import lambdasinaction.dsl.model.Tax;

import java.util.function.Function;

/**
 * 利用函数式
 */
public class TaxCalculator {

    public static double calculate( Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge ) {
        double value = order.getValue();
        if (useRegional) value = Tax.regional(value);
        if (useGeneral) value = Tax.general(value);
        if (useSurcharge) value = Tax.surcharge(value);
        return value;
    }

    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;

    public TaxCalculator withTaxRegional() {
        useRegional = true;
        return this;
    }

    public TaxCalculator withTaxGeneral() {
        useGeneral= true;
        return this;
    }

    public TaxCalculator withTaxSurcharge() {
        useSurcharge = true;
        return this;
    }

    public double calculate(Order order) {
        return calculate( order, useRegional, useGeneral, useSurcharge );
    }

    public Function<Double, Double> taxFuncion = Function.identity();

    /**
     * 我们是否会写这个参数形式的接口，
     * @param f
     * @return
     */
    public TaxCalculator with(Function<Double, Double> f) {
        taxFuncion.andThen( f );
        return this;
    }

    public double calculateF(Order order) {
        return taxFuncion.apply( order.getValue() );
    }

    public static void main(String[] args) {
        Order order = new Order();

        double value = TaxCalculator.calculate(order, true, false, true);
        System.out.println(value);

        //类似builder 模式
        value = new TaxCalculator().withTaxRegional()
            .withTaxSurcharge()
            .calculate(order);

        //用函数式编程的想法实现
        value = new TaxCalculator().with(Tax::regional)
            .with(Tax::surcharge)
            .calculate(order);

        //函数式编程，核心是函数，有时候计算往往是后置的，把计算公式传递，
        //不过我们平时用的Builder 一般针对bean来说，一个比较简单的实现例如lambok
        System.out.println("======="+new TaxCalculator().with(Tax::test).calculate(order));
    }
}
