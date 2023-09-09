package com.eat.gymgenius;

import java.util.List;

public class Workout {
    private String name;
    private List<Exercise> exercises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Workout(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return name;
    }
}
