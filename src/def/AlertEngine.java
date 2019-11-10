package def;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class AlertEngine {

    int threshold;
    int noOfDays;
    ActivityEngine engine;
    ArrayList<EventReport> eventReports;

    public AlertEngine(String eventFile, String statFile, String outputFile, int days ) throws FileNotFoundException {
        this.noOfDays = days;
        engine = new ActivityEngine(eventFile, statFile, outputFile, noOfDays);
        eventReports = new ArrayList<EventReport>(days);
        getThreshold();
        calculateAnomalies();
        writeToStream();
    }

    public void getThreshold() {
        int sumOfWeights = 0;
        ArrayList<EventInfo> eventInfos = engine.getEventsInfos();
        for (int currentEvent = 0; currentEvent<noOfDays; currentEvent++) {
            sumOfWeights += eventInfos.get(currentEvent).getWeight();
        }
        threshold = sumOfWeights*2;
    }

    public void calculateAnomalies() {
        ArrayList<Event> events = engine.getGeneratedEvents();
        ArrayList<EventInfo> eventInfos = engine.getEventsInfos();

        double anomaly = 0.0;
        for (int currentDay = 0; currentDay<noOfDays; currentDay++) {
            int dailyAnomalyCounter = 0;
            eventReports.add(new EventReport(events.size()));

            for (int currentEvent = 0; currentEvent<events.size(); currentEvent++) {
                anomaly = abs(events.get(currentEvent).occurrences.get(currentDay) - events.get(currentEvent).getMean())
                        / events.get(currentEvent).getStdDev() * eventInfos.get(currentEvent).getWeight();
                eventReports.get(currentDay).eventAnomalies.add(new EventAnomaly(events.get(currentEvent).name, anomaly));
            }
            if (eventReports.get(currentDay).getCumulativeAnomalyCounter() >= threshold ) {
                eventReports.get(currentDay).flagAnomaly();
            }
        }
    }

    private void writeToStream() {
        System.out.println("Running Alert Engine ( Testing )");
        System.out.println("--------------------------------");
        String headerRow = "Event Name\t| Event Type\t| ";
        for (int i=0; i<noOfDays; i++) {
            headerRow += String.format("Day %d \t| ", i+1);
        }
        System.out.println(headerRow);

        String currentLine = "";
        for (int currentEvent = 0; currentEvent < engine.getGeneratedEvents().size(); currentEvent++) {
            Event event = engine.getGeneratedEvents().get(currentEvent);
            EventInfo eventInfo = engine.getEventsInfos().get(currentEvent);
            currentLine += String.format("%s\t| ", event.name);
            if (eventInfo.type == EventDataType.CONTINOUS) {
                currentLine += "C\t| ";
            }
            else if (eventInfo.type == EventDataType.DISCRETE) {
                currentLine += "D\t| ";
            }
            for (int currentDay=0 ; currentDay<noOfDays; currentDay++) {
                currentLine += String.format("%f\t| ", event.occurrences.get(currentDay));
            }
            System.out.println(currentLine);
        }

        System.out.println("----------------------------------------");
        System.out.println(String.format("%s: %f", "Threshold", threshold));

        currentLine = "";
        String status = "";
        for (int currentDay = 0; currentDay < noOfDays; currentDay++) {
            currentLine += String.format("Day %d: %f ", currentDay+1, eventReports.get(currentDay).getCumulativeAnomalyCounter());
            if (threshold >= eventReports.get(currentDay).getCumulativeAnomalyCounter())
                status = "OK";
            else
                status = "FLAGGED";
            currentLine += String.format("[ %s ]", status);
            System.out.println(currentLine);
        }
    }
}
