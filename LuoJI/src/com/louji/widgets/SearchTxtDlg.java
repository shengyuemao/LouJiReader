// �Զ��������Ի���
// Leo @ 2010/10/05

package com.louji.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.louji.base.R;

public class SearchTxtDlg extends Dialog implements OnClickListener{
	 public SearchTxtDlg(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	 
	Button btnConfirm;
	Button btnCancel;
	EditText editSearch;
	String searchKeyword = "";
	  
	public void setDisplay(){
		setContentView(R.layout.readbooksearchdlg);	// ���öԻ���Ĳ���
		btnConfirm = (Button)findViewById(R.id.search_dlg_btn_confirm);
		btnConfirm.setOnClickListener(this);
		btnCancel = (Button)findViewById(R.id.search_dlg_btn_cancel);
		btnCancel.setOnClickListener(this);
		editSearch = (EditText)findViewById(R.id.search_dlg_edit_search);
	    setProperty(); 
	    setTitle("�ؼ�������");				// �趨�Ի���ı���
	    show();								// ��ʾ�Ի���   
	    }
	    
	public void setProperty(){
		Window window = getWindow();		// �õ��Ի���Ĵ���
	    WindowManager.LayoutParams wl = window.getAttributes();
	    wl.x = 0;							
	    wl.y = 0;
	    wl.width = 240;
	    wl.height = 180;
	    wl.alpha = 0.9f;
	    wl.gravity = Gravity.CENTER;         
	    window.setAttributes(wl); 
	    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_dlg_btn_confirm:
			searchKeyword = editSearch.getText().toString();
			dismiss();
			break;
		case R.id.search_dlg_btn_cancel:
			searchKeyword = "";
			dismiss();
			break;
		}
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
}