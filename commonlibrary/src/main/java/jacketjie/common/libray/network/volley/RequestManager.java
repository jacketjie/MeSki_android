package jacketjie.common.libray.network.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/test_1/6.
 */
public class RequestManager {
    private static RequestManager mInstance;
    private static RequestQueue mRequestQueue;

    private RequestManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new RequestManager(context);
                }
            }
        }
        return mInstance;
    }

    public <T> void addRequest(Request<T> req, String tag) {
        mRequestQueue.add(req);
        if (!TextUtils.isEmpty(tag))
            req.setTag(tag);
    }



}
