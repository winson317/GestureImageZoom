package com.example.gestureimagezoom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

/*
 * 使用android的手势检测只需两个步骤：
 * 1、创建一个GestureDetector 对象，创建该对象时必须实现一个GestureDetector.OnGestureListener监听器实例.
 * 2、为应用程序的Activity的TouchEvent事件绑定监听器，在事件处理中指定把Activity上的TouchEvent事件交给GestureDetector处理.
 */

public class GestureImageZoom extends Activity implements OnGestureListener{
	
	GestureDetector detector;  //定义手势检测器实例
	ImageView imageView;
	Bitmap bitmap;  //初始的图片资源
	int width, height; //定义图片的宽和高
	float currentScale = 1; //记录当前的缩放比
	Matrix matrix; //控制图片缩放的Matrix对象

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        detector = new GestureDetector(this); //创建手势检测器
        imageView = (ImageView)findViewById(R.id.showImage);
        matrix = new Matrix(); 
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.flower); //获取被缩放的源图片
        width = bitmap.getWidth(); //获得位图的宽
        height = bitmap.getHeight(); //获得位图高
        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.flower)); //设置ImageView初始化时显示的图片
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	return detector.onTouchEvent(event); //将该Activity上的触碰事件交给GestureDetector处理
    }

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		velocityX = velocityX > 4000 ? 4000 : velocityX;
		velocityX = velocityX <-4000 ? -4000 : velocityX;
		currentScale += currentScale * velocityX / 4000.0f; //根据手势的速度来计算缩放比，如果velocityX>0,放大图像，否则缩小图像
		currentScale = currentScale > 0.01 ? currentScale : 0.01f; //保证currentScale不会等于0
		matrix.reset(); //重置matrix
		matrix.setScale(currentScale, currentScale, 160, 200); //缩放matrix
		BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
		//如果图片还未回收，先强制回收该图片
		if (!bitmapDrawable.getBitmap().isRecycled())
		{
			bitmapDrawable.getBitmap().recycle();
		}
		//根据原始位图和matrix创建新图片
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		imageView.setImageBitmap(bitmap2); //显示新的位图
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
