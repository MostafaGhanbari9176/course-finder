package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.tables.Report;

/**
 * Created by M-gh on 14-Mar-18.
 */

public class PresentReport implements Report.OnReportListener {


    public interface OnPresentReportListener {
        void flagFromReport(boolean flag);

        void messageFromReport(String message);
    }

    private OnPresentReportListener onPresentReportListener;

    public PresentReport(OnPresentReportListener onPresentReportListener) {
        this.onPresentReportListener = onPresentReportListener;
    }

    public void report(String signText, String reportText, int spamId, String spamerId, String reporterId) {
        Report report = new Report(this);
        report.report(signText, reportText, spamId, spamerId, reporterId);

    }

    @Override
    public void resieveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            onPresentReportListener.messageFromReport("خطا");
        else
            onPresentReportListener.flagFromReport(res.get(0).code == 0 ? false : true);
    }

    @Override
    public void sendMessage(String message) {

    }
}
