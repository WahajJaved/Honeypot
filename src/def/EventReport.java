package def;

import java.util.ArrayList;

public class EventReport {
    public boolean flagged;

    ArrayList<EventAnomaly> eventAnomalies;

    public EventReport(int size) {
        eventAnomalies = new ArrayList<EventAnomaly>(size);
        flagged = false;
    }

    public double getCumulativeAnomalyCounter() {
        double sum = 0;
        for (EventAnomaly anomaly : eventAnomalies) {
            sum += anomaly.weightedValue;
        }
        return sum;
    }
    public void flagAnomaly() {
        flagged = true;
    }
}
