package com.example.mywork.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.mywork.MyApplication;
/**
 * 			定位类
 * @author hc360
 *
 */
public class LocationManager {

	public LocationClient mLocationClient = null;
	
	private static LocationManager Instance;
	
	private OnBackResult result;
	
	public static LocationManager getInstance ()
	{
		if ( Instance == null ) Instance = new LocationManager();
		return Instance;
	}
	
	public void startLocation ( OnBackResult result )
	{
		if ( isStarting() )
			return;
		
		this.result = result;
		
		mLocationClient = new LocationClient(MyApplication.instance.getContext());

		mLocationClient.registerLocationListener(new MyLocationListenner());

		setLocationOption();
		
		mLocationClient.start();
	}
	
	private void cancel ()
	{
		if ( mLocationClient != null && mLocationClient.isStarted() )
		{
			mLocationClient.stop();
		}
		mLocationClient = null;
		if ( result != null ) result = null;
	}
	
	/**
	 * 设置相关参数
	 */
	private void setLocationOption() {

		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setCoorType("bd09ll");

//		option.setOpenGps(true);

		option.setIsNeedAddress(true);

//		option.setLocationNotify(true);
		
		

		// option.setPriority(LocationClientOption.LocationMode.);

		// option.setAddrType("all");// 返回的定位结果包含地址信息

		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02

		option.setScanSpan(900);// 设置发起定位请求的间隔时间为5000ms

		// option.disableCache(true);// 禁止启用缓存定位

		// option.setPoiNumber(5); // 最多返回POI个数

		// option.setPoiDistance(1000); // poi查询距离

		// option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * 监听函数，有新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			double latitude = location.getLatitude();
			location.getLocType();
			double longitude = location.getLongitude();
			
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());
			String address = location.getAddrStr();
			
			if ( result != null )
			{
				result.Handler(latitude, longitude, address);
			}
			
			cancel();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}

		}
	}
	
	private boolean isStarting ()
	{
		if ( mLocationClient != null && mLocationClient.isStarted() )
		{
			return true;
		}
		return false;
	}
	
	//接口返回经纬度
	public interface OnBackResult
	{
		public void Handler (double latitude, double longitude, String address );
	}
	
}
