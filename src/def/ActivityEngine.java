package def;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ActivityEngine {

    String eventFileName;
    String statFileName;
    int noOfDays;

    public ActivityEngine(String eventFile, String statFile, int days) {
        eventFileName = eventFile;
        statFileName = statFile;
        noOfDays = days;
    }

    public void readEventsFromFile() throws FileNotFoundException {
        File eventFile = new File(eventFileName);
        Scanner eventScanner = new Scanner(eventFile);
        String currentLine = eventScanner.nextLine();
        String [] tokens = currentLine.split(":");

        String name;
        EventDataType datatype;
        float min, max;
        int weight;

        for(int tokenNo = 0; tokenNo < tokens.length; tokenNo++) {
            switch (tokenNo) {
                case 0:
                    name = tokens[tokenNo];
                    break;
                case 1:
                    if (tokens[tokenNo].equals("C")){
                        datatype = EventDataType.CONTINOUS;
                    }
                    else if (tokens[tokenNo].equals("D")){
                        datatype = EventDataType.DISCRETE;
                    }
                    break;
                case 2:
                    min = Float.valueOf(tokens[tokenNo]);
                    break;
                case 3:
                    max = Float.valueOf(tokens[tokenNo]);
                    break;

            }
        }
    }

    public void readStatsFromFile() throws FileNotFoundException {

    }
}
