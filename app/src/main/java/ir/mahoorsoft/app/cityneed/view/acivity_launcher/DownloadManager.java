package ir.mahoorsoft.app.cityneed.view.acivity_launcher;

import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by usb-web on 2/28/2018.
 */

public class DownloadManager {

    private final String LOG_TAG = "DownloadManager";

    private static final int DELAY_SIMULATED = 10;
    private static final int DOWNLOAD_BUFFER_SIZE = 4 * 1024;

    private Handler handler = new Handler();

    private int downloadedSize;
    private int totalSize;
    private int percent;
    private boolean isDownloadComplete = false;

    private String downloadPath;
    private String savePath;
    private String fileName;
    private OnProgressDownloadListener downloadListener;
    private boolean simulate;

    private boolean isDownloadStop = false;

    public DownloadManager download() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    totalSize = connection.getContentLength();

                    new File(savePath).mkdirs();

                    FileOutputStream outputStream = new FileOutputStream(savePath + fileName);
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
                    int len = 0;

                    if (downloadListener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadListener.onDownloadStart();
                            }
                        });
                    }


                    while ((len = inputStream.read(buffer)) > 0) {
                        if (isDownloadStop) {
                            break;
                        }

                        outputStream.write(buffer, 0, len);
                        downloadedSize += len;

                        percent = (int) (100.0f * (float) downloadedSize / totalSize);

                        // log the percent
                        Log.i(LOG_TAG, "percent" + percent);

                        if (downloadListener != null) {
                            final int finalDownloadedSize = downloadedSize;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    downloadListener.onDownloading(percent, finalDownloadedSize, totalSize);

                                    if (percent == 100) {
                                        if (!isDownloadComplete) {
                                            downloadListener.onDownloadFinished(savePath + fileName);
                                            isDownloadComplete = true;
                                        }
                                    }
                                }
                            });
                        }

                        if (simulate) {
                            try {
                                Thread.sleep(DELAY_SIMULATED);
                            } catch (InterruptedException e) {
                                Log.e(LOG_TAG, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }

                    outputStream.close();
                } catch (IOException e) {
                    if (downloadListener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadListener.onDownloadFailed();
                            }
                        });
                    }

                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return this;
    }

    public void stopDownlaod() {
        isDownloadStop = true;
    }

    public DownloadManager downloadPath(String value) {
        downloadPath = value;
        return this;
    }

    public DownloadManager fileName(String value) {
        fileName = value;
        return this;
    }

    public DownloadManager savePath(String value) {
        savePath = value;
        return this;
    }

    public DownloadManager listener(OnProgressDownloadListener value) {
        downloadListener = value;
        return this;
    }

    public DownloadManager simulate(boolean value) {
        simulate = value;
        return this;
    }

    public interface OnProgressDownloadListener {

        void onDownloadStart();

        void onDownloading(int percent, int downloadedSize, int fileSize);

        void onDownloadFinished(String saveFilePath);

        void onDownloadFailed();
    }
}
