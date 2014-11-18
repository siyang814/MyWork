package com.example.mywork.imagecache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.mywork.MyApplication;
import com.example.mywork.util.Common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class ImageLoader {

	private MemoryCache memoryCache = new MemoryCache();
	private AbstractFileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	// 线程池
	private ExecutorService executorService;
	private Context context;
	private String userId;

	private boolean isload = false;
	// 加载图片的动画
	AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);

	// 图片最长保存时间
	private final static long IMAGE_MAX_SAVE_TIME = 1000 * 60 * 60 * 24 * 7;
	// 2秒钟
	private final long TIME_TO_REQUEST = 500;
	// url map
	private Map<String, String> urlmap = Collections
			.synchronizedMap(new WeakHashMap<String, String>());

	private static ImageLoader Instance;

	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(3);
		this.context = context;
		animation.setFillEnabled(true);
		animation.setFillAfter(true);
		animation.setDuration(2000);
	}

	public static ImageLoader getInstance() {
		if (Instance == null)
			Instance = new ImageLoader(MyApplication.instance.getContext());
		return Instance;
	}

	// 最主要的方法
	public void DisplayImage(String url, ImageView imageView,
			boolean isLoadOnlyFromCache, String userId) {
		this.userId = userId;
		String tagUrl = imageViews.get(imageView);
		//判断是否是同一个图片的多次请求
		if ( !Common.isNull(tagUrl) && tagUrl.equals(url))
		{
			return;
		}
		imageViews.put(imageView, url);
		// 先从内存缓存中查找
		ColorDrawable colorDrawable = new ColorDrawable(0X00FFFFFF);
		imageView.setImageDrawable(colorDrawable);
		Bitmap bitmap = memoryCache.get(url);

		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			if ( imageViews.containsKey(imageView))
			{
				imageViews.remove(imageView);
			}
		} else if (!isLoadOnlyFromCache) {
			// 若没有的话则开启新线程加载图片
			queuePhoto(url, imageView);
		} else if (bitmap == null) {

		}
	}

	/**
	 * 判断是否需要从新加载图片
	 * 
	 * @param url
	 * @return true 是， false 不
	 */
	private boolean checkImageRequest(String url) {
		// 是否请求过此图片
		if (urlmap.containsKey(url)) {
			// 是否过期
			if (checkUrlDirty(Long.parseLong(urlmap.get(url)))) {
				urlmap.put(url, "" + System.currentTimeMillis());
				return true;
			} else {
				return false;
			}
		}
		urlmap.put(url, "" + System.currentTimeMillis());
		return true;
	}

	private void queuePhoto(String url, ImageView imageView) {
//		 if ( !checkImageRequest(url) )
//		 {
//			 return;
//		 }
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	public void setIsLoad(boolean isload) {
		this.isload = isload;

	}

	public void deletimg(String url) {
		File f = fileCache.getFile(url);
		f.delete();
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		// 先从文件缓存中查找是否有
		Bitmap b = null;
		if (f != null && f.exists()) {
			// 检查图片是否过期，如果过期则删除它
			if (checkImageDirty(f.lastModified())) {
				f.delete();
				return null;
			}
			b = decodeFile(f);
		}

		if (isload == false) {
			if (b != null) {
				return b;
			}
		}
		// 最后从指定的url中下载图片
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;

		} catch (Exception ex) {
			Log.e("",
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			onFail();
			// if (b != null) {
			// return BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.grid_icon);

			// } else {
			// // BitmapDrawable colorDrawable = (BitmapDrawable) context
			// // .getResources().getDrawable(R.drawable.ic_launcher);
			// //
			// // BitmapDrawable bd = (BitmapDrawable) colorDrawable;
			// //
			// // Bitmap bm = bd.getBitmap();
			// return bm;
			// }
			return b;
		} finally {
			onSuccess();
		}
	}

	/**
	 * 检测图片是否过期
	 * 
	 * @param lastmodifytime
	 * @return
	 */
	private boolean checkImageDirty(long lastmodifytime) {
		long curtime = System.currentTimeMillis();
		return (curtime - lastmodifytime) > IMAGE_MAX_SAVE_TIME ? true : false;
	}

	/**
	 * 检测URL是否过期
	 * 
	 * @param lastmodifytime
	 * @return true 过期， false 没有
	 */
	private boolean checkUrlDirty(long lastmodifytime) {
		long curtime = System.currentTimeMillis();
		return (curtime - lastmodifytime) > TIME_TO_REQUEST ? true : false;
	}

	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	private Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			// while (true) {
			// if (width_tmp / 2 < REQUIRED_SIZE
			// || height_tmp / 2 < REQUIRED_SIZE)
			// break;
			// width_tmp /= 2;
			// height_tmp /= 2;
			// scale *= 2;
			// }

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		} finally {
		}
		return null;
	}

	private OnSuccessListener onSuccessListener;

	public void onSuccess() {
		if (onSuccessListener != null) {
			onSuccessListener.onSuccess();
		}
	}

	/**
	 * 创建图片加载成功接口
	 * 
	 * @author wanpg
	 * 
	 */
	public interface OnSuccessListener {

		public void onSuccess();
	}

	private OnFailListener onFailListener;

	public void onFail() {
		if (onFailListener != null) {
			onFailListener.onFail();
		}
	}

	/**
	 * 创建图片加载失败接口
	 * 
	 * @author wanpg
	 * 
	 */
	public interface OnFailListener {
		public void onFail();
	}

	public void setOnFailListener(OnFailListener onFailListener) {
		this.onFailListener = onFailListener;
	}

	public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
		this.onSuccessListener = onSuccessListener;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);

			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			// 更新的操作放在UI线程中
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	/**
	 * 防止图片错位
	 * 
	 * @param photoToLoad
	 * @return
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// 用于在UI线程中更新界面
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				animationLoad(photoToLoad.imageView, bitmap);
			// photoToLoad.imageView.setImageBitmap(bitmap);

		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			Log.e("", "CopyStream catch Exception...");
		}
	}

	// 图片加载动画
	private void animationLoad(ImageView iv, Bitmap bitmap) {
		if ( imageViews.containsKey(iv))
		{
			imageViews.remove(iv);
		}
		iv.setImageBitmap(bitmap);
		 iv.startAnimation(animation);
	}

}