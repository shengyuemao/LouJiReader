package com.louji.cartoon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.louji.base.R;
import com.louji.contacts.Contacts;
import com.louji.util.OnlineImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.mythos.android.MainActivity;
import cn.com.mythos.android.Contents.Contents;
import cn.com.mythos.android.Contents.Utils;
/**
 * ��Activity��Ҫʵ�����������Ķ�����
 * @author Administrator
 *
 */
public class CartoonActivity extends BaseActivity
{
	private RelativeLayout layout1 ;//��ʾ������
	private RelativeLayout layout2 ;//��ʾ������
	
	private TextView pagePosition ;//����ҳ����ʾ
	private TextView imageName ; //��������
	private ImageView imageView ; //������ʾ
	private RelativeLayout relativeLayout ; //�м��
	
	private Bitmap bmap = null ;
	private int disWidth ;
	private int disHeight ;
	private LinkedList<String> imageList ;//�����б�
	private Map<String, String> imagePosition ;//������ǰλ��
	private int imageIndex ;
	private GestureDetector gestureDetector ;
	
	
	private String[] imageArray ;//ͼƬ����
	
	
@Override
protected void onCreate(Bundle savedInstanceState)
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cartoon) ;
	
	layout1 = (RelativeLayout)findViewById(R.id.layout1) ;
	layout2 = (RelativeLayout)findViewById(R.id.layout2) ;
	
	imageName = (TextView)findViewById(R.id.cartoon_imageName) ;
	pagePosition = (TextView)findViewById(R.id.cartoon_pagePosition) ;
	imageView = (ImageView)findViewById(R.id.cartoon_imageView) ;
	relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout_main) ;
}

/**
 * ��ȡ�ֻ��ֱ��ʣ����ݴ�С���øߺͿ�
 */
public void getDisplayMetrics()
{
	DisplayMetrics dm = new DisplayMetrics() ;
	getWindowManager().getDefaultDisplay().getMetrics(dm) ;
	disWidth = dm.widthPixels ;
	disHeight = dm.heightPixels ;
}

/**
 * ���������ļ����µ�����ͼƬ
 */
public LinkedList<String> netLoadImages(){
	String picPath = "" ;
	
}

/**
 * ����intent�������ͼƬ������·��
 */
public String getNetPicPath(){
	String picPath = "" ;
	Bundle bundle = this.getIntent().getExtras() ;
	if(bundle != null && bundle.size() > 0 ){
		picPath = bundle.getString("picUrl") ;
		
	}else {
		picPath = Utils.getFileRead(Contacts.SHOWHISTORY) ;
	}
	return picPath ;
}

/**
 * ���õ�ǰͼƬ��λ��
 */
public int getPicPosition(String picPath,String[] imagesArray){
	int position = 0 ;
	for(int i = 0 ; i < imagesArray.length ; i++ ){
		if(picPath.equals(imagesArray[i])){
			position = i ;
			break ;
		}
	}
	return position ;
}

/**
 * ���õ�ǰͼƬ��С
 */
public void setImageView(int index){
	imagePosition = new HashMap<String, String>() ;
	imagePosition.clear();
	imagePosition.put("positionId", String.valueOf(index)) ;
	
	OnlineImageLoader.displayImage(imageArray[index], imageView);
	setPageInfo() ;
	Utils.resetBitmap();//����ͼƬ���Բ���
}

/**
 * ����ͼƬ��Ϣ
 */
public void setPageInfo(){
	if (imageList != null && imageList.size() > 0) {
		if (imagePosition != null && imagePosition.size() > 0) {
			String picPath = Utils.getImagePath(imagePosition, imageList);
			onPause();
			if (picPath != null && picPath.length() > 0) {
				String picName = picPath.substring(
						picPath.lastIndexOf("/") + 1, picPath.length());
				imageName.setText(picName);
			}
			String page = Utils.getImagePagePosition(imagePosition,
					imageList);
			pagePosition.setText(page);
		}
	} else {
		showNoPicMsg();
	}
}

//ͼƬ��������Ϣ
	public void showNoPicMsg() {
		Toast.makeText(this, "û��ͼƬ", Toast.LENGTH_SHORT).show();
	}

	// ��Ϣ��ʾ
	public void showMsg(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	
	// ��Ϣ��ʾ������
	public void showMsg(String str) {
		Toast.makeText(this, str,Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ��д�����ǵ�SHOWTIME�ļ�,��ֹ���л�Activityʱ���߳�����
	 */
	private void setAutoPlayTime(String time){
		String content = "time=" + time;
		Utils.saveFile(Contacts.AUTOSHOWTIME, content, false);
	}
	
	
	/**ҳ����ת��ť�ļ�����ʵ�ֵ�һҳ����һҳ����һҳ�����һҳ����ת*/
		private View.OnClickListener pageButton = new View.OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						CartoonActivity.this);
				dialog.setTitle(R.string.pageTitle);
				String[] PAGEARRAY = new String[] { getString(R.string.firstPage),
						getString(R.string.beforePage),
						getString(R.string.nextPage), getString(R.string.lastPage) };
				dialog.setItems(PAGEARRAY, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(imagePosition != null && imagePosition.size() > 0) {
							int position = Integer.parseInt(imagePosition
									.get("positionId"));
							switch (which) {
							case 0:
								if (position > 0) {
									imageIndex = 0;
									setImageView(imageIndex);
									showMsg((imageIndex + 1) + "/" + imageArray.length);
								} else {
									showMsg(R.string.pageFirst);
								}
								break;
							case 1:
								if (position >= 1) {
									imageIndex = position - 1;
									setImageView(imageIndex);
									showMsg((imageIndex + 1) + "/" + imageArray.length);
								} else {
									showMsg(R.string.pageFirst);
								}
								break;
							case 2:
								if (position < imageArray.length - 1) {
									imageIndex = position + 1;
									setImageView(imageIndex);
									showMsg((imageIndex + 1) + "/" + imageArray.length);
								} else if (position == imageArray.length - 1) {
									showMsg(R.string.pageEnd);
								}
								break;
							case 3:
								if (position != imageArray.length - 1) {
									imageIndex = imageArray.length - 1;
									setImageView(imageIndex);
									showMsg((imageIndex + 1) + "/" + imageArray.length);
								} else {
									showMsg(R.string.pageEnd);
								}
								break;
							}
						}
					}
				});
				dialog.setNegativeButton(R.string.pageCancel,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();

							}
						});
				dialog.show();
			}
		};

		// lastPageButton�ļ���
		public View.OnClickListener lastPageButton = new View.OnClickListener() {

			public void onClick(View v) {
				getArrayAtBitmap("left");
			}
		};
		// nextPageButton�ļ���
		public View.OnClickListener nextPageButton = new View.OnClickListener() {

			public void onClick(View v) {
				getArrayAtBitmap("right");
			}
		};
}
