package def;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {

        if (args.length<3) {
            System.out.println("Missing Requirements");
            System.out.println("Required min 3 args");
            System.exit(0);
        }
        String initialStatFilename = "";
        String initialEventFilename = "";
        int noOfDays = 0;

        for(int i=0;i<args.length;i++) {
            switch (i) {
                case 0:
                    initialEventFilename = args[i];
                    break;
                case 1:
                    initialStatFilename = args[i];
                    break;
                case 2:
                    noOfDays = Integer.parseInt(args[i]);
                    break;
                default:
                    System.out.println("Invalid Number of Arguments");
            }
        }

        System.out.println("Received the following values as parameters");
        System.out.println(String.format("Event Filename: %s", initialEventFilename));
        System.out.println(String.format("Stat Filename: %s", initialStatFilename));
        System.out.println(String.format("No Days: %d",noOfDays));



        ActivityEngine activityEngine = new ActivityEngine(initialEventFilename,initialStatFilename,
                "logs.txt", noOfDays);
        activityEngine.writeGeneratedEventsToFile("logs.txt");

        String outputNewStatsFile = "newStats.txt";
        AnalysisEngine analysisEngine = new AnalysisEngine("logs.txt", outputNewStatsFile, activityEngine.getGeneratedEvents());

        AlertEngine alertEngine = new AlertEngine(activityEngine, noOfDays);
    }
}

