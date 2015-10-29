package com.louji.widgets;
import android.content.Context;  
import android.graphics.Matrix;  
import android.graphics.PointF;  
import android.util.AttributeSet;  
import android.util.FloatMath;  
import android.view.MotionEvent;  
import android.widget.ImageView;  
  
// by ikmb@163.com  
public class ImageTouchView extends ImageView {  
      
    private PointF startPoint = new PointF();  
    private Matrix matrix = new Matrix();  
    private Matrix currentMaritx = new Matrix();  
      
    private int mode = 0;//���ڱ��ģʽ  
    private static final int DRAG = 1;//�϶�  
    private static final int ZOOM = 2;//�Ŵ�  
    private float startDis = 0;  
    private PointF midPoint;//���ĵ�  
      
    /**  
     * Ĭ�Ϲ��캯��  
     * @param context  
     */    
    public ImageTouchView(Context context){    
        super(context);    
    }    
    /**  
     * �ù��췽���ھ�̬����XML�ļ����Ǳ����  
     * @param context  
     * @param paramAttributeSet  
     */    
    public ImageTouchView(Context context,AttributeSet paramAttributeSet){    
        super(context,paramAttributeSet);    
    }  
      
    public boolean onTouchEvent(MotionEvent event) {  
        switch (event.getAction() & MotionEvent.ACTION_MASK) {  
        case MotionEvent.ACTION_DOWN:  
            mode = DRAG;  
            currentMaritx.set(this.getImageMatrix());//��¼ImageView���ڵ��ƶ�λ��  
            startPoint.set(event.getX(),event.getY());//��ʼ��  
            break;  
  
        case MotionEvent.ACTION_MOVE://�ƶ��¼�  
            if (mode == DRAG) {//ͼƬ�϶��¼�  
                float dx = event.getX() - startPoint.x;//x���ƶ�����  
                float dy = event.getY() - startPoint.y;  
                matrix.set(currentMaritx);//�ڵ�ǰ��λ�û������ƶ�  
                matrix.postTranslate(dx, dy);  
                  
            } else if(mode == ZOOM){//ͼƬ�Ŵ��¼�  
                float endDis = distance(event);//��������  
                if(endDis > 10f){  
                    float scale = endDis / startDis;//�Ŵ���  
                    //Log.v("scale=", String.valueOf(scale));  
                    matrix.set(currentMaritx);  
                    matrix.postScale(scale, scale, midPoint.x, midPoint.y);  
                }  
                  
                  
            }  
  
            break;  
              
        case MotionEvent.ACTION_UP:  
            mode = 0;  
            break;  
        //����ָ�뿪��Ļ������Ļ���д���(��ָ)  
        case MotionEvent.ACTION_POINTER_UP:  
            mode = 0;  
            break;  
        //����Ļ���Ѿ��д��㣨��ָ��,����һ����ָѹ����Ļ  
        case MotionEvent.ACTION_POINTER_DOWN:  
            mode = ZOOM;  
            startDis = distance(event);  
              
            if(startDis > 10f){//������ָ����������  
                midPoint = mid(event);  
                currentMaritx.set(this.getImageMatrix());//��¼��ǰ�����ű���  
            }  
              
            break;  
  
  
        }  
        this.setImageMatrix(matrix);  
        return true;  
    }  
      
    /** 
     * ����֮��ľ��� 
     * @param event 
     * @return 
     */  
    private static float distance(MotionEvent event){  
        //�����ߵľ���  
        float dx = event.getX(1) - event.getX(0);  
        float dy = event.getY(1) - event.getY(0);  
        return FloatMath.sqrt(dx*dx + dy*dy);  
    }  
    /** 
     * ��������֮�����ĵ�ľ��� 
     * @param event 
     * @return 
     */  
    private static PointF mid(MotionEvent event){  
        float midx = event.getX(1) + event.getX(0);  
        float midy = event.getY(1) - event.getY(0);  
          
        return new PointF(midx/2, midy/2);  
    }  
  
}  