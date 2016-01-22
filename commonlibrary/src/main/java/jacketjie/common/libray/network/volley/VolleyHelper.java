package jacketjie.common.libray.network.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

/**
 * Created by Administrator on 2016/test_1/6.
 */
public class VolleyHelper {
    /**
     * get请求，返回字符串
     *
     * @param context
     * @param url
     * @param header
     * @param tag
     * @param listener
     * @param errorListener
     */
    public static void RequestGetString(Context context, String url, Map<String, String> header, String tag, Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, header, listener, errorListener);
        RequestManager.getInstance(context).addRequest(stringRequest, tag);
    }

    /**
     * get请求，自定义接口获取请求数据
     *
     * @param context
     * @param url
     * @param header
     * @param tag
     * @param responseListener
     */
    public static void RequestGetString(Context context, String url, Map<String, String> header, String tag, VolleyResponseListener<String> responseListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, header, setResponseListener(responseListener), setErrorListener(responseListener));
        RequestManager.getInstance(context).addRequest(stringRequest, tag);
    }

    /**
     * get请求，Gson自动解析返回实体对象
     *
     * @param context
     * @param clazz
     * @param url
     * @param mHeader
     * @param listener
     * @param errorListener
     * @param <T>
     */
    public static <T> void RequestGetBean(Context context, Class<T> clazz, String url, Map<String, String> mHeader, Response.Listener<T> listener,
                                          Response.ErrorListener errorListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET, url, clazz, mHeader, null, listener, errorListener);
        RequestManager.getInstance(context).addRequest(gsonRequest, null);
    }

    /**
     * get请求，Gson自动解析返回实体对象，自定义接口获取请求数据
     *
     * @param context
     * @param clazz
     * @param url
     * @param mHeader
     * @param responseListener
     * @param <T>
     */
    public static <T> void RequestGetBean(Context context, Class<T> clazz, String url, Map<String, String> mHeader, final VolleyResponseListener<T> responseListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET, url, clazz, mHeader, null, setResponseListener(responseListener), setErrorListener(responseListener));
        RequestManager.getInstance(context).addRequest(gsonRequest, null);
    }

    /**
     * post请求，返回字符串
     *
     * @param context
     * @param url
     * @param header
     * @param tag
     * @param listener
     * @param errorListener
     */
    public static void RequestPostString(Context context, String url, Map<String, String> header, String tag, Response.Listener<String> listener,
                                         Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, header, listener, errorListener);
        RequestManager.getInstance(context).addRequest(stringRequest, tag);
    }

    public static void RequestPostString(Context context, String url, Map<String, String> header, String tag, VolleyResponseListener<String> responseListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, header, setResponseListener(responseListener), setErrorListener(responseListener));
        RequestManager.getInstance(context).addRequest(stringRequest, tag);
    }

    /**
     * post请求，返回gson解析实体
     *
     * @param context
     * @param clazz
     * @param url
     * @param mHeader
     * @param mParams
     * @param listener
     * @param errorListener
     * @param <T>
     */
    public static <T> void RequestPostBean(Context context, Class<T> clazz, String url, Map<String, String> mHeader, Map<String, String> mParams, Response.Listener<T> listener,
                                           Response.ErrorListener errorListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.POST, url, clazz, mHeader, mParams, listener, errorListener);
        RequestManager.getInstance(context).addRequest(gsonRequest, null);
    }

    /**
     * post请求，返回Gson解析实体类，自定义接口接收数据
     *
     * @param context
     * @param clazz
     * @param url
     * @param mHeader
     * @param mParams
     * @param responseListener
     * @param <T>
     */
    public static <T> void RequestPostBean(Context context, Class<T> clazz, String url, Map<String, String> mHeader, Map<String, String> mParams, VolleyResponseListener<T> responseListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.POST, url, clazz, mHeader, mParams, setResponseListener(responseListener), setErrorListener(responseListener));
        RequestManager.getInstance(context).addRequest(gsonRequest, null);
    }


    /**
     * 设置请求成功监听
     *
     * @param listener
     * @param <T>
     * @return
     */
    protected static <T> Response.Listener<T> setResponseListener(final VolleyResponseListener<T> listener) {
        Response.Listener<T> responseListener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (listener != null)
                    listener.requestSuccess(response);
            }
        };
        return responseListener;
    }

    /**
     * 设置请求失败监听
     *
     * @param errorListener
     * @param <T>
     * @return
     */
    protected static <T> Response.ErrorListener setErrorListener(final VolleyResponseListener<T> errorListener) {
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.requestError(error);
            }
        };
        return error;
    }

}
