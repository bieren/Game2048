package com.pac.game2048;

import com.pac.bean.LocalColor;
import com.pac.bean.LocalValue;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 卡片类
 * 2017年5月19日 23:09:39
 * @author pzc
 */
public class Card extends FrameLayout {

	private int num = 0;
	private TextView label;


	public void setLabelColor(int color) {
		label.setBackgroundColor(color);
	}

	public Card(Context context) {
		super(context);
		
		label = new TextView(getContext());
		
		label.setTextSize(32);
		label.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(LocalValue.CARDLEFT, LocalValue.CARDTOP, LocalValue.CARDRIGHT, LocalValue.CARDBOTTOM);
		addView(label, lp);
		
		
		setNum(0);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if (num == 0) {
			label.setText("");
		}else {
			label.setText(num+"");
		}
		
		label.setTextColor(LocalColor.CARDTEXTCOLOR);
		setColor(num);
	}

	private void setColor(int num){
		int j = 0 ;
		int i = 0 ;
		int numTmp = num;
		
		for (; j < num; i++) {
			
			if (j == 0) {
				j = 2;
			}else {
				j = j*2;
			}

			numTmp = numTmp/j;
			
		}
		
		int colorKey = i%8;
		int color ;
		switch (colorKey) {
		case 1:
			color = Color.BLACK;
			break;
		case 2:
			color = Color.DKGRAY;
			break;
		case 3:
			color = Color.GRAY;
			break;
		case 4:
			color = Color.LTGRAY;
			break;
		case 5:
			color = Color.RED;
			break;
		case 6:
			color = Color.GREEN;
			break;
		case 7:
			color = Color.BLUE;
			break;
		default:
			color = Color.BLUE;
			break;
		}
		
		label.setBackgroundColor(color);
		
	}
	
	public boolean equals(Card c){
		if (c == null) {
			return false;
		}
		
		return getNum() == c.getNum();
		
	}
	
	
	
}
