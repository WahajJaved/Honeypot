package def;

public class EventInfo {
    public String name;
    public EventDataType type;
    public double minValue;
    public double maxValue;
    public int weight;

    double mean;
    double stdDev;

    public EventInfo(String name, EventDataType datatype, double min, double max, int weight) {
        this.name = name;
        this.type = datatype;
        this.minValue = min;
        this.maxValue = max;
        this.weight = weight;
    }
    public void setStatistic(double mean, double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }
}
