package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Program {
    private final List<Operator> list = new ArrayList<>();

    private Program() {
        list.add(randomOperator());
    }

    public Program crossover(final Program p) {
        final Random random = new Random();
        int x = random.nextInt(this.list.size());
        int y = random.nextInt(p.list.size());
        final List<Operator> list = new ArrayList<>();
        for (int i = 0; i <= x; i++) {
            list.add(this.list.get(i));
        }
        for (int i = y; i < p.list.size(); i++) {
            list.add(p.list.get(i));
        }
        return new Program(list);
    }

    public double evaluate(final Set<Map.Entry<Integer, Integer>> set) {
        double score = 0.0;
        for (final Map.Entry<Integer, Integer> entry : set) {
            score -= Math.abs(evaluate(entry.getKey()) - entry.getValue());
        }
        return score;
    }

    private static Operator randomOperator() {
        return Operator.newInstance(new Random().nextInt(8));
    }

    private Program(List<Operator> list) {
        this.list.addAll(list);
    }

    private int evaluate(int i) {
        for (final Operator operator : list) {
            i = operator.getOperator().apply(i);
        }
        return i;
    }

    public static Program newInstance() {
        return new Program();
    }

    @Override
    public String toString() {
        return "Program{" + list + "}";
    }

    public Program mutate() {
        final List<Operator> list = new ArrayList<>(this.list);
        list.set(new Random().nextInt(list.size()), randomOperator());
        return new Program(list);
    }
}
