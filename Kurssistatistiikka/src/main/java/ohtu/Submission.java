package ohtu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Submission {
    private int week;
    private int hours;
    private List<Integer> exercises;


    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
        String ex = exercises.stream().map(e -> e.toString()).collect (Collectors.joining (" "));
        return "viikko " + week + ": tehtyjä tehtäviä yhteensä: " + exercises.size() + " , aikaa kului " + hours +" tuntia, tehdyt tehtävät: " + ex;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }

}