package jacketjie.common.libray.network.volley;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016/test_1/7.
 */
public interface VolleyResponseListener<T> {
    void requestSuccess(T data);
    void requestError(VolleyError error);
}
