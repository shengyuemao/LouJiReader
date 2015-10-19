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
 * 该Activity主要实现网络在线阅读漫画
 * @author Administrator
 *
 */
public class CartoonActivity extends BaseActivity
{
	private RelativeLayout layout1 ;//显示漫画层
	private RelativeLayout layout2 ;//显示操作层
	
	private TextView pagePosition ;//漫画页数显示
	private TextView imageName ; //漫画名称
	private ImageView imageView ; //漫画显示
	private RelativeLayout relativeLayout ; //中间层
	
	private Bitmap bmap = null ;
	private int disWidth ;
	private int disHeight ;
	private LinkedList<String> imageList ;//漫画列表
	private Map<String, String> imagePosition ;//漫画当前位置
	private int imageIndex ;
	private GestureDetector gestureDetector ;
	
	
	private String[] imageArray ;//图片集合
	
	
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
 * 获取手机分辨率，根据大小设置高和宽
 */
public void getDisplayMetrics()
{
	DisplayMetrics dm = new DisplayMetrics() ;
	getWindowManager().getDefaultDisplay().getMetrics(dm) ;
	disWidth = dm.widthPixels ;
	disHeight = dm.heightPixels ;
}

/**
 * 加载网络文件夹下的所有图片
 */
public LinkedList<String> netLoadImages(){
	String picPath = "" ;
	
}

/**
 * 根据intent获得漫画图片的网络路径
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
 * 设置当前图片的位置
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
 * 设置当前图片大小
 */
public void setImageView(int index){
	imagePosition = new HashMap<String, String>() ;
	imagePosition.clear();
	imagePosition.put("positionId", String.valueOf(index)) ;
	
	OnlineImageLoader.displayImage(imageArray[index], imageView);
	setPageInfo() ;
	Utils.resetBitmap();//重置图片属性参数
}

/**
 * 设置图片信息
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

//图片不存在信息
	public void showNoPicMsg() {
		Toast.makeText(this, "没有图片", Toast.LENGTH_SHORT).show();
	}

	// 信息提示
	public void showMsg(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	
	// 信息提示的重载
	public void showMsg(String str) {
		Toast.makeText(this, str,Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 重写并覆盖掉SHOWTIME文件,防止在切换Activity时的线程运作
	 */
	private void setAutoPlayTime(String time){
		String content = "time=" + time;
		Utils.saveFile(Contacts.AUTOSHOWTIME, content, false);
	}
	
	
	/**页面跳转按钮的监听，实现第一页、上一页、下一页、最后一页的跳转*/
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

		// lastPageButton的监听
		public View.OnClickListener lastPageButton = new View.OnClickListener() {

			public void onClick(View v) {
				getArrayAtBitmap("left");
			}
		};
		// nextPageButton的监听
		public View.OnClickListener nextPageButton = new View.OnClickListener() {

			public void onClick(View v) {
				getArrayAtBitmap("right");
			}
		};
}
