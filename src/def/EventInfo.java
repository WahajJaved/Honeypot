package def;

public class EventInfo {
    public String name;
    public EventDataType type;
    public float minValue;
    public float maxValue;
    public int weight;

    public EventInfo(String name, EventDataType datatype, float min, float max, int weight) {
        this.name = name;
        this.type = datatype;
        this.minValue = min;
        this.maxValue = max;
        this.weight = weight;
    }


}
