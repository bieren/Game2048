package com.pac.game2048;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.pac.bean.LocalColor;
import com.pac.bean.LocalValue;

public class GameView extends GridLayout {

	private Card[][] cardMap = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
//	false 未增加随机数    true 已增加随机数
	private boolean hadAddPoint = false;
	

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView() {

		setColumnCount(LocalValue.COLUMNCOUNT);
		setBackgroundColor(LocalColor.BACKGROUNDCOLOR);
		/**
		 * 动作侦听
		 */
		setOnTouchListener(new View.OnTouchListener() {
			private float startX;
			private float startY;
			private float offsetX;
			private float offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				 Card[][] oleCardMap = new Card[4][4];

//				 for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {
//
//						for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {

//							oleCardMap = cardMap;
//						}
//
//					}
				 
				 
				
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					startX = event.getX();
					startY = event.getY();

					break;
				case MotionEvent.ACTION_UP:

					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					
					GameView.this.hadAddPoint =false;
					
					if (Math.abs(offsetX) > Math.abs(offsetY)) {

						if (offsetX < -LocalValue.ERRORVALUE) {
							swipeLeft(false);
						} else if (offsetX > LocalValue.ERRORVALUE) {
							swipeRight(false);
						}
					} else {
						if (offsetY < -LocalValue.ERRORVALUE) {
							// System.out.println(offsetX+","+offsetY);
							// System.out.println(startX+","+startY);
							swipeUp(false);
						} else if (offsetY > LocalValue.ERRORVALUE) {
							// System.out.println(offsetX+","+offsetY);
							// System.out.println(startX+","+startY);
							swipeDown(false);
						}
					}

					if (emptyPoints.size() >= LocalValue.COLUMNCOUNTY
							* LocalValue.COLUMNCOUNTX) {
						startGame();
					}
					
					
					
					if (checkGame()) {
						addRandom();
					}

//					 for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {
//
//						for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {
//							if (!oleCardMap[y][x].equals(cardMap[y][x])) {
//								addRandom();
//							}
//						}
//
//					}
					 
					
					
					
					
					
					break;
				default:
					break;
				}

				return true;
			}
		});

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h) - LocalValue.SPACE)
				/ LocalValue.COLUMNCOUNT;

		addCard(cardWidth, cardWidth);

		startGame();

		addRandom();
		addRandom();
		addRandom();
		addRandom();
		addRandom();
		addRandom();
		addRandom();
		addRandom();
	}

	private boolean checkGame() {

		if ((emptyPoints.size() > 0)||(swipeLeft(true) || swipeRight(true) || swipeUp(true) || swipeDown(true))) {
			return true;
		}

		return false;
	}

	private void startGame() {

		for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {

			for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {

				cardMap[x][y].setNum(LocalValue.INITNUMBER_0);
			}

		}
	}

	/**
	 * 
	 * @param cardWidth
	 *            宽
	 * @param cardHight
	 *            高
	 */
	private void addCard(int cardWidth, int cardHight) {
		Card c;

		for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {

			for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {
				c = new Card(getContext());
				c.setNum(LocalValue.INITNUMBER_0);
				addView(c, cardWidth, cardHight);
				cardMap[x][y] = c;
			}

		}
	}

	private void addRandom() {

		emptyPoints.clear();

		// int x = (int)(Math.random()*3);
		// int y = (int)(Math.random()*3);

		for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {
			for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {
				if (cardMap[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		Point p = emptyPoints
				.remove((int) (Math.random() * emptyPoints.size()));

		cardMap[p.x][p.y]
				.setNum(Math.random() > LocalValue.TWOCHANCE ? LocalValue.INITNUMBER_2
						: LocalValue.INITNUMBER_4);
		cardMap[p.x][p.y].setLabelColor(Color.GREEN);
		this.hadAddPoint = true ;
	}

	private boolean swipeLeft(boolean flag) {

		for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {
			for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {

				for (int x1 = x + 1; x1 < LocalValue.COLUMNCOUNTX; x1++) {

					if (cardMap[x1][y].getNum() > 0) {

						if (cardMap[x][y].getNum() <= 0) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y].setNum(cardMap[x1][y].getNum());

								cardMap[x1][y].setNum(LocalValue.INITNUMBER_0);
								x--;
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y]
										.setNum(cardMap[x][y].getNum() * 2);
								cardMap[x1][y].setNum(LocalValue.INITNUMBER_0);
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						}

					}

				}

			}
		}
		return false;
		// System.out.println("Left");
	}

	private boolean swipeRight(boolean flag) {

		for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {
			for (int x = 3; x >= 0; x--) {

				for (int x1 = x - 1; x1 >= 0; x1--) {

					if (cardMap[x1][y].getNum() > 0) {

						if (cardMap[x][y].getNum() <= 0) {

							if (flag) {
								return true;
							} else {
								cardMap[x][y].setNum(cardMap[x1][y].getNum());

								cardMap[x1][y].setNum(LocalValue.INITNUMBER_0);
								x++;
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {

							if (flag) {
								return true;
							} else {
								cardMap[x][y]
										.setNum(cardMap[x][y].getNum() * 2);
								cardMap[x1][y].setNum(LocalValue.INITNUMBER_0);
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						}
					}

				}

			}
		}

		// System.out.println("Right");
		return false;

	}

	private boolean swipeUp(boolean flag) {

		for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {
			for (int y = 0; y < LocalValue.COLUMNCOUNTY; y++) {

				for (int y1 = y + 1; y1 < LocalValue.COLUMNCOUNTY; y1++) {

					if (cardMap[x][y1].getNum() > 0) {

						if (cardMap[x][y].getNum() <= 0) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y].setNum(cardMap[x][y1].getNum());

								cardMap[x][y1].setNum(LocalValue.INITNUMBER_0);
								y--;
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y]
										.setNum(cardMap[x][y].getNum() * 2);
								cardMap[x][y1].setNum(LocalValue.INITNUMBER_0);
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						}

					}

				}

			}
		}
		return false;
		// System.out.println("Up");
	}

	private boolean swipeDown(boolean flag) {

		for (int x = 0; x < LocalValue.COLUMNCOUNTX; x++) {
			for (int y = 3; y >= 0; y--) {

				for (int y1 = y - 1; y1 >= 0; y1--) {

					if (cardMap[x][y1].getNum() > 0) {

						if (cardMap[x][y].getNum() <= 0) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y].setNum(cardMap[x][y1].getNum());

								cardMap[x][y1].setNum(LocalValue.INITNUMBER_0);
								y++;
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							if (flag) {
								return true;
							} else {
								cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
								cardMap[x][y1].setNum(LocalValue.INITNUMBER_0);
								if (!this.hadAddPoint) {
									addRandom();
								}
								break;
							}
						}

					}

				}

			}
		}
		return false;
		// System.out.println("Down");

	}

}
