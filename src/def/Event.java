package def;

import java.util.ArrayList;

public class Event {
    public String name;
    public ArrayList<Double> occurrences;

    public Event(String name) {
        this.name = name;

    }
    public void addOccurrence(double occurrence) {
        occurrences.add(occurrence);
    }

    public double getMean() {
        double sum = 0;
        for (int i=0; i< occurrences.size(); i++) {
            sum += occurrences.get(i);
        }
        return (sum/occurrences.size());
    }

    public double getStdDev() {
        double mean = getMean();
        double temp =0;
        for (int i = 0; i < occurrences.size(); i++)
        {
            double val = occurrences.get(i);
            double squareDifferenceToMean = Math.pow(val - mean, 2);

            temp += squareDifferenceToMean;
        }
        double meanOfDiffs = temp / (double) (occurrences.size());
        return Math.sqrt(meanOfDiffs);
    }
}
