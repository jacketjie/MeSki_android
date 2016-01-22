package jacketjie.common.libray.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jacketjie.common.libray.R;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<String> mDatas;
    private Context context;
    private LayoutInflater inflater;
    private int selectedPos;

    public GridViewAdapter(List<String> mDatas,Context context) {
        this.mDatas = mDatas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.item = (TextView) convertView.findViewById(R.id.grid_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setText(mDatas.get(position));
        if (selectedPos == position) {
            holder.item.setTextColor(Color.WHITE);
            holder.item.setBackgroundResource(R.drawable.grid_item_selected);
        } else {
            holder.item.setBackgroundResource(R.drawable.grid_item_default);
            holder.item.setTextColor(Color.parseColor("#333333"));
        }
        return convertView;
    }

    public void updateSelect(int selectedPos){
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView item;
    }
}