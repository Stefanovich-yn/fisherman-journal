package com.steff.fishermanjournal.main;

import com.steff.fishermanjournal.controller.Controller;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();

        System.out.println("==================================================");
        System.out.println("       FISHERMAN'S JOURNAL APPLICATION");
        System.out.println("==================================================\n");

        String createRecordRequest = "CREATE_RECORD\n" +
                "fisherman=Ivan Petrov\n" +
                "fish=Pike\n" +
                "quantity=3\n" +
                "weight=4500\n" +
                "waterBody=Neman River";
        sendRequest(controller, createRecordRequest);

        sendRequest(controller, "SHOW_ALL");

        String changeStatusRequest = "CHANGE_STATUS\n" +
                "id=1\n" +
                "status=VERIFIED";
        sendRequest(controller, changeStatusRequest);

        sendRequest(controller, "SHOW_ACTIVE");

        String badRequest = "CREATE_RECORD\n" +
                "fisherman=Ivan Petrov\n" +
                "fish=Perch\n" +
                "quantity=1\n" +
                "weight=50\n" +
                "waterBody=Neman River";
        sendRequest(controller, badRequest);
    }

    private static void sendRequest(Controller controller, String request) {
        System.out.println(">>> REQUEST:\n" + request);
        String response = controller.doAction(request);
        System.out.println("<<< RESPONSE:\n" + response);
        System.out.println("--------------------------------------------------\n");
    }
}