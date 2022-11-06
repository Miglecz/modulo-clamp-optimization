package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.miglecz.optimization.genetic.operator.Crossovers;

public class Program {
    private final List<Operator> list = new ArrayList<>();

    private Program() {
        list.add(randomOperator());
    }

    public Program crossover(final Program p) {
        return new Program(Crossovers.crossover(list, p.list));
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

    private Program(final List<Operator> list) {
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
