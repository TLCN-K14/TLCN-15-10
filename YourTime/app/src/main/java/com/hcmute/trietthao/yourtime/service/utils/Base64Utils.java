package com.hcmute.trietthao.yourtime.service.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64Utils {

    public static void decode(String content, IBase64DecodeListener listener) {
        if (content == null || content.length() == 0) {
            listener.onDecodeSuccess("");
            return;
        }
        Base64DecodeTask task = new Base64DecodeTask();
        task.setListener(listener);
        task.execute(content);
    }
    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String encodeImage = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return encodeImage;
    }

    public interface IBase64DecodeListener {

        void onDecodeSuccess(String content);

        void onDecodeError();
    }

    private static class Base64DecodeTask extends AsyncTask<String, Void, String> {

        private IBase64DecodeListener listener;

        public IBase64DecodeListener getListener() {
            return listener;
        }

        public void setListener(IBase64DecodeListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return new String(Base64.decode(params[0], Base64.DEFAULT));
            } catch (Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String content) {
            if (content != null) {
                listener.onDecodeSuccess(content);
            } else {
                listener.onDecodeError();
            }
        }
    }

}
