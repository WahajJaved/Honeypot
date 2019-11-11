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
    public AnalysisEngine(String logFile,String outputFileName , ArrayList <Event> eventType ) throws IOException {
        this.logFile = logFile;
        this.eventType = eventType;
        this.outputFileName = outputFileName;

        this.myEventColl = new EventsCollection();
        System.out.println("========================================\n");
        System.out.println("Running Analysis Engine");
        System.out.println("------------------------------");


        //generateStats();
        readLogFile();
        System.out.println("========================================\n");

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
                String[] tokens = fileInput.split(":");
                myEventColl.getLast().addOccurrence(Double.parseDouble(tokens[1]));
            }
            if(nameFlag){
                eventType.add(new Event(fileInput));
                nameFlag=false;
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
        Event event;
        printWriter.printf("%d\n", myEventColl.getSize());
        for (int currentEvent = 0; currentEvent < myEventColl.getSize() ; currentEvent++) {
            event = myEventColl.getAt(currentEvent);
            System.out.println(String.format("Event Name: %s", event.name));
            System.out.println(String.format("Mean: %f",event.getMean()));
            System.out.println(String.format("Std Deviation: %f",event.getStdDev()));
            printWriter.printf("%s:%f:%f\n",event.name,event.getMean(),event.getStdDev());
        }
        printWriter.close();
    }

}
