package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 14-Mar-18.
 */

public class Report {

    public interface OnReportListener {
        void resieveFlag(ArrayList<ResponseOfServer> res);

        void sendMessage(String message);
    }

    private OnReportListener onReportListener;

    public Report(OnReportListener onReportListener) {
        this.onReportListener = onReportListener;
    }

    public void report(String signText, String reportText, int spamId, String spamerId, String reporterId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> reportReq = api.report(signText, reportText, spamId, spamerId, reporterId);
        reportReq.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onReportListener.resieveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onReportListener.sendMessage(Message.convertRetrofitMessage(t.getMessage()));
            }
        });
    }
}
