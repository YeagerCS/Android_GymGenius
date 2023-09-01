package com.eat.gymgenius;

public class Workout {
    private String name;
    private Exercise[] exercises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Exercise[] getExercises() {
        return exercises;
    }

    public void setExercises(Exercise[] exercises) {
        this.exercises = exercises;
    }

    public Workout(String name, Exercise[] exercises) {
        this.name = name;
        this.exercises = exercises;
    }
}
