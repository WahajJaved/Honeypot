package def;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AnalysisEngine {
    String logFile;
    String outputFileName;
    ArrayList <Event> eventType;
    EventsCollection myEventColl;

    //
    public AnalysisEngine(String logFile,String outputFileName , ArrayList <Event> eventType ){
        this.logFile = logFile;
        this.eventType = eventType;
        this.outputFileName = outputFileName;
    }

    public boolean isValidEvent(Event evn){
        Iterator<Event> eventIterator = eventType.iterator();
        while (eventIterator.hasNext()) {
            Event currentEvent = eventIterator.next();
            if(evn.name == currentEvent.name ){
                return true;
            }
        }
        return false;
    }

    public void readLogFile() throws FileNotFoundException {
        File file = new File(logFile);
        myEventColl = new EventsCollection();

        String fileInput;
        Event placeEvent;
        boolean nameFlag = false;
        boolean dataFlag = false;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){
            fileInput = sc.nextLine();
            if(dataFlag){
                String[] tokens = fileInput.split("\\:");
                myEventColl.getLast().addOccurrence(Double.parseDouble(tokens[1]));
            }
            if(nameFlag){
                eventType.add(new Event(fileInput));
                nameFlag = false;
                dataFlag=true;
            }
            if(fileInput.toString().equals("---")){
                nameFlag=true;
                dataFlag=false;
            }
            else{
                nameFlag=false;
                dataFlag=false;
            }
        }
        sc.close();
    }

    public void generateStats() throws IOException {
        FileWriter fileWriter = new FileWriter(outputFileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Event curreEvent;
        for (int currentEvent = 0; currentEvent < myEventColl.getSize() ; currentEvent++) {
            curreEvent = myEventColl.getAt(currentEvent);
            printWriter.printf("%s:%f:%f\n",curreEvent.name,curreEvent.getMean(),curreEvent.getStdDev());
        }
        printWriter.close();
    }

}
