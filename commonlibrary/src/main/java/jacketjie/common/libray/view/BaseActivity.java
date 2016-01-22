package jacketjie.common.libray.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.dialog.AsTimeDialogView;

/**
 * Created by Administrator on 2015/12/30.
 */
public class BaseActivity extends AppCompatActivity{

    public void showDialog(){
        AsTimeDialogView dialogView = (AsTimeDialogView) findViewById(R.id.id_network_dialog);
        if (dialogView != null){
            dialogView.setVisibility(View.VISIBLE);
            dialogView.startDraw();
        }
    }
    public void hiddenDialog(){
        AsTimeDialogView dialogView = (AsTimeDialogView) findViewById(R.id.id_network_dialog);
        if (dialogView != null){
            dialogView.setVisibility(View.GONE);
            dialogView.stopDraw();
        }
    }

}
