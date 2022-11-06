package org.example;

import static org.miglecz.optimization.genetic.GeneticOptimizationBuilder.builder;
import static org.miglecz.optimization.stream.Collectors.toBestSolution;
import java.util.Map;
import java.util.Random;
import org.miglecz.optimization.Optimization;
import org.miglecz.optimization.Solution;
import org.miglecz.optimization.genetic.operator.Crossover;
import org.miglecz.optimization.genetic.operator.Factory;
import org.miglecz.optimization.genetic.operator.Fitness;
import org.miglecz.optimization.genetic.operator.Mutation;
import org.miglecz.optimization.stream.TakeWhiles;

public class Main {
    public static void main(final String[] args) {
        final Map<Integer, Integer> expected = Map.of(
            //1, 2,
            //2, 3,
            //3, 4,
            //4, 5,
            //5, 6,
            //6, 7,
            //7, 8,
            //8, 1
            1, 8,
            2, 1,
            3, 2,
            4, 3,
            5, 4,
            6, 5,
            7, 6,
            8, 7
        );
        final Factory<Program> factory = Program::newInstance;
        final Fitness<Program> fitness = p -> p.evaluate(expected.entrySet());
        final Mutation<Program> mutation = Program::mutate;
        final Crossover<Program> crossover = Program::crossover;
        final Optimization<Program> optimization = builder(Program.class)
            .withRandom(new Random())
            .withFactory(factory)
            .withFitness(fitness)
            //.withPopulation(50)
            .withElite(3)
            .withMutant(20, mutation)
            .withOffspring(50, crossover)
            .withImmigrant(10)
            .build();
        final Solution<Program> best = optimization.stream()
            .takeWhile(TakeWhiles.belowScore(0))
            //.peek(System.out::println)
            .peek(i -> System.out.println(i.toBestString()))
            .collect(toBestSolution());
        System.out.println("optimum " + best);
    }
}
