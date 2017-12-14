package ir.mahoorsoft.app.cityneed.model.struct;

import ir.mahoorsoft.app.cityneed.model.api.ApiClient;

/**
 * Created by MAHNAZ on 11/30/2017.
 */

public class Message {
    public static String getCodeMessage(int i) {
        String response;
        switch (i) {
            case 0:
                response ="ثبت شماره همراه با مشکل مواجه شد.";
                break;
            default:
                response = "خطا!!!!";
        }
        return response;
    }

    public static String convertRetrofitMessage(String s){
        switch (s){

            case "java.net.SocketTimeoutException: connect timed out":
               return "ارتباط با سرور مقدور نمی باشد....";
            case "java.net.ConnectException: Failed to connect to /"+ ApiClient.serverAddress:
                return "ارتباط با سرور مقدور نمی باشد....";
            default:
                return s;

        }

    }
}
