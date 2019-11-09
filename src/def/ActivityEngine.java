package def;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityEngine {

    String eventFileName;
    String statFileName;
    int noOfDays;

    ArrayList<EventInfo> eventInfos;

    public ActivityEngine(String eventFile, String statFile, int days) {
        eventFileName = eventFile;
        statFileName = statFile;
        noOfDays = days;


    }

    public void readEventsFromFile() throws FileNotFoundException {
        File eventFile = new File(eventFileName);
        Scanner eventScanner = new Scanner(eventFile);

        int lineNo = 0;

        while (eventScanner.hasNextLine()) {
            String currentLine = eventScanner.nextLine();
            if (lineNo == 0) {
                int num = Integer.parseInt(currentLine);
                eventInfos = new ArrayList<EventInfo>(num);
            } else {
                String[] tokens = currentLine.split(":");
                eventInfos.add(getEventInfoFromToken(tokens));
            }
            lineNo++;
        }
    }

    public void readStatsFromFile() throws FileNotFoundException {
        File statFile = new File(statFileName);
        Scanner statScanner = new Scanner(statFile);

        int lineNo = 0;

        while (statScanner.hasNextLine()) {
            String currentLine = statScanner.nextLine();
            if (lineNo == 0) {
                int num = Integer.parseInt(currentLine);
            } else {
                String[] tokens = currentLine.split(":");
                setEventInfoStatisticFromToken(tokens);
            }
            lineNo++;
        }
    }

    private EventInfo getEventInfoFromToken(String[] tokens) {
        String name = "";
        EventDataType datatype = null;
        double min = 0.0;
        double max = 0.0;
        int weight = 0;

        for (int tokenNo = 0; tokenNo < tokens.length; tokenNo++) {
            switch (tokenNo) {
                case 0:
                    name = tokens[tokenNo];
                    break;
                case 1:
                    if (tokens[tokenNo].equals("C")) {
                        datatype = EventDataType.CONTINOUS;
                    } else if (tokens[tokenNo].equals("D")) {
                        datatype = EventDataType.DISCRETE;
                    } else {
                        System.out.println("Event Type is Unknown");
                    }
                    break;
                case 2:
                    if (datatype == EventDataType.CONTINOUS) {
                        min = Double.valueOf(tokens[tokenNo]);
                    } else if (datatype == EventDataType.DISCRETE) {
                        min = Double.valueOf(tokens[tokenNo]);
                    }
                    break;
                case 3:
                    if (datatype == EventDataType.CONTINOUS) {
                        max = Double.valueOf(tokens[tokenNo]);
                    } else if (datatype == EventDataType.DISCRETE) {
                        max = Double.valueOf(tokens[tokenNo]);
                    }
                case 4:
                    weight = Integer.valueOf(tokens[tokenNo]);
                    break;
                default:
                    System.out.println("Error, out of range data");
            }
        }
        return new EventInfo(name,datatype, min, max, weight);
    }


    public Event generateSingleNormalEvent() {

        return null;
    }

    public void setEventInfoStatisticFromToken(String[] tokens) {
        String name;
        double mean = 0.0;
        double stdDev = 0.0;

        EventInfo eventInfo = null;


        for (int tokenNo = 0; tokenNo < tokens.length; tokenNo++) {
            switch (tokenNo) {
                case 0:
                    name = tokens[tokenNo];
                    for (int i = 0; i < eventInfos.size(); i++) {
                        if (eventInfos.get(i).name.equals(name)) {
                            eventInfo = eventInfos.get(i);
                        }
                    }
                    break;
                case 1:
                    mean = Double.parseDouble(tokens[tokenNo]);
                    break;
                case 2:
                    stdDev = Double.parseDouble(tokens[tokenNo]);
                    break;
                default:
                    System.out.println("Error, out of range data");
            }
        }
        eventInfo.setStatistic(mean, stdDev);
    }
}