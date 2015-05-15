// �Զ����View�ؼ���ʵ���ı�ѡ��
// Leo @ 2010/10/05

package com.main.widgets;

import android.content.Context;   
import android.text.Layout;   
import android.text.Selection;   
import android.util.AttributeSet;
import android.view.ContextMenu;   
import android.view.MenuItem;
import android.view.MotionEvent;   
import android.widget.EditText;   
  
public class CustomTxtView extends EditText {   

	private final static int C_MENU_BEGIN_SELECTION = 0;
    boolean bIsBeginSelecting = false;
    int line = 0;	// ���������
    int off = 0;	// ���������
    
    private class MenuHandler implements MenuItem.OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem item) {
            return onContextMenuItem(item.getItemId());
        }
    }
    
    public boolean onContextMenuItem(int id) {
    	switch (id) {
    	case C_MENU_BEGIN_SELECTION:
    		bIsBeginSelecting = true;
    		setCursorVisible(true);
    		return true;
    	}
    	
		return false;
    }
    
    public CustomTxtView(Context context, AttributeSet attrs) {
        super(context, attrs); //, 16842884
    }
    
    public CustomTxtView(Context context) {   
        super(context);   
    }    
       
    // ������Ļ�����������Ĳ˵�
    @Override  
    protected void onCreateContextMenu(ContextMenu menu) {   
    		MenuHandler handler = new MenuHandler();
    		menu.add(0, C_MENU_BEGIN_SELECTION, 0, "�ı�ѡ��ģʽ").
            setOnMenuItemClickListener(handler);
    }   
       
    @Override  
    public boolean getDefaultEditable() {   
        return false;   
    }   
       
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
    	if (bIsBeginSelecting) {
    		// �ı�ѡ��ģʽ�����⴦��
            int action = event.getAction();   
            Layout layout = getLayout();   

            switch(action) {   
            case MotionEvent.ACTION_DOWN:   
                line = layout.getLineForVertical(getScrollY()+ (int)event.getY());           
                off = layout.getOffsetForHorizontal(line, (int)event.getX());   
                Selection.setSelection(getEditableText(), off);   
                break;   
            case MotionEvent.ACTION_MOVE:   
            case MotionEvent.ACTION_UP:   
                line = layout.getLineForVertical(getScrollY()+(int)event.getY());    
                int curOff = layout.getOffsetForHorizontal(line, (int)event.getX());
                if (curOff > off)
                	Selection.setSelection(getEditableText(), off, curOff);
                else
                	Selection.setSelection(getEditableText(), curOff, off);
            }   
            return true;  
    	} else {
    		super.onTouchEvent(event);
    		return true;
    	}
    }   
    
    // ���ѡ������
    public void clearSelection() {
    	Selection.removeSelection(getEditableText());
		bIsBeginSelecting = false;
		setCursorVisible(false);
    }
    
    public boolean isInSelectMode() {
    	return bIsBeginSelecting;
    }
}  