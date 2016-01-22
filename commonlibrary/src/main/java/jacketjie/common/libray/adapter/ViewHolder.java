package jacketjie.common.libray.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import jacketjie.common.libray.R;


public class ViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private DisplayImageOptions options;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.plugin_camera_no_pictures)
		.showImageOnFail(R.drawable.plugin_camera_no_pictures)
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(false)// 设置下载的图片是否缓存在内存中 
		.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位  
		.displayer(new FadeInBitmapDisplayer(100))//设置图片渐显的时间
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		 .build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder(context, parent, layoutId, position);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView()
	{
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		if (view !=null) {
			view.setText(text);
		}
		return this;
	}
	/**
	 * 设置指定view的可见性
	 * @param viewId
	 * @param visiable
	 * @return
	 */
	public ViewHolder setVisiable(int viewId,boolean visiable){
		View view = getView(viewId);
		if (visiable) {
			view.setVisibility(View.VISIBLE);
		}else{
			view.setVisibility(View.GONE);
			
		}
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * @param viewId
	 * @param url
	 * @return
	 */
	public ViewHolder setImageByImageLoader(int viewId, String url)
	{
		ImageView view = getView(viewId);
		ImageLoader.getInstance().displayImage(url, view, options, new AnimateFirstDisplayListener());
		return this;
	}

	/**
	 *  为ImageView设置图片
	 * @param viewId
	 * @param bm
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	public int getPosition()
	{
		return mPosition;
	}

}
