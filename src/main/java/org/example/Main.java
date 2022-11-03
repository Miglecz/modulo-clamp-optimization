package org.example;

import static org.miglecz.optimization.Collect.toBestSolution;
import static org.miglecz.optimization.TakeWhile.progressingIteration;
import static org.miglecz.optimization.genetic.facade.GeneticBuilderFacade.builder;
import java.util.Map;
import java.util.Random;
import org.miglecz.optimization.Optimization;
import org.miglecz.optimization.Solution;
import org.miglecz.optimization.genetic.facade.operator.Crossover;
import org.miglecz.optimization.genetic.facade.operator.Factory;
import org.miglecz.optimization.genetic.facade.operator.Fitness;
import org.miglecz.optimization.genetic.facade.operator.Mutation;

public class Main {
    public static void main(String[] args) {
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
            .withPopulation(50)
            .withElite(1)
            .withMutant(20, mutation)
            .withOffspring(20, crossover)
            .withImmigrant(20)
            .build();
        final Solution<Program> best = optimization.stream()
            .takeWhile(progressingIteration(1000))
            .peek(iteration -> {
                System.out.printf("iteration=%d pop=%d best=%s%n",
                    iteration.getIndex(),
                    iteration.getSolutions().size(),
                    iteration.getBest().get()
                );
                //System.out.println(iteration);
            })
            .limit(20000)
            .collect(toBestSolution());
        System.out.println("optimum " + best);
    }
}
