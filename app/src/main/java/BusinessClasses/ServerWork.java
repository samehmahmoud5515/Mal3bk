package BusinessClasses;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Khaled on 4/16/2017.
 */

public class ServerWork {
   // public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    okhttp3.MediaType JSON = okhttp3.MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

    OkHttpClient client = new OkHttpClient();

    public Call post(String url,String image,int id,String imageName, Callback callback) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"key\"\r\n\r\nlogic123\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"id\"\r\n\r\n"+id+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"image\"\r\n\r\n"+image+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW" +
                "\\r\\nContent-Disposition: form-data; name=\\\"imageName\\\"\\r\\n\\r\\n"+imageName+"\\r\\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "aec09b91-a167-99d9-bcd3-aca6cfbf52d7")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }




}
