package org.wlf.filedownloader.listener;

import android.os.Handler;
import android.os.Looper;

import org.wlf.filedownloader.base.FailReason;

/**
 * OnDetectUrlFileListener
 * <br/>
 * 探测网络文件监听器
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 */
public interface OnDetectUrlFileListener {

    /**
     * the url file need to create(no database record for this url file)
     *
     * @param url      file url
     * @param fileName file name
     * @param saveDir  saveDir
     * @param fileSize fileSize
     */
    void onDetectNewDownloadFile(String url, String fileName, String saveDir, int fileSize);

    /**
     * the url file exist(it is in database record)
     *
     * @param url file url
     */
    void onDetectUrlFileExist(String url);

    /**
     * DetectUrlFileFailed
     *
     * @param url        file url
     * @param failReason fail reason
     */
    void onDetectUrlFileFailed(String url, DetectUrlFileFailReason failReason);

    /**
     * Callback helper for main thread
     */
    public static class MainThreadHelper {

        /**
         * the url file need to create(no database record for this url file)
         *
         * @param url      file url
         * @param fileName file name
         * @param saveDir  saveDir
         * @param fileSize fileSize
         */
        public static void onDetectNewDownloadFile(final String url, final String fileName, final String saveDir, final int fileSize, final OnDetectUrlFileListener onDetectUrlFileListener) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onDetectUrlFileListener.onDetectNewDownloadFile(url, fileName, saveDir, fileSize);
                    handler.removeCallbacksAndMessages(null);
                }
            });
        }

        /**
         * the url file exist(it is in database record)
         *
         * @param url file url
         */
        public static void onDetectUrlFileExist(final String url, final OnDetectUrlFileListener onDetectUrlFileListener) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onDetectUrlFileListener.onDetectUrlFileExist(url);
                    handler.removeCallbacksAndMessages(null);
                }
            });
        }

        /**
         * DetectUrlFileFailed
         *
         * @param url        file url
         * @param failReason fail reason
         */
        public static void onDetectUrlFileFailed(final String url, final DetectUrlFileFailReason failReason, final OnDetectUrlFileListener onDetectUrlFileListener) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onDetectUrlFileListener.onDetectUrlFileFailed(url, failReason);
                    handler.removeCallbacksAndMessages(null);
                }
            });
        }

    }

    /**
     * DetectUrlFileFailReason
     */
    public static class DetectUrlFileFailReason extends FailReason {

        private static final long serialVersionUID = -6863373572721814857L;

        /**
         * URL illegal
         */
        public static final String TYPE_URL_ILLEGAL = DetectUrlFileFailReason.class.getName() + "_TYPE_URL_ILLEGAL";
        /**
         * url over redirect count
         */
        public static final String TYPE_URL_OVER_REDIRECT_COUNT = DetectUrlFileFailReason.class.getName() + "_TYPE_URL_OVER_REDIRECT_COUNT";
        /**
         * bad http response code,not 2XX
         */
        public static final String TYPE_BAD_HTTP_RESPONSE_CODE = DetectUrlFileFailReason.class.getName() + "_TYPE_BAD_HTTP_RESPONSE_CODE";
        /**
         * the file need to download does not exist
         */
        public static final String TYPE_HTTP_FILE_NOT_EXIST = DetectUrlFileFailReason.class.getName() + "_TYPE_HTTP_FILE_NOT_EXIST";

        public DetectUrlFileFailReason(String detailMessage, String type) {
            super(detailMessage, type);
        }

        public DetectUrlFileFailReason(Throwable throwable) {
            super(throwable);
        }

        @Override
        protected void onInitTypeWithThrowable(Throwable throwable) {
            super.onInitTypeWithThrowable(throwable);
            // TODO
        }

    }
}