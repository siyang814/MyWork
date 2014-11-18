package com.example.mywork.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
/**
 * 			图片处理
 * @author hc360
 *
 */
public class ImageManager {

	// 缩放图片
		public static Bitmap getZoomPicture(Bitmap mBitmap, int width, int height) {
			if (mBitmap.getWidth() == width && mBitmap.getHeight() == height) {
				return mBitmap;
			} else {
				Matrix matrix = new Matrix();
				matrix.postScale(width, height);
				Bitmap fBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
						mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
				return fBitmap;
			}
		}
	
		public static final Bitmap getBitmap(Context context, int resID) {
			return ((BitmapDrawable) context.getResources().getDrawable(resID))
					.getBitmap();
			// Resources res=context.getResources();
			// return BitmapFactory.decodeResource(res, resID);

		}

		public static Bitmap drawableToBitmap(Drawable drawable) {

			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			// canvas.setBitmap(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		}

		public static final Drawable getDrawable(Bitmap bitmap) {
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}

		private byte[] Bitmap2Bytes(Bitmap bm) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
		}

		private Bitmap Bytes2Bimap(byte[] b) {
			if (b.length != 0) {
				return BitmapFactory.decodeByteArray(b, 0, b.length);
			} else {
				return null;
			}
		}
		/**
		 * 			获取Drawable
		 * @param in
		 * @return
		 */
		public Drawable input2Drawable ( InputStream in )
		{
			if ( in != null )
			{
				return  BitmapDrawable.createFromStream(in, "");
			}
			else
			{
				return null;
			}
		}
		/**
		 * 			按大小压缩图片
		 * @param image
		 * @return
		 */
		private Bitmap compressImage(Bitmap image) {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
				baos.reset();//重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;//每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
			return bitmap;
		}
		
		/**
		 * 			按尺寸压缩图片
		 * @param srcPath
		 * @return
		 */
		private Bitmap getimage(String srcPath) {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			//开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
			
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;//这里设置高度为800f
			float ww = 480f;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;//设置缩放比例
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
		}
		/**
		 * 			图片按比例大小压缩方法（根据Bitmap图片压缩）
		 * @param image
		 * @return
		 */
		private Bitmap comp(Bitmap image) {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();		
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出	
				baos.reset();//重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			//开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;//这里设置高度为800f
			float ww = 480f;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;//设置缩放比例
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			isBm = new ByteArrayInputStream(baos.toByteArray());
			bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
		}

		/**
		 * 				根据Url获取图片
		 * @param iv
		 * @param Url
		 * @return
		 */
		public void getUrlBitmap ( ImageView iv, String Url )
		{
			iv.setImageBitmap(getBitmap(Url));
		}
		
		//第一种方法
			public Bitmap getHttpBitmap(String data)
			{
				Bitmap bitmap = null;
				try
				{
					//初始化一个URL对象
					URL url = new URL(data);
					//获得HTTPConnection网络连接对象
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(5*1000);
					connection.setDoInput(true);
					connection.connect();
					//得到输入流
					InputStream is = connection.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
					//关闭输入流
					is.close();
					//关闭连接
					connection.disconnect();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bitmap;
			}
		
		//第二种方法
			public Bitmap getBitmap(String s)
			{
				Bitmap bitmap = null;
				try
				{
					URL url = new URL(s);
					bitmap = BitmapFactory.decodeStream(url.openStream());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bitmap;
			}
		
}
