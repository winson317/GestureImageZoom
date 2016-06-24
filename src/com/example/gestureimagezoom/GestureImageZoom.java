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
 * ʹ��android�����Ƽ��ֻ���������裺
 * 1������һ��GestureDetector ���󣬴����ö���ʱ����ʵ��һ��GestureDetector.OnGestureListener������ʵ��.
 * 2��ΪӦ�ó����Activity��TouchEvent�¼��󶨼����������¼�������ָ����Activity�ϵ�TouchEvent�¼�����GestureDetector����.
 */

public class GestureImageZoom extends Activity implements OnGestureListener{
	
	GestureDetector detector;  //�������Ƽ����ʵ��
	ImageView imageView;
	Bitmap bitmap;  //��ʼ��ͼƬ��Դ
	int width, height; //����ͼƬ�Ŀ�͸�
	float currentScale = 1; //��¼��ǰ�����ű�
	Matrix matrix; //����ͼƬ���ŵ�Matrix����

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        detector = new GestureDetector(this); //�������Ƽ����
        imageView = (ImageView)findViewById(R.id.showImage);
        matrix = new Matrix(); 
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.flower); //��ȡ�����ŵ�ԴͼƬ
        width = bitmap.getWidth(); //���λͼ�Ŀ�
        height = bitmap.getHeight(); //���λͼ��
        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.flower)); //����ImageView��ʼ��ʱ��ʾ��ͼƬ
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	return detector.onTouchEvent(event); //����Activity�ϵĴ����¼�����GestureDetector����
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
		currentScale += currentScale * velocityX / 4000.0f; //�������Ƶ��ٶ����������űȣ����velocityX>0,�Ŵ�ͼ�񣬷�����Сͼ��
		currentScale = currentScale > 0.01 ? currentScale : 0.01f; //��֤currentScale�������0
		matrix.reset(); //����matrix
		matrix.setScale(currentScale, currentScale, 160, 200); //����matrix
		BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
		//���ͼƬ��δ���գ���ǿ�ƻ��ո�ͼƬ
		if (!bitmapDrawable.getBitmap().isRecycled())
		{
			bitmapDrawable.getBitmap().recycle();
		}
		//����ԭʼλͼ��matrix������ͼƬ
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		imageView.setImageBitmap(bitmap2); //��ʾ�µ�λͼ
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
