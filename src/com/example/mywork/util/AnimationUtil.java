package com.example.mywork.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

	public static void slideview(final View view, final float p1, final float p2, final float y, final float yt) {
	    TranslateAnimation animation = new TranslateAnimation(p1, p2, y, yt);
//	    animation.setInterpolator(new OvershootInterpolator());
	    animation.setDuration(600);
//	    animation.setStartOffset(0);
	    animation.setAnimationListener(new Animation.AnimationListener() {
	        @Override
	        public void onAnimationStart(Animation animation) {
	        }
	        
	        @Override
	        public void onAnimationRepeat(Animation animation) {
	        }
	        
	        @Override
	        public void onAnimationEnd(Animation animation) {
	            int left = (int) p2;
	            int top = view.getTop();
	            int width = view.getWidth();
	            int height = view.getHeight();
	            view.clearAnimation();
	            view.layout(left, top, left+width, top+height);
	        }
	    });
	    view.startAnimation(animation);
	}
	
	/**
	 * 		展示
	 * @param v
	 */
	public static void startRightIn ( View v )
	{
		TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        v.startAnimation(mShowAction);
	}
	/**
	 * 		隐藏
	 * @param v
	 */
	public static void startRightOut ( View v )
	{
		TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,   
				0.0f, Animation.RELATIVE_TO_SELF, 1.0f,   
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,   
                0.0f);  
		mHiddenAction.setDuration(500);   
		mHiddenAction.setFillAfter(true);
		v.startAnimation(mHiddenAction);
	}
	/**
	 *     渐显效果
	 * @param v
	 */
	public static void setAlphaIn ( final View v )
	{
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(1000);
		v.startAnimation(alpha);
	}
	/**
	 * 		渐隐效果
	 * @param v
	 */
	public static void setAlphaOut ( final View v )
	{
		
		AlphaAnimation alpha = new AlphaAnimation(1, 0);
		alpha.setDuration(1000);
		v.startAnimation(alpha);
	}
	
}
