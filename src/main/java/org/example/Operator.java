package org.example;

import java.util.Random;
import java.util.function.UnaryOperator;

public class Operator {
    private final UnaryOperator<Integer> operator;
    private final String name;

    public UnaryOperator<Integer> getOperator() {
        return operator;
    }

    private Operator(final UnaryOperator<Integer> operator, final String name) {
        this.operator = operator;
        this.name = name;
    }

    private static Operator minus(int i) {
        return new Operator(o -> o - i, "i -= " + i);
    }

    private static Operator plus(int i) {
        return new Operator(o -> o + i, "i += " + i);
    }

    private static Operator mod(int i) {
        ++i;
        final int finalI = i;
        return new Operator(o -> o % finalI, "i %= " + i);
    }

    public static Operator random(int i) {
        switch (new Random().nextInt(3)) {
            case 0:
                return minus(i);
            case 1:
                return plus(i);
            case 2:
                return mod(i);
        }
        return null;
    }

    public static Operator newInstance(int i) {
        return random(i);
    }

    @Override
    public String toString() {
        return "Operator{" + name + "}";
    }
}
