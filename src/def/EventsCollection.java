package def;

import java.util.ArrayList;

public class EventsCollection {
    public ArrayList<Event> eventCol;
    int size;

    public EventsCollection(){
        eventCol=null;
    }

    public void add(Event newEvent){
        eventCol.add(newEvent);
        size++;
    }

    public Event getLast(){
        return eventCol.get(eventCol.size()-1);
    }

    public Event getAt(int i){
        return eventCol.get(i);
    }

    public int getSize(){
        return size;
    }

}
