package com.steff.fishermanjournal.main;

import com.steff.fishermanjournal.controller.Controller;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("               FISHERMAN'S JOURNAL                ");
        System.out.println("==================================================");

        File databaseFile = new File("catch_records.txt");
        if (databaseFile.exists()) {
            databaseFile.delete();
        }

        Controller controller = new Controller();

        System.out.println("\n--- 1: Creating 2 correct records ---");

        String record1 = "CREATE_RECORD\n" +
                "fisherman=Ivan Petrovich\n" +
                "fish=Pike\n" +
                "quantity=3\n" +
                "weight=6000\n" +
                "waterBody=Neman River";
        sendRequest(controller, record1);

        String record2 = "CREATE_RECORD\n" +
                "fisherman=Marya Kotova\n" +
                "fish=Perch\n" +
                "quantity=10\n" +
                "weight=2500\n" +
                "waterBody=Naroch Lake";
        sendRequest(controller, record2);

        System.out.println("\n--- 2: Executing SHOW_ALL ---");
        sendRequest(controller, "SHOW_ALL");

        System.out.println("\n--- 3: Sequential status transition for record ID 1 ---");
        sendRequest(controller, "CHANGE_STATUS\nid=1\nstatus=VERIFIED");
        sendRequest(controller, "CHANGE_STATUS\nid=1\nstatus=REGISTERED");
        sendRequest(controller, "CHANGE_STATUS\nid=1\nstatus=ARCHIVED");

        System.out.println("\n--- 4: Forbidden status transition on record ID 2 (NEW -> ARCHIVED) ---");
        sendRequest(controller, "CHANGE_STATUS\nid=2\nstatus=ARCHIVED");

        System.out.println("\n--- 5: Search by fish type (partial, case-insensitive) ---");
        sendRequest(controller, "FIND_BY_FISH\nfish=pike");

        System.out.println("\n--- 6: Search by water body (partial, case-insensitive) ---");
        sendRequest(controller, "FIND_BY_WATERBODY\nwaterBody=naro");

        System.out.println("\n--- 7: Getting statistics for Ivan Petrovich ---");
        sendRequest(controller, "GET_STATS\nfisherman=Ivan Petrovich");

        System.out.println("\n--- 8: Executing SHOW_ACTIVE (ID 1 is ARCHIVED and must be hidden) ---");
        sendRequest(controller, "SHOW_ACTIVE");

        System.out.println("\n--- 9: Error handling and validations ---");

        System.out.println("* Unknown Command:");
        sendRequest(controller, "UNKNOWN_COMMAND_NAME\nparam=test");

        System.out.println("* Invalid quantity (quantity=0):");
        String badQuantity = "CREATE_RECORD\n" +
                "fisherman=Bob\n" +
                "fish=Carp\n" +
                "quantity=0\n" +
                "weight=500\n" +
                "waterBody=Pond";
        sendRequest(controller, badQuantity);

        System.out.println("* Too short water body name (less than 3 characters):");
        String badWaterBody = "CREATE_RECORD\n" +
                "fisherman=Bob\n" +
                "fish=Carp\n" +
                "quantity=1\n" +
                "weight=500\n" +
                "waterBody=Ab";
        sendRequest(controller, badWaterBody);

        System.out.println("* Missing parameter 'fisherman':");
        String missingFisherman = "CREATE_RECORD\n" +
                "fish=Carp\n" +
                "quantity=1\n" +
                "weight=500\n" +
                "waterBody=Svityaz Lake";
        sendRequest(controller, missingFisherman);

        System.out.println("\n--- 10: Simulating Application Restart ---");
        System.out.println("Creating new controller instance to re-read file from disk...");

        Controller restartedController = new Controller();

        System.out.println("\n* Verifying that saved records are loaded from file:");
        sendRequest(restartedController, "SHOW_ALL");

        System.out.println("* Creating a new record to verify ID continues from 3 without duplication:");
        String record3 = "CREATE_RECORD\n" +
                "fisherman=Alex Ivanov\n" +
                "fish=Zander\n" +
                "quantity=1\n" +
                "weight=1200\n" +
                "waterBody=Dnepr River";
        sendRequest(restartedController, record3);

        System.out.println("* Final journal state after restart:");
        sendRequest(restartedController, "SHOW_ALL");
    }

    private static void sendRequest(Controller controller, String request) {
        System.out.println(">>> REQUEST:\n" + request);
        String response = controller.doAction(request);
        System.out.println("<<< RESPONSE:\n" + response);
        System.out.println("--------------------------------------------------");
    }
}