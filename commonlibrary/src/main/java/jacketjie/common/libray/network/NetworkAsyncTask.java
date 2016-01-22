package jacketjie.common.libray.network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;

import jacketjie.common.libray.custom.view.dialog.AsTimeDialogLayout;


/**
 * Created by Administrator on 2015/12/30.
 */
public abstract class NetworkAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private ViewGroup group;
    private Context context;
    private boolean showDialog;
    private String DIALOG_TAG = "as_times_dialog_tag";
    private AsTimeDialogLayout dialogLayout;
//    private AsTimeDialogView dialogView;

    public NetworkAsyncTask(Context context, ViewGroup group, boolean showDialog) {
        this.context = context;
        this.group = group;
        this.showDialog = showDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            if (group != null) {
                if (group.findViewWithTag(DIALOG_TAG) != null) {
                    dialogLayout = (AsTimeDialogLayout) group.findViewWithTag(DIALOG_TAG);
                    dialogLayout.start();
                } else {
//                    dialogGroup = LayoutInflater.from(context).inflate(R.layout.progress_dialog,null);
//                    dialogView = (AsTimeDialogView) dialogGroup.findViewById(R.id.id_network_dialog);
//                    FrameLayout frameLayout = new FrameLayout(context);
//                    frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                    frameLayout.setTag(DIALOG_TAG);
//                    dialogView = new AsTimeDialogView(context);
                    dialogLayout = new AsTimeDialogLayout(context);
                    dialogLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    dialogLayout.setTag(DIALOG_TAG);
//                    dialogView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                    dialogView.setTag(DIALOG_TAG);
//                    frameLayout.addView(dialogView);
                        group.addView(dialogLayout, 0);
                    dialogLayout.start();
                }
            }
        }
    }

    @Override
    protected void onCancelled(Result result) {
        super.onCancelled(result);
        if (showDialog && dialogLayout != null) {
            dialogLayout.stop();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (showDialog && dialogLayout != null) {
            dialogLayout.stop();
        }
    }
}
