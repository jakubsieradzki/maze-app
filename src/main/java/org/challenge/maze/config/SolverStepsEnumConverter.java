package org.challenge.maze.config;

import org.challenge.maze.infrastructure.http.maze.SolverSteps;
import org.springframework.core.convert.converter.Converter;

public class SolverStepsEnumConverter implements Converter<String, SolverSteps> {
    @Override
    public SolverSteps convert(String source) {
        return SolverSteps.valueOf(source.toUpperCase());
    }

}
