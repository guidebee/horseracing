package utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ui.MyGameView;

import com.example.xin_game_horse2.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * ��ô������ƥ�������ƶ�ʱ��������������µ��ƶ���ת�� �뷨��Ҫÿƥ����һ����λ�ã����������ƶ�
 * Ҫ���ÿƥ�����˳��Ȼ�����˳���ƶ�����ͬ�ķ�λ������ÿ�ζ���˳�ţ�
 * 
 * �뷨2:�������ƶ��������ڶ�����ʱ����ÿƥ����ٶȣ�ͷ��Ҫ��300��2��Ҫ��280��ÿ�����μ�20 ��Ҫ��취��ȡ��ÿƥ�������
 * 
 * @author Administrator
 * 
 */

public class BackGroundManager {
	private Bitmap background = null;// ��Ƭ
	private Bitmap background2 = null;
	private Bitmap outlineBitmap = null;// ��Χ��ɫ����
	private Bitmap frameBitmap = null;// ��ɫѹע������
	private Bitmap lastoddsBitmap = null;// �м�����������ͼƬ
	private Bitmap last5rBitmap = null;// ���Ͻ�����ͼƬ
	private Bitmap blueFontBitmap = null;// ����ͼƬ��
	private Bitmap yellowFontBitmap = null;
	private Bitmap redFontBitmap = null;
	private Bitmap rebet01Bitmap = null;// ��ע������ť
	private Bitmap rebet02Bitmap = null;
	private Bitmap wonBitmap = null;// ����н�����
	private Bitmap coinBitmap = null;// ������ܷ�
	private Bitmap start01Bitmap = null;// ��ʼ
	private Bitmap start02Bitmap = null;
	private Bitmap b1Bitmap = null;
	private int x = 0;
	private int x1;

	private Bitmap door_frameBitmap = null;
	private Bitmap door_openBitmap = null;
	private Bitmap end_pointBitmap = null;
	private Bitmap track_background01Bitmap = null;
	private Bitmap track_background02Bitmap = null;
	private Bitmap track_foreground01Bitmap = null;
	private Bitmap track_foreground02Bitmap = null;

	private Bitmap horseWhiteBitmap = null;
	private Bitmap horsePurpleBitmap = null;
	private Bitmap horseRedBitmap = null;
	private Bitmap horseBlueBitmap = null;
	private Bitmap horseYellowBitmap = null;
	private Bitmap horseGreenBitmap = null;

	float mScreenWidth, mScreenHeight;

	float xLeft;// ��ע��һ������
	float yLeft;// ��ע��һ������
	int count = 0;// ѹע���±�
	private int wonNum = 0;

	public BackGroundManager(View view, float screenWidth, float screenHeight,
			int x1, float xLeft, float yLeft) {
		b1Bitmap = readBitMap(view.getContext(), R.drawable.b1);
		background = readBitMap(view.getContext(), R.drawable.background);
		outlineBitmap = readBitMap(view.getContext(), R.drawable.outline);
		frameBitmap = readBitMap(view.getContext(), R.drawable.frame);
		lastoddsBitmap = readBitMap(view.getContext(), R.drawable.lastodds);
		last5rBitmap = readBitMap(view.getContext(), R.drawable.last5r);

		// ��������ͼƬ��(�����ƣ��죩
		blueFontBitmap = readBitMap(view.getContext(), R.drawable.race_blue_0);
		yellowFontBitmap = readBitMap(view.getContext(),
				R.drawable.race_yellow_0);
		redFontBitmap = readBitMap(view.getContext(), R.drawable.race_red_0);

		rebet01Bitmap = readBitMap(view.getContext(), R.drawable.adjust_score01);
		rebet02Bitmap = readBitMap(view.getContext(), R.drawable.adjust_score02);

		wonBitmap = readBitMap(view.getContext(), R.drawable.won);

		coinBitmap = readBitMap(view.getContext(), R.drawable.coin);

		start01Bitmap = readBitMap(view.getContext(), R.drawable.start01);
		start02Bitmap = readBitMap(view.getContext(), R.drawable.start02);

		door_frameBitmap = readBitMap(view.getContext(), R.drawable.door_frame);
		door_openBitmap = readBitMap(view.getContext(), R.drawable.door_open);
		end_pointBitmap = readBitMap(view.getContext(), R.drawable.end_point);
		track_background01Bitmap = readBitMap(view.getContext(),
				R.drawable.track_background01);
		track_background02Bitmap = readBitMap(view.getContext(),
				R.drawable.track_background02);
		track_foreground01Bitmap = readBitMap(view.getContext(),
				R.drawable.track_foreground01);
		track_foreground02Bitmap = readBitMap(view.getContext(),
				R.drawable.track_foreground02);

		horseWhiteBitmap = readBitMap(view.getContext(), R.drawable.white_blue);
		horsePurpleBitmap = readBitMap(view.getContext(), R.drawable.purple_red);
		horseRedBitmap = readBitMap(view.getContext(), R.drawable.red_blue);
		horseBlueBitmap = readBitMap(view.getContext(), R.drawable.blue_red);
		horseYellowBitmap = readBitMap(view.getContext(),
				R.drawable.yellow_blue);
		horseGreenBitmap = readBitMap(view.getContext(), R.drawable.green_red);

		mScreenWidth = screenWidth;
		mScreenHeight = screenHeight;
		this.x1 = x1;

		this.xLeft = xLeft;
		this.yLeft = yLeft;

	}

	// ��ȡͼƬ����ֱ��ʹ��BitmapFactory.decodeResource������ش�ͼƬʱoom
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	private int countFrame = 0;// �Լ�5�����󣬿�ʼ����

	private int moveX = 0;
	private int moveRightDownX = 0;
	private int moveLeftDownX = 0;
	private int moveY = 0;

	private int[] moveHorses = new int[6];

	private boolean isRight = true;
	private boolean isRight2 = false;
	private boolean isRightDown = false;
	private boolean isDown = false;
	private boolean isLeftDown = false;
	public static boolean isEnd = false;
	private boolean isGetSort = true;
	private int countEnd = 0;

	public void moveTo() {
		if (Math.abs(moveX) + 80 < mScreenWidth * 3 && isRight) {// �����ƶ��������3����
			moveX = moveX - 80;
			if (Math.abs(moveX) + 80 >= mScreenWidth * 2) {// �����ƶ���һ��λ��ʱ���ٶ�Ҫ�㶨����
				isRight2 = true;
				if (isGetSort) {
					isGetSort = false;
					getHorseSpeedSort();
				}
			}
		} else {// �����ƶ�
			isRight = false;
			if (Math.abs(moveY) + moveRightDownX < mScreenWidth * 2 / 3) {// �����ƶ�
				isRightDown = true;
				moveRightDownX = moveRightDownX + 80;
			} else {
				isRightDown = false;
				if (Math.abs(moveY) < mScreenHeight * 2 - mScreenHeight / 3) {// ֱ��
					isDown = true;
				} else {// ����
					isDown = false;
					isLeftDown = true;
					// moveLeftDownX = moveLeftDownX - 40;
				}
			}

			if (Math.abs(moveY) < mScreenHeight * 3 - mScreenHeight / 3) {
				moveY = moveY - 80;
			} else {
				isLeftDown = false;
				if (moveX < 0) {
					moveX = moveX + 80;
				} else if ((moveHorses[0] + moveRightDownX) <= mScreenWidth / 4
						|| (moveHorses[1] + moveRightDownX) <= mScreenWidth / 4
						|| (moveHorses[2] + moveRightDownX) <= mScreenWidth / 4
						|| (moveHorses[3] + moveRightDownX) <= mScreenWidth / 4
						|| (moveHorses[4] + moveRightDownX) <= mScreenWidth / 4
						|| (moveHorses[5] + moveRightDownX) <= mScreenWidth / 4) {

					isEnd = true;

					countEnd++;
					// �����յ㣬����10�£��Զ���ת��playStyle=3���棬���Ѳ�����0,�����´β���
					if (countEnd >= 10) {
						countEnd = 0;
						countFrame = 0;
						moveX = 0;
						moveY = 0;
						isRight = true;
						isRight2 = false;
						isGetSort = true;
						moveHorses[0] = 0;
						moveHorses[1] = 0;
						moveHorses[2] = 0;
						moveHorses[3] = 0;
						moveHorses[4] = 0;
						moveHorses[5] = 0;
						moveRightDownX = 0;
						moveLeftDownX = 0;
						MyGameView.playStyle = 3;
						MyGameView.isPlay = false;
						isEnd = false;
					}
				}
			}
		}

	}

	int horseRightNum = 0;
	int horseRightDownNum = 0;
	int horseDownNum = 0;
	int horseLeftDownNum = 0;
	int horseLeftNum = 0;
	public static boolean isOne = true;
	// �����������playStyle=3�������ʼ����Ļ���Ʒ���playStyle=1
	public void drawTrackMoveDown(Canvas canvas) {

		drawResult(canvas);
		canvas.drawBitmap(b1Bitmap, null, new RectF(0, Content.MoveY
				- mScreenHeight, mScreenWidth, Content.MoveY), null);//
		
		if(isOne){
			Content.coin = Content.coin+Content.resultWonNum;
			isOne = false;
		}
		// ��ҷ���
		drawCoin2(canvas);
		// // �н�����
		// drawWon(canvas);
		// // ����������
		// drawOdds(canvas);
		// �����
		drawResult(canvas);
	}

	// ��������ʼ����Ļ����
	public void drawTrackMoveLeft(Canvas canvas) {
		canvas.drawBitmap(track_background02Bitmap, null, new RectF(
				Content.MoveX + mScreenWidth, moveY, Content.MoveX
						+ mScreenWidth * 3, moveY + mScreenHeight * 11 / 3),
				null);//

		canvas.drawBitmap(track_foreground02Bitmap, null, new RectF(
				Content.MoveX + mScreenWidth, moveY, Content.MoveX
						+ mScreenWidth * 3, moveY + mScreenHeight * 4
						- mScreenHeight / 3), null);//

		canvas.drawBitmap(horseWhiteBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 1 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 3 / 9), null);// xͼƬ
		canvas.drawBitmap(horsePurpleBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 2 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 4 / 9), null);// xͼƬ
		canvas.drawBitmap(horseRedBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 3 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 5 / 9), null);// xͼƬ
		canvas.drawBitmap(horseBlueBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 4 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 6 / 9), null);// xͼƬ
		canvas.drawBitmap(horseYellowBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 5 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 7 / 9), null);// xͼƬ
		canvas.drawBitmap(horseGreenBitmap, new Rect(
				Content.horseRightNum[horseRightNum][0],
				Content.horseRightNum[horseRightNum][1],
				Content.horseRightNum[horseRightNum][2],
				Content.horseRightNum[horseRightNum][3]), new RectF(
				Content.MoveX + mScreenWidth, mScreenHeight * 6 / 9,
				Content.MoveX + mScreenWidth + mScreenWidth * 7 / 40,
				mScreenHeight * 8 / 9), null);// xͼƬ
		horseRightNum++;
		if (horseRightNum == 3) {
			horseRightNum = 0;
		}
		canvas.drawBitmap(door_frameBitmap, null, new RectF(Content.MoveX
				+ mScreenWidth + mScreenWidth / 8, 0, Content.MoveX
				+ mScreenWidth + mScreenWidth * 9 / 40, mScreenHeight * 8 / 9),
				null);//
	}

	// �������
	public void drawTrack(Canvas canvas) {
		canvas.drawBitmap(track_background02Bitmap, null,
				new RectF(moveX, moveY, moveX + mScreenWidth * 2, moveY
						+ mScreenHeight * 11 / 3), null);//

		canvas.drawBitmap(track_background01Bitmap, null, new RectF(moveX
				+ mScreenWidth * 2, moveY, moveX + mScreenWidth * 4, moveY
				+ mScreenHeight * 11 / 3), null);//

		if (isRight) {
			if (MyGameView.isPlay) {
				countFrame++;
			}
			canvas.drawBitmap(horseWhiteBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[0], mScreenHeight * 1 / 9, moveHorses[0]
							+ mScreenWidth * 7 / 40, mScreenHeight * 3 / 9),
					null);// xͼƬ
			canvas.drawBitmap(horsePurpleBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[1], mScreenHeight * 2 / 9, moveHorses[1]
							+ mScreenWidth * 7 / 40, mScreenHeight * 4 / 9),
					null);// xͼƬ
			canvas.drawBitmap(horseRedBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[2], mScreenHeight * 3 / 9, moveHorses[2]
							+ mScreenWidth * 7 / 40, mScreenHeight * 5 / 9),
					null);// xͼƬ
			canvas.drawBitmap(horseBlueBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[3], mScreenHeight * 4 / 9, moveHorses[3]
							+ mScreenWidth * 7 / 40, mScreenHeight * 6 / 9),
					null);// xͼƬ
			canvas.drawBitmap(horseYellowBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[4], mScreenHeight * 5 / 9, moveHorses[4]
							+ mScreenWidth * 7 / 40, mScreenHeight * 7 / 9),
					null);// xͼƬ
			canvas.drawBitmap(horseGreenBitmap, new Rect(
					Content.horseRightNum[horseRightNum][0],
					Content.horseRightNum[horseRightNum][1],
					Content.horseRightNum[horseRightNum][2],
					Content.horseRightNum[horseRightNum][3]), new RectF(
					moveHorses[5], mScreenHeight * 6 / 9, moveHorses[5]
							+ mScreenWidth * 7 / 40, mScreenHeight * 8 / 9),
					null);// xͼƬ
			horseRightNum++;
			if (horseRightNum == 3) {
				horseRightNum = 0;
			}
			if (countFrame >= 6) {// 5�º�ʼ����
				canvas.drawBitmap(door_openBitmap, null, new RectF(moveX
						+ mScreenWidth / 8, 0, moveX + mScreenWidth * 9 / 40,
						mScreenHeight * 8 / 9), null);//

				if (isRight2) {
					getMoveHorseRight2();
				} else {
					getMoveHorse();// ˢ�¸�������ٶȱ仯
				}

			}
			canvas.drawBitmap(door_frameBitmap, null, new RectF(moveX
					+ mScreenWidth / 8, 0, moveX + mScreenWidth * 9 / 40,
					mScreenHeight * 8 / 9), null);//
		} else {
			if (isRightDown) {
				canvas.drawBitmap(horseWhiteBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[0] + moveRightDownX,
								mScreenHeight * 1 / 9, moveHorses[0]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 3 / 9), null);// xͼƬ
				canvas.drawBitmap(horsePurpleBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[1] + moveRightDownX,
								mScreenHeight * 2 / 9, moveHorses[1]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 4 / 9), null);// xͼƬ
				canvas.drawBitmap(horseRedBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[2] + moveRightDownX,
								mScreenHeight * 3 / 9, moveHorses[2]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 5 / 9), null);// xͼƬ
				canvas.drawBitmap(horseBlueBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[3] + moveRightDownX,
								mScreenHeight * 4 / 9, moveHorses[3]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 6 / 9), null);// xͼƬ
				canvas.drawBitmap(horseYellowBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[4] + moveRightDownX,
								mScreenHeight * 5 / 9, moveHorses[4]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 7 / 9), null);// xͼƬ
				canvas.drawBitmap(horseGreenBitmap, new Rect(
						Content.horseRightDownNum[horseRightDownNum][0],
						Content.horseRightDownNum[horseRightDownNum][1],
						Content.horseRightDownNum[horseRightDownNum][2],
						Content.horseRightDownNum[horseRightDownNum][3]),
						new RectF(moveHorses[5] + moveRightDownX,
								mScreenHeight * 6 / 9, moveHorses[5]
										+ moveRightDownX + mScreenWidth * 7
										/ 40, mScreenHeight * 8 / 9), null);// xͼƬ

				horseRightDownNum++;
				if (horseRightDownNum == 4) {
					horseRightDownNum = 0;
				}
			} else if (isDown) {
				canvas.drawBitmap(horseWhiteBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[0] + moveRightDownX, mScreenHeight * 1 / 9,
						moveHorses[0] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight / 9 + mScreenHeight * 7 / 80), null);// xͼƬ
				canvas.drawBitmap(horsePurpleBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[1] + moveRightDownX, mScreenHeight * 2 / 9,
						moveHorses[1] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 2 / 9 + mScreenHeight * 7 / 80), null);// xͼƬ
				canvas.drawBitmap(horseRedBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[2] + moveRightDownX, mScreenHeight * 3 / 9,
						moveHorses[2] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 3 / 9 + mScreenHeight * 7 / 80), null);// xͼƬ
				canvas.drawBitmap(horseBlueBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[3] + moveRightDownX, mScreenHeight * 4 / 9,
						moveHorses[3] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 4 / 9 + mScreenHeight * 7 / 80), null);// xͼƬ
				canvas.drawBitmap(horseYellowBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[4] + moveRightDownX, mScreenHeight * 5 / 9,
						moveHorses[4] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 5 / 9 + mScreenHeight * 7 / 80), null);// xͼƬ
				canvas.drawBitmap(horseGreenBitmap, new Rect(
						Content.horseDownNum[horseDownNum][0],
						Content.horseDownNum[horseDownNum][1],
						Content.horseDownNum[horseDownNum][2],
						Content.horseDownNum[horseDownNum][3]), new RectF(
						moveHorses[5] + moveRightDownX, mScreenHeight * 6 / 9,
						moveHorses[5] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 6 / 9 + mScreenHeight * 7 / 80), null);// xͼƬ

				horseDownNum++;
				if (horseDownNum == 4) {
					horseDownNum = 0;
				}
			} else if (isLeftDown) {
				canvas.drawBitmap(horseWhiteBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[0] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 1 / 9,
								moveHorses[0] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 3 / 9), null);// xͼƬ
				canvas.drawBitmap(horsePurpleBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[1] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 2 / 9,
								moveHorses[1] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 4 / 9), null);// xͼƬ
				canvas.drawBitmap(horseRedBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[2] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 3 / 9,
								moveHorses[2] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 5 / 9), null);// xͼƬ
				canvas.drawBitmap(horseBlueBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[3] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 4 / 9,
								moveHorses[3] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 6 / 9), null);// xͼƬ
				canvas.drawBitmap(horseYellowBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[4] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 5 / 9,
								moveHorses[4] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 7 / 9), null);// xͼƬ
				canvas.drawBitmap(horseGreenBitmap, new Rect(
						Content.horseLeftDownNum[horseLeftDownNum][0],
						Content.horseLeftDownNum[horseLeftDownNum][1],
						Content.horseLeftDownNum[horseLeftDownNum][2],
						Content.horseLeftDownNum[horseLeftDownNum][3]),
						new RectF(moveHorses[5] + moveRightDownX
								+ moveLeftDownX, mScreenHeight * 6 / 9,
								moveHorses[5] + moveRightDownX + moveLeftDownX
										+ mScreenWidth * 7 / 40,
								mScreenHeight * 8 / 9), null);// xͼƬ

				horseLeftDownNum++;
				if (horseLeftDownNum == 3) {
					horseLeftDownNum = 0;
				}
			} else if (!isEnd) {
				canvas.drawBitmap(horseWhiteBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[0] + moveRightDownX, mScreenHeight * 1 / 9,
						moveHorses[0] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 3 / 9), null);// xͼƬ
				canvas.drawBitmap(horsePurpleBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[1] + moveRightDownX, mScreenHeight * 2 / 9,
						moveHorses[1] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 4 / 9), null);// xͼƬ
				canvas.drawBitmap(horseRedBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[2] + moveRightDownX, mScreenHeight * 3 / 9,
						moveHorses[2] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 5 / 9), null);// xͼƬ
				canvas.drawBitmap(horseBlueBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[3] + moveRightDownX, mScreenHeight * 4 / 9,
						moveHorses[3] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 6 / 9), null);// xͼƬ
				canvas.drawBitmap(horseYellowBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[4] + moveRightDownX, mScreenHeight * 5 / 9,
						moveHorses[4] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 7 / 9), null);// xͼƬ
				canvas.drawBitmap(horseGreenBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[5] + moveRightDownX, mScreenHeight * 6 / 9,
						moveHorses[5] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 8 / 9), null);// xͼƬ

				horseLeftNum++;
				if (horseLeftNum == 3) {
					horseLeftNum = 0;
				}
				// �����жϣ����ƶ���һ��ʱ����Ҫ�����������ƥ���ٶ�
				if (Math.abs(moveX) < mScreenWidth * 4 / 3) {
					//�ж��Ƿ�������ƶ�λ���Ե���end�˵�
					if ((moveHorses[0] + moveRightDownX) <= mScreenWidth / 4
							|| (moveHorses[1] + moveRightDownX) <= mScreenWidth / 4
							|| (moveHorses[2] + moveRightDownX) <= mScreenWidth / 4
							|| (moveHorses[3] + moveRightDownX) <= mScreenWidth / 4
							|| (moveHorses[4] + moveRightDownX) <= mScreenWidth / 4
							|| (moveHorses[5] + moveRightDownX) <= mScreenWidth / 4) {
						//�����ˣ����ڱ���Ϊ��Ҷ����ʶ����仯����Ҫ�����Ե��������涼��Ҫ����ǰ������Ҫ��֤�ڶ���
						getMoverHorse3();

					} else {
						//û��֮ǰ�ƶ�����
						getMoverHorse2Result();

					}
				} else {
					getMoveHorse2();// ˢ�¸�������ٶȱ仯
				}
			} else {// ����
				canvas.drawBitmap(horseWhiteBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[0] + moveRightDownX, mScreenHeight * 1 / 9,
						moveHorses[0] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 3 / 9), null);// xͼƬ
				canvas.drawBitmap(horsePurpleBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[1] + moveRightDownX, mScreenHeight * 2 / 9,
						moveHorses[1] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 4 / 9), null);// xͼƬ
				canvas.drawBitmap(horseRedBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[2] + moveRightDownX, mScreenHeight * 3 / 9,
						moveHorses[2] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 5 / 9), null);// xͼƬ
				canvas.drawBitmap(horseBlueBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[3] + moveRightDownX, mScreenHeight * 4 / 9,
						moveHorses[3] + moveRightDownX + mScreenWidth / 8
								+ mScreenWidth / 20, mScreenHeight * 6 / 9),
						null);// xͼƬ
				canvas.drawBitmap(horseYellowBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[4] + moveRightDownX, mScreenHeight * 5 / 9,
						moveHorses[4] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 7 / 9), null);// xͼƬ
				canvas.drawBitmap(horseGreenBitmap, new Rect(
						Content.horseLeftNum[horseLeftNum][0],
						Content.horseLeftNum[horseLeftNum][1],
						Content.horseLeftNum[horseLeftNum][2],
						Content.horseLeftNum[horseLeftNum][3]), new RectF(
						moveHorses[5] + moveRightDownX, mScreenHeight * 6 / 9,
						moveHorses[5] + moveRightDownX + mScreenWidth * 7 / 40,
						mScreenHeight * 8 / 9), null);// xͼƬ

				if (countEnd % 2 == 1) {
					// �����һλ��
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[Content.result[0]][0],
							Content.num[Content.result[0]][1],
							Content.num[Content.result[0]][0]
									+ Content.num[Content.result[0]][2],
							Content.num[Content.result[0]][1]
									+ Content.num[Content.result[0]][3]),
							new RectF(mScreenWidth / 6, mScreenHeight / 3,
									mScreenWidth * 7 / 30,
									mScreenHeight * 2 / 3), null);// xͼƬ

					// ���-
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[11][0], Content.num[11][1],
							Content.num[11][0] + Content.num[11][2],
							Content.num[11][1] + Content.num[11][3]),
							new RectF(mScreenWidth * 17 / 60, mScreenHeight
									* 10 / 21 - mScreenHeight / 25,
									mScreenWidth * 7 / 20, mScreenHeight * 10
											/ 21 + mScreenHeight / 25), null);// xͼƬ

					// ����ڶ�λ��
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[Content.result[1]][0],
							Content.num[Content.result[1]][1],
							Content.num[Content.result[1]][0]
									+ Content.num[Content.result[1]][2],
							Content.num[Content.result[1]][1]
									+ Content.num[Content.result[1]][3]),
							new RectF(mScreenWidth * 2 / 5, mScreenHeight / 3,
									mScreenWidth * 14 / 30,
									mScreenHeight * 2 / 3), null);// xͼƬ

					// �н����ʣ�С��10������10С��100������100С��1000������1000��
					if (Content.result[2] == 1000) {
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[1][0], Content.num[1][1],
								Content.num[1][0] + Content.num[1][2],
								Content.num[1][1] + Content.num[1][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 6
										/ 15, mScreenHeight / 3, mScreenWidth
										/ 6 + mScreenWidth * 7 / 15,
										mScreenHeight * 2 / 3), null);//
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[0][0], Content.num[0][1],
								Content.num[0][0] + Content.num[0][2],
								Content.num[0][1] + Content.num[0][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 7
										/ 15 + mScreenWidth / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 8 / 15
												+ mScreenWidth / 30,
										mScreenHeight * 2 / 3), null);//

						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[0][0], Content.num[0][1],
								Content.num[0][0] + Content.num[0][2],
								Content.num[0][1] + Content.num[0][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 8
										/ 15 + mScreenWidth * 2 / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 9 / 15
												+ mScreenWidth * 2 / 30,
										mScreenHeight * 2 / 3), null);//

						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[0][0], Content.num[0][1],
								Content.num[0][0] + Content.num[0][2],
								Content.num[0][1] + Content.num[0][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 9
										/ 15 + mScreenWidth * 3 / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 10 / 15
												+ mScreenWidth * 3 / 30,
										mScreenHeight * 2 / 3), null);//

					} else if (Content.result[2] >= 100
							&& Content.result[2] <= 500) {
						int a = Content.result[2] / 100;// ��λ
						int b = Content.result[2] % 100 / 10;// ʮλ
						int c = Content.result[2] % 10;// ��λ
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[a][0], Content.num[a][1],
								Content.num[a][0] + Content.num[a][2],
								Content.num[a][1] + Content.num[a][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 6
										/ 15, mScreenHeight / 3, mScreenWidth
										/ 6 + mScreenWidth * 7 / 15,
										mScreenHeight * 2 / 3), null);//
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[b][0], Content.num[b][1],
								Content.num[b][0] + Content.num[b][2],
								Content.num[b][1] + Content.num[b][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 7
										/ 15 + mScreenWidth / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 8 / 15
												+ mScreenWidth / 30,
										mScreenHeight * 2 / 3), null);//
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[c][0], Content.num[c][1],
								Content.num[c][0] + Content.num[c][2],
								Content.num[c][1] + Content.num[c][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 8
										/ 15 + mScreenWidth * 2 / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 9 / 15
												+ mScreenWidth * 2 / 30,
										mScreenHeight * 2 / 3), null);//

					} else if (Content.result[2] >= 10
							&& Content.result[2] <= 80) {
						int a = Content.result[2] / 10;// ʮλ
						int b = Content.result[2] % 10;// ��λ
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[a][0], Content.num[a][1],
								Content.num[a][0] + Content.num[a][2],
								Content.num[a][1] + Content.num[a][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 6
										/ 15, mScreenHeight / 3, mScreenWidth
										/ 6 + mScreenWidth * 7 / 15,
										mScreenHeight * 2 / 3), null);//

						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[b][0], Content.num[b][1],
								Content.num[b][0] + Content.num[b][2],
								Content.num[b][1] + Content.num[b][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 7
										/ 15 + mScreenWidth / 30,
										mScreenHeight / 3, mScreenWidth / 6
												+ mScreenWidth * 8 / 15
												+ mScreenWidth / 30,
										mScreenHeight * 2 / 3), null);//
					} else if (Content.result[2] >= 3 && Content.result[2] <= 8) {
						canvas.drawBitmap(redFontBitmap, new Rect(
								Content.num[Content.result[2]][0],
								Content.num[Content.result[2]][1],
								Content.num[Content.result[2]][0]
										+ Content.num[Content.result[2]][2],
								Content.num[Content.result[2]][1]
										+ Content.num[Content.result[2]][3]),
								new RectF(mScreenWidth / 6 + mScreenWidth * 6
										/ 15, mScreenHeight / 3, mScreenWidth
										/ 6 + mScreenWidth * 7 / 15,
										mScreenHeight * 2 / 3), null);//
					}

				}

			}
			//
			canvas.drawBitmap(end_pointBitmap, null, new RectF(moveX
					+ mScreenWidth / 4, mScreenHeight / 8, moveX + mScreenWidth
					/ 4 + mScreenWidth / 80, mScreenHeight), null);//
		}
		canvas.drawBitmap(track_foreground02Bitmap, null, new RectF(moveX,
				moveY, moveX + mScreenWidth * 2, moveY + mScreenHeight * 4
						- mScreenHeight / 3), null);//
		canvas.drawBitmap(track_foreground01Bitmap, null, new RectF(moveX
				+ mScreenWidth * 2, moveY, moveX + mScreenWidth * 4, moveY
				+ mScreenHeight * 4 - mScreenHeight / 3), null);//

		if (countFrame >= 6) {
			moveTo();
		}

	}

	int[] index = new int[6];

	private void getHorseSpeedSort() {
		// ���ݵ�ǰ��ƥ�����飬��ȡ������������
		List<Integer> list = new ArrayList<Integer>();

		for (int i : moveHorses) {
			list.add(i);
		}
		// ����������
		// java.util.Arrays.sort(moveHorses);

		// �����������������5���������±�
		for (int j = moveHorses.length - 1; j >= 0; j--) {
			index[5 - j] = list.indexOf(moveHorses[j]);
			list.set(list.indexOf(moveHorses[j]), 0);
		}

	}

	private void getMoveHorseRight2() {
		// ��ȡ��ƥ�����λ��ʱ���Ⱥ�˳��Ȼ������ٶ�Ϊ������ʾ
		if (moveHorses[index[0]] <= 340) {
			moveHorses[index[0]] = moveHorses[index[0]] + 20;
		} else if (moveHorses[index[0]] > 340) {
			moveHorses[index[0]] = moveHorses[index[0]] - 20;
		}

		if (moveHorses[index[1]] <= 310) {
			moveHorses[index[1]] = moveHorses[index[1]] + 20;
		} else if (moveHorses[index[1]] > 310) {
			moveHorses[index[1]] = moveHorses[index[1]] - 20;
		}

		if (moveHorses[index[2]] <= 280) {
			moveHorses[index[2]] = moveHorses[index[2]] + 20;
		} else if (moveHorses[index[2]] > 280) {
			moveHorses[index[2]] = moveHorses[index[2]] - 20;
		}

		if (moveHorses[index[3]] <= 250) {
			moveHorses[index[3]] = moveHorses[index[3]] + 20;
		} else if (moveHorses[index[3]] > 250) {
			moveHorses[index[3]] = moveHorses[index[3]] - 20;
		}

		if (moveHorses[index[4]] <= 220) {
			moveHorses[index[4]] = moveHorses[index[4]] + 20;
		} else if (moveHorses[index[4]] > 220) {
			moveHorses[index[4]] = moveHorses[index[4]] - 20;
		}

		if (moveHorses[index[5]] <= 190) {
			moveHorses[index[5]] = moveHorses[index[5]] + 20;
		} else if (moveHorses[index[5]] > 190) {
			moveHorses[index[5]] = moveHorses[index[5]] - 20;
		}
	}

	private void getMoverHorse3() {	
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx="+Content.result[1]);
		switch (Content.result[1]) {
		case 1:
			if((moveHorses[0] + moveRightDownX) > mScreenWidth / 4+40){	
				System.out.println("111111111111");
				moveHorses[0] = moveHorses[0] - 20;
			}
			break;
		case 2:
			if((moveHorses[1] + moveRightDownX) > mScreenWidth / 4+40){
				System.out.println("2222222222222");

				moveHorses[1] = moveHorses[1] - 20;
			}
			break;
		case 3:
			if((moveHorses[2] + moveRightDownX) > mScreenWidth / 4+40){
				System.out.println("3333333333333");

				moveHorses[2] = moveHorses[2] - 20;
			}
			break;
		case 4:
			if((moveHorses[3] + moveRightDownX) > mScreenWidth / 4+40){
				System.out.println("4444444444444");

				moveHorses[3] = moveHorses[3] - 20;
			}
			break;
		case 5:
			if((moveHorses[4] + moveRightDownX) > mScreenWidth / 4+40){
				System.out.println("5555555555555");

				moveHorses[4] = moveHorses[4] - 20;
			}
			break;
		case 6:
			if((moveHorses[5] + moveRightDownX) > mScreenWidth / 4+40){	
				System.out.println("6666666666666");

				moveHorses[5] = moveHorses[5] - 20;
			}
			break;

		default:
			break;
		}

	}

	// ���ƶ����յ�ʱ������һ���ľ��룬���ݽ�����ܵ�һ��������ǰÿ���ƶ�30,�ڶ���ÿ���ƶ�20�����಻
	private void getMoverHorse2Result() {
		// TODO �Զ����ɵķ������
		switch (Content.result[0]) {
		case 1:
			moveHorses[0] = moveHorses[0] - 50;
			moveHorses[1] = moveHorses[1] - 3;
			moveHorses[2] = moveHorses[2] - 3;
			moveHorses[3] = moveHorses[3] - 3;
			moveHorses[4] = moveHorses[4] - 3;
			moveHorses[5] = moveHorses[5] - 3;

			break;
		case 2:
			moveHorses[1] = moveHorses[1] - 50;
			moveHorses[0] = moveHorses[0] - 3;
			moveHorses[2] = moveHorses[2] - 3;
			moveHorses[3] = moveHorses[3] - 3;
			moveHorses[4] = moveHorses[4] - 3;
			moveHorses[5] = moveHorses[5] - 3;
			break;
		case 3:
			moveHorses[2] = moveHorses[2] - 50;
			moveHorses[1] = moveHorses[1] - 3;
			moveHorses[0] = moveHorses[0] - 3;
			moveHorses[3] = moveHorses[3] - 3;
			moveHorses[4] = moveHorses[4] - 3;
			moveHorses[5] = moveHorses[5] - 3;
			break;
		case 4:
			moveHorses[3] = moveHorses[3] - 50;
			moveHorses[1] = moveHorses[1] - 3;
			moveHorses[2] = moveHorses[2] - 3;
			moveHorses[0] = moveHorses[0] - 3;
			moveHorses[4] = moveHorses[4] - 3;
			moveHorses[5] = moveHorses[5] - 3;
			break;
		case 5:
			moveHorses[4] = moveHorses[4] - 50;
			moveHorses[1] = moveHorses[1] - 3;
			moveHorses[2] = moveHorses[2] - 3;
			moveHorses[3] = moveHorses[3] - 3;
			moveHorses[0] = moveHorses[0] - 3;
			moveHorses[5] = moveHorses[5] - 3;
			break;
		case 6:
			moveHorses[5] = moveHorses[5] - 50;
			moveHorses[1] = moveHorses[1] - 3;
			moveHorses[2] = moveHorses[2] - 3;
			moveHorses[3] = moveHorses[3] - 3;
			moveHorses[0] = moveHorses[0] - 3;
			moveHorses[4] = moveHorses[4] - 3;
			break;

		default:
			break;
		}

		switch (Content.result[1]) {
		case 1:
			moveHorses[0] = moveHorses[0] - 30;
			break;
		case 2:
			moveHorses[1] = moveHorses[1] - 30;
			break;
		case 3:
			moveHorses[2] = moveHorses[2] - 30;
			break;
		case 4:
			moveHorses[3] = moveHorses[3] - 30;
			break;
		case 5:
			moveHorses[4] = moveHorses[4] - 30;
			break;
		case 6:
			moveHorses[5] = moveHorses[5] - 30;
			break;

		default:
			break;
		}

	}

	int randomHorseWhite = 0;
	int randomHorsePurple = 0;
	int randomHorseRed = 0;
	int randomHorseBlue = 0;
	int randomHorseYellow = 0;
	int randomHorseGreen = 0;
	Random random = new Random();

	// ��������ٱ仯1(����)
	private void getMoveHorse() {
		randomHorseWhite = random.nextInt(2);
		if (randomHorseWhite == 0) {// ��
			if (moveHorses[0] > 40) {
				moveHorses[0] = moveHorses[0] - 20;
			} else {

			}
		} else {// 1Ϊ��
			if (moveHorses[0] < mScreenWidth / 2) {
				moveHorses[0] = moveHorses[0] + 20;
			}
		}

		randomHorsePurple = random.nextInt(2);
		if (randomHorsePurple == 0) {// ��
			if (moveHorses[1] > 40) {
				moveHorses[1] = moveHorses[1] - 20;
			}
		} else {// 1Ϊ��
			if (randomHorsePurple < mScreenWidth / 2) {
				moveHorses[1] = moveHorses[1] + 20;
			}
		}

		randomHorseRed = random.nextInt(2);
		if (randomHorseRed == 0) {// ��
			if (moveHorses[2] > 40) {
				moveHorses[2] = moveHorses[2] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[2] < mScreenWidth / 2) {
				moveHorses[2] = moveHorses[2] + 20;
			}
		}

		randomHorseBlue = random.nextInt(2);
		if (randomHorseBlue == 0) {// ��
			if (moveHorses[3] > 40) {
				moveHorses[3] = moveHorses[3] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[3] < mScreenWidth / 2) {
				moveHorses[3] = moveHorses[3] + 20;
			}
		}

		randomHorseYellow = random.nextInt(2);
		if (randomHorseYellow == 0) {// ��
			if (moveHorses[4] > 40) {
				moveHorses[4] = moveHorses[4] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[4] < mScreenWidth / 2) {
				moveHorses[4] = moveHorses[4] + 20;
			}
		}

		randomHorseGreen = random.nextInt(2);
		if (randomHorseGreen == 0) {// ��
			if (moveHorses[5] > 40) {
				moveHorses[5] = moveHorses[5] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[5] < mScreenWidth / 2) {
				moveHorses[5] = moveHorses[5] + 20;
			}
		}
	}

	// ��������ٱ仯1(����)
	private void getMoveHorse2() {
		randomHorseWhite = random.nextInt(2);
		if (randomHorseWhite == 0) {// ��
			if (moveHorses[0] < mScreenWidth * 2 / 3) {
				moveHorses[0] = moveHorses[0] - 20;
			} else {

			}
		} else {// 1Ϊ��
			if (moveHorses[0] < mScreenWidth - 40) {
				moveHorses[0] = moveHorses[0] + 20;
			}
		}

		randomHorsePurple = random.nextInt(2);
		if (randomHorsePurple == 0) {// ��
			if (moveHorses[1] < mScreenWidth * 2 / 3) {
				moveHorses[1] = moveHorses[1] - 20;
			}
		} else {// 1Ϊ��
			if (randomHorsePurple < mScreenWidth - 40) {
				moveHorses[1] = moveHorses[1] + 20;
			}
		}

		randomHorseRed = random.nextInt(2);
		if (randomHorseRed == 0) {// ��
			if (moveHorses[2] < mScreenWidth * 2 / 3) {
				moveHorses[2] = moveHorses[2] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[2] < mScreenWidth - 40) {
				moveHorses[2] = moveHorses[2] + 20;
			}
		}

		randomHorseBlue = random.nextInt(2);
		if (randomHorseBlue == 0) {// ��
			if (moveHorses[3] < mScreenWidth * 2 / 3) {
				moveHorses[3] = moveHorses[3] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[3] < mScreenWidth - 40) {
				moveHorses[3] = moveHorses[3] + 20;
			}
		}

		randomHorseYellow = random.nextInt(2);
		if (randomHorseYellow == 0) {// ��
			if (moveHorses[4] < mScreenWidth * 2 / 3) {
				moveHorses[4] = moveHorses[4] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[4] < mScreenWidth - 40) {
				moveHorses[4] = moveHorses[4] + 20;
			}
		}

		randomHorseGreen = random.nextInt(2);
		if (randomHorseGreen == 0) {// ��
			if (moveHorses[5] < mScreenWidth * 2 / 3) {
				moveHorses[5] = moveHorses[5] - 20;
			}
		} else {// 1Ϊ��
			if (moveHorses[5] < mScreenWidth - 40) {
				moveHorses[5] = moveHorses[5] + 20;
			}
		}
	}

	// ��Ϸ��ʼǰ��ע��ӰͼƬ
	public void drawBackGround(Canvas canvas, View view, boolean isBet,
			int betNum, boolean isStart) {
		canvas.drawBitmap(background, null, new RectF(Content.MoveX,
				Content.MoveY, Content.MoveX + mScreenWidth, Content.MoveY
						+ mScreenHeight), null);// ����1ͼƬ

		canvas.drawBitmap(outlineBitmap, null, new RectF(Content.MoveX + x1,
				Content.MoveY + x1, Content.MoveX + mScreenWidth - x1,
				Content.MoveY + mScreenHeight - x1), null);// ��ɫ����ͼƬ

		canvas.drawBitmap(frameBitmap, null, new RectF(Content.MoveX + x1 * 4,
				Content.MoveY + x1 * 4, Content.MoveX + mScreenWidth * 3 / 4
						- x1, Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2),
				null);// ��ע������ͼƬ

		canvas.drawBitmap(lastoddsBitmap, null, new RectF(Content.MoveX
				+ mScreenWidth * 3 / 8 - x1 * 2 + 5, Content.MoveY + x1 / 2,
				Content.MoveX + mScreenWidth * 5 / 8 - x1 * 2 + 5,
				Content.MoveY + x1 * 3), null);// lastoddsͼƬ

		canvas.drawBitmap(last5rBitmap, null, new RectF(Content.MoveX
				+ mScreenWidth * 6 / 8 + x1 * 3, Content.MoveY + x1 / 2,
				Content.MoveX + mScreenWidth - x1 * 4, Content.MoveY + x1 * 6),
				null);// last5rͼƬ

		// rebetδ��ǰ
		if (!isBet) {
			canvas.drawBitmap(rebet01Bitmap, null, new RectF(Content.MoveX + x1
					* 4 + xLeft * 8 / 3,
					Content.MoveY + x1 * 4 + yLeft * 9 / 2, Content.MoveX + x1
							* 4 + xLeft * 8 / 3 + xLeft, Content.MoveY + x1 * 4
							+ yLeft * 9 / 2 + yLeft), null);// rebetͼƬ
		} else {
			canvas.drawBitmap(rebet02Bitmap, null, new RectF(Content.MoveX + x1
					* 4 + xLeft * 8 / 3,
					Content.MoveY + x1 * 4 + yLeft * 9 / 2, Content.MoveX + x1
							* 4 + xLeft * 8 / 3 + xLeft, Content.MoveY + x1 * 4
							+ yLeft * 9 / 2 + yLeft), null);// rebetͼƬ
		}
		// rebet�ұߵ�x
		canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[10][0],
				Content.num[10][1], Content.num[10][0] + Content.num[10][2],
				Content.num[10][1] + Content.num[10][3]), new RectF(
				Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft, Content.MoveY
						+ x1 * 5 + yLeft * 9 / 2, Content.MoveX + x1 * 5
						+ xLeft * 8 / 3 + xLeft + xLeft / 3, Content.MoveY + x1
						* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

		switch (betNum) {
		case 1:
			// rebet=1
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[1][0],
					Content.num[1][1], Content.num[1][0] + Content.num[1][2],
					Content.num[1][1] + Content.num[1][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 10, Content.MoveY + x1 * 5 + yLeft * 9
							/ 2, Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft
							+ xLeft / 3 + xLeft / 3 * 5 / 6, Content.MoveY + x1
							* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

			break;
		case 2:
			// rebet=2
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[2][0],
					Content.num[2][1], Content.num[2][0] + Content.num[2][2],
					Content.num[2][1] + Content.num[2][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 10, Content.MoveY + x1 * 5 + yLeft * 9
							/ 2, Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft
							+ xLeft / 3 + xLeft / 3 * 5 / 6, Content.MoveY + x1
							* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ
			break;
		case 5:
			// rebet=5
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[5][0],
					Content.num[5][1], Content.num[5][0] + Content.num[5][2],
					Content.num[5][1] + Content.num[5][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 10, Content.MoveY + x1 * 5 + yLeft * 9
							/ 2, Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft
							+ xLeft / 3 + xLeft / 3 * 5 / 6, Content.MoveY + x1
							* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ
			break;
		case 10:
			// rebet=10 ��1+0�ķ���
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[1][0],
					Content.num[1][1], Content.num[1][0] + Content.num[1][2],
					Content.num[1][1] + Content.num[1][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 10, Content.MoveY + x1 * 5 + yLeft * 9
							/ 2, Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft
							+ xLeft / 3 + xLeft / 3 * 5 / 6, Content.MoveY + x1
							* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 3 * 5 / 6 + xLeft / 10, Content.MoveY
							+ x1 * 5 + yLeft * 9 / 2, Content.MoveX + x1 * 5
							+ xLeft * 8 / 3 + xLeft + xLeft / 3 + xLeft / 3 * 5
							/ 6 + xLeft / 10 + xLeft / 3 * 5 / 6, Content.MoveY
							+ x1 * 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

			break;
		case 80:
			// rebet=80 ��8+0�ķ���
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[8][0],
					Content.num[8][1], Content.num[8][0] + Content.num[8][2],
					Content.num[8][1] + Content.num[8][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 10, Content.MoveY + x1 * 5 + yLeft * 9
							/ 2, Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft
							+ xLeft / 3 + xLeft / 3 * 5 / 6, Content.MoveY + x1
							* 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 5 + xLeft * 8 / 3 + xLeft + xLeft / 3
							+ xLeft / 3 * 5 / 6 + xLeft / 10, Content.MoveY
							+ x1 * 5 + yLeft * 9 / 2, Content.MoveX + x1 * 5
							+ xLeft * 8 / 3 + xLeft + xLeft / 3 + xLeft / 3 * 5
							/ 6 + xLeft / 10 + xLeft / 3 * 5 / 6, Content.MoveY
							+ x1 * 3 + yLeft * 9 / 2 + yLeft), null);// xͼƬ

			break;

		default:
			break;
		}

		// ѹע��������ɫ����6,5,4,3,2
		for (int i = 6; i > 1; i--) {
			canvas.drawBitmap(blueFontBitmap, new Rect(Content.num[i][0],
					Content.num[i][1], Content.num[i][0] + Content.num[i][2],
					Content.num[i][1] + Content.num[i][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 2 / 3 + (xLeft * (6 - i)),
					Content.MoveY + x1 * 5, Content.MoveX + x1 * 4 + xLeft
							+ (xLeft * (6 - i)), Content.MoveY + yLeft / 2 + x1
							* 5), null);// last5rͼƬ

		}

		// ѹע��������ɫ����1,2,3,4,5
		for (int i = 1; i < 6; i++) {
			canvas.drawBitmap(blueFontBitmap, new Rect(Content.num[i][0],
					Content.num[i][1], Content.num[i][0] + Content.num[i][2],
					Content.num[i][1] + Content.num[i][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 10, Content.MoveY + x1 * 5
							+ yLeft / 2 + yLeft / 5 + yLeft * (i - 1),
					Content.MoveX + x1 * 4 + xLeft / 3 * 5 / 6, Content.MoveY
							+ x1 * 5 + yLeft * 6 / 5 + yLeft * (i - 1)), null);// last5rͼƬ

		}
		// won ����
		canvas.drawBitmap(wonBitmap, null, new RectF(Content.MoveX + x1 * 4
				+ xLeft, Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft
				* 2 / 3, Content.MoveX + x1 * 4 + xLeft * 5 / 3, Content.MoveY
				+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2),
				null);// rebetͼƬ
		// start(�������ԣ����start��������odds���Զ��仯2016-07-11 19:57)
		if (!isStart) {
			canvas.drawBitmap(start01Bitmap, null, new RectF(Content.MoveX + x1
					* 4 + xLeft * 5 / 2, Content.MoveY + mScreenHeight * 3 / 4
					- x1 * 2 + yLeft * 2 / 3, Content.MoveX + x1 * 4 + xLeft
					+ xLeft * 7 / 2, Content.MoveY + mScreenHeight * 3 / 4 - x1
					* 2 + yLeft * 2 / 3 + yLeft), null);// startͼƬ
		} else {
			canvas.drawBitmap(start02Bitmap, null, new RectF(Content.MoveX + x1
					* 4 + xLeft * 5 / 2, Content.MoveY + mScreenHeight * 3 / 4
					- x1 * 2 + yLeft * 2 / 3, Content.MoveX + x1 * 4 + xLeft
					+ xLeft * 7 / 2, Content.MoveY + mScreenHeight * 3 / 4 - x1
					* 2 + yLeft * 2 / 3 + yLeft), null);// startͼƬ
		}

		// coin ����
		canvas.drawBitmap(coinBitmap, null, new RectF(Content.MoveX + x1 * 4
				+ xLeft * 16 / 3, Content.MoveY + mScreenHeight * 3 / 4 - x1
				* 2 + yLeft * 2 / 3, Content.MoveX + x1 * 4 + xLeft * 6,
				Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
						+ yLeft / 2), null);// rebetͼƬ
		// ��ҷ���
		drawCoin(canvas);
		// �н�����
		drawWon(canvas);
		// ����������
		drawOdds(canvas);
		// �����
		drawResult(canvas);

	}

	// �н�����
	private void drawWon(Canvas canvas) {
		// TODO �Զ����ɵķ������
		if (MyGameView.playStyle == 1 || MyGameView.playStyle == 4
				|| MyGameView.playStyle == 5) {
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);// rebetͼƬ
		} else if (MyGameView.playStyle == 3 || MyGameView.playStyle == 5) {
			drawWonNum(canvas);
		}

	}

	int count1 = 0;
	int count2 = 0;
	boolean isCount1 = true;
	boolean isCount2 = true;

	// �����
	private void drawResult(Canvas canvas) {
		if (MyGameView.resultLists.size() > 0) {
			if (MyGameView.resultLists.size() >= 5) {
				for (int i = MyGameView.resultLists.size() - 1; i >= MyGameView.resultLists
						.size() - 5; i--) {
					if (MyGameView.playStyle == 3) {
						if (count1 == 0) {
							if (isCount1) {
								drawResult2(canvas, i, count1);
								isCount1 = false;
							} else {
								isCount1 = true;
							}
						} else {
							drawResult2(canvas, i, count1);
						}
					} else {
						drawResult2(canvas, i, count1);
					}
					count1++;
				}
				count1 = 0;
			} else {
				for (int i = MyGameView.resultLists.size() - 1; i >= 0; i--) {
					if (MyGameView.playStyle == 3) {
						if (count2 == 0) {
							if (isCount2) {
								drawResult2(canvas, i, count2);
								isCount2 = false;
							} else {
								isCount2 = true;
							}
						} else {
							drawResult2(canvas, i, count2);
						}
					} else {
						drawResult2(canvas, i, count2);
					}
					count2++;
				}
				count2 = 0;
			}

		}
	}

	private void drawResult2(Canvas canvas, int i, int count) {
		canvas.drawBitmap(blueFontBitmap, new Rect(
				Content.num[MyGameView.resultLists.get(i)[0]][0],
				Content.num[MyGameView.resultLists.get(i)[0]][1],
				Content.num[MyGameView.resultLists.get(i)[0]][0]
						+ Content.num[MyGameView.resultLists.get(i)[0]][2],
				Content.num[MyGameView.resultLists.get(i)[0]][1]
						+ Content.num[MyGameView.resultLists.get(i)[0]][3]),
				new RectF(Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3,
						Content.MoveY + x1 * 7 + count * yLeft, Content.MoveX
								+ mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 8
								/ 45, Content.MoveY + x1 * 5 + yLeft + count
								* yLeft), null);// �����ǰ����

		canvas.drawBitmap(blueFontBitmap, new Rect(Content.num[11][0],
				Content.num[11][1], Content.num[11][0] + Content.num[11][2],
				Content.num[11][1] + Content.num[11][3]),
				new RectF(Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft
						* 10 / 45, Content.MoveY + x1 * 7 + (yLeft - x1 * 2)
						* 2 / 5 + count * yLeft, Content.MoveX + mScreenWidth
						* 6 / 8 + x1 * 3 + xLeft * 16 / 45, Content.MoveY + x1
						* 7 + (yLeft - x1 * 2) * 3 / 5 + count * yLeft), null);// �����
																				// -��ͼ����ȡ�ָ߶ȵ�1/5

		canvas.drawBitmap(blueFontBitmap, new Rect(
				Content.num[MyGameView.resultLists.get(i)[1]][0],
				Content.num[MyGameView.resultLists.get(i)[1]][1],
				Content.num[MyGameView.resultLists.get(i)[1]][0]
						+ Content.num[MyGameView.resultLists.get(i)[1]][2],
				Content.num[MyGameView.resultLists.get(i)[1]][1]
						+ Content.num[MyGameView.resultLists.get(i)[1]][3]),
				new RectF(Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft
						* 18 / 45, Content.MoveY + x1 * 7 + count * yLeft,
						Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft
								* 18 / 45 + xLeft * 8 / 45, Content.MoveY + x1
								* 5 + yLeft + count * yLeft), null);// �����ǰ����
		// �н����ʣ�С��10������10С��100������100С��1000������1000��
		if (MyGameView.resultLists.get(i)[2] == 1000) {
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[1][0],
					Content.num[1][1], Content.num[1][0] + Content.num[1][2],
					Content.num[1][1] + Content.num[1][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 12 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 18 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 18 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 24 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 24 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 30 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 30 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 36 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
		} else if (MyGameView.resultLists.get(i)[2] >= 100
				&& MyGameView.resultLists.get(i)[2] <= 500) {
			int a = MyGameView.resultLists.get(i)[2] / 100;// ��λ
			int b = MyGameView.resultLists.get(i)[2] % 100 / 10;// ʮλ
			int c = MyGameView.resultLists.get(i)[2] % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 18 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 24 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 24 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 30 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 30 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 36 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//

		} else if (MyGameView.resultLists.get(i)[2] >= 10
				&& MyGameView.resultLists.get(i)[2] <= 80) {
			int a = MyGameView.resultLists.get(i)[2] / 10;// ʮλ
			int b = MyGameView.resultLists.get(i)[2] % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 18 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 24 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18
							/ 45 + xLeft * 24 / 45, Content.MoveY + x1 * 7
							+ count * yLeft, Content.MoveX + mScreenWidth * 6
							/ 8 + x1 * 3 + xLeft * 18 / 45 + xLeft * 30 / 45,
					Content.MoveY + x1 * 5 + yLeft + count * yLeft), null);//
		} else if (MyGameView.resultLists.get(i)[2] >= 3
				&& MyGameView.resultLists.get(i)[2] <= 8) {
			canvas.drawBitmap(
					yellowFontBitmap,
					new Rect(
							Content.num[MyGameView.resultLists.get(i)[2]][0],
							Content.num[MyGameView.resultLists.get(i)[2]][1],
							Content.num[MyGameView.resultLists.get(i)[2]][0]
									+ Content.num[MyGameView.resultLists.get(i)[2]][2],
							Content.num[MyGameView.resultLists.get(i)[2]][1]
									+ Content.num[MyGameView.resultLists.get(i)[2]][3]),
					new RectF(Content.MoveX + mScreenWidth * 6 / 8 + x1 * 3
							+ xLeft * 18 / 45 + xLeft * 18 / 45, Content.MoveY
							+ x1 * 7 + count * yLeft, Content.MoveX
							+ mScreenWidth * 6 / 8 + x1 * 3 + xLeft * 18 / 45
							+ xLeft * 24 / 45, Content.MoveY + x1 * 5 + yLeft
							+ count * yLeft), null);// rebetͼƬ
		}
	}

	// �н�����
	private void drawWonNum(Canvas canvas) {
		// ����Ĵ�СΪxLeft/3(��һ�����ֺ�ǰһ���������xLeft/5-xLeft/9)
		// coin ��ֵ������ܷ�����,ÿ��һλ��������ǰ�ƶ�xLeft/9�ĵ�λ���ÿ���
		if (Content.resultWonNum >= 0 && Content.resultWonNum < 10) {
			canvas.drawBitmap(yellowFontBitmap, new Rect(
					Content.num[Content.resultWonNum][0],
					Content.num[Content.resultWonNum][1],
					Content.num[Content.resultWonNum][0]
							+ Content.num[Content.resultWonNum][2],
					Content.num[Content.resultWonNum][1]
							+ Content.num[Content.resultWonNum][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);// rebetͼƬ
		} else if (Content.resultWonNum >= 10 && Content.resultWonNum < 100) {
			int a = Content.resultWonNum / 10;// ʮλ
			int b = Content.resultWonNum % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 - xLeft / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 - xLeft / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 + xLeft / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//
		} else if (Content.resultWonNum >= 100 && Content.resultWonNum < 999) {
			int a = Content.resultWonNum / 100;// ��λ
			int b = Content.resultWonNum % 100 / 10;// ʮλ
			int c = Content.resultWonNum % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 - xLeft * 2 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 - xLeft * 2 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 5
							- xLeft / 9, Content.MoveY + mScreenHeight * 3 / 4
							- x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 2 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 + xLeft * 2 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//
		} else if (Content.resultWonNum >= 1000 && Content.resultWonNum < 9999) {
			int a = Content.resultWonNum / 1000;// ǧλ
			int b = Content.resultWonNum % 1000 / 100;// ��λ
			int c = (Content.resultWonNum / 10) % 10;// ʮλ
			int d = (Content.resultWonNum % 100) % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 - xLeft * 3 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 - xLeft * 3 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 5
							- xLeft * 2 / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 2 / 5
							- xLeft / 9, Content.MoveY + mScreenHeight * 3 / 4
							- x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 3 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 + xLeft * 3 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//
		} else if (Content.resultWonNum >= 10000
				&& Content.resultWonNum < 99999) {
			int a = Content.resultWonNum / 10000;// ��λ
			int b = Content.resultWonNum / 1000 % 10;// ǧλ
			int c = Content.resultWonNum / 100 % 10;// ��λ
			int d = Content.resultWonNum / 10 % 10;// ʮλ
			int e = Content.resultWonNum % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 - xLeft * 4 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 - xLeft * 4 / 9,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 5
							- xLeft * 3 / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft / 5 - xLeft * 3 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 2 / 5
							- xLeft * 2 / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft * 2 / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 3 / 5
							- xLeft / 9, Content.MoveY + mScreenHeight * 3 / 4
							- x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft / 3
							+ xLeft * 3 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[e][0],
					Content.num[e][1], Content.num[e][0] + Content.num[e][2],
					Content.num[e][1] + Content.num[e][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft + xLeft / 6 + xLeft * 4 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft + xLeft / 6 + xLeft / 3 + xLeft * 4 / 5,
					Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2
							/ 3 + yLeft / 2 + x1 + x1 * 3), null);//
		}
	}

	// ��ҷ���
	private void drawCoin(Canvas canvas) {
		// coin ��ֵ������ܷ�����,ÿ��һλ��������ǰ�ƶ�xLeft/9�ĵ�λ���ÿ���
		if (Content.coin >= 0 && Content.coin < 10) {
			canvas.drawBitmap(yellowFontBitmap,
					new Rect(Content.num[Content.coin][0],
							Content.num[Content.coin][1],
							Content.num[Content.coin][0]
									+ Content.num[Content.coin][2],
							Content.num[Content.coin][1]
									+ Content.num[Content.coin][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6, Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2
							+ yLeft * 2 / 3 + yLeft / 2 + x1 * 2, Content.MoveX
							+ x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft / 3,
							Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2
									+ yLeft * 2 / 3 + yLeft / 2 + x1 + x1 * 3),
					null);// rebetͼƬ
		} else if (Content.coin >= 10 && Content.coin < 100) {
			int a = Content.coin / 10;// ʮλ
			int b = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
							Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6
									+ xLeft / 3 - xLeft / 9, Content.MoveY
									+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft
									* 2 / 3 + yLeft / 2 + x1 + x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6 + xLeft / 5, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
							Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6
									+ xLeft / 3 + xLeft / 5, Content.MoveY
									+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft
									* 2 / 3 + yLeft / 2 + x1 + x1 * 3), null);//
		} else if (Content.coin >= 100 && Content.coin < 999) {
			int a = Content.coin / 100;// ��λ
			int b = Content.coin % 100 / 10;// ʮλ
			int c = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 2 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3), null);//
		} else if (Content.coin >= 1000 && Content.coin < 9999) {
			int a = Content.coin / 1000;// ǧλ
			int b = Content.coin % 1000 / 100;// ��λ
			int c = (Content.coin / 10) % 10;// ʮλ
			int d = (Content.coin % 100) % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 3 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 3 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5 - xLeft / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft * 2 / 3
							+ xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
		} else if (Content.coin >= 10000 && Content.coin < 99999) {
			int a = Content.coin / 10000;// ��λ
			int b = Content.coin / 1000 % 10;// ǧλ
			int c = Content.coin / 100 % 10;// ��λ
			int d = Content.coin / 10 % 10;// ʮλ
			int e = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 3 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 3 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5 - xLeft / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft * 2 / 3
							+ xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[e][0],
					Content.num[e][1], Content.num[e][0] + Content.num[e][2],
					Content.num[e][1] + Content.num[e][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft + xLeft * 2
							/ 5 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 + x1
							* 3), null);//
		}
	}

	// ��ҷ���2(Ϊ�˴�playStyle=3�л���1������Ч��)
	private void drawCoin2(Canvas canvas) {		
		// coin ��ֵ������ܷ�����,ÿ��һλ��������ǰ�ƶ�xLeft/9�ĵ�λ���ÿ���
		if (Content.coin >= 0 && Content.coin < 10) {
			canvas.drawBitmap(yellowFontBitmap,
					new Rect(Content.num[Content.coin][0],
							Content.num[Content.coin][1],
							Content.num[Content.coin][0]
									+ Content.num[Content.coin][2],
							Content.num[Content.coin][1]
									+ Content.num[Content.coin][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6,
							Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2
							+ yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight, Content.MoveX
							+ x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft / 3,
							Content.MoveY + mScreenHeight * 3 / 4 - x1 * 2
									+ yLeft * 2 / 3 + yLeft / 2 + x1 + x1 * 3-mScreenHeight),
					null);// rebetͼƬ
		} else if (Content.coin >= 10 && Content.coin < 100) {
			int a = Content.coin / 10;// ʮλ
			int b = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
							Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6
									+ xLeft / 3 - xLeft / 9, Content.MoveY
									+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft
									* 2 / 3 + yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]),
					new RectF(Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft
							/ 6 + xLeft / 5, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
							Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6
									+ xLeft / 3 + xLeft / 5, Content.MoveY
									+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft
									* 2 / 3 + yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
		} else if (Content.coin >= 100 && Content.coin < 999) {
			int a = Content.coin / 100;// ��λ
			int b = Content.coin % 100 / 10;// ʮλ
			int c = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 2 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3-mScreenHeight), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3-mScreenHeight), null);//
		} else if (Content.coin >= 1000 && Content.coin < 9999) {
			int a = Content.coin / 1000;// ǧλ
			int b = Content.coin % 1000 / 100;// ��λ
			int c = (Content.coin / 10) % 10;// ʮλ
			int d = (Content.coin % 100) % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 3 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 3 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3-mScreenHeight), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5 - xLeft / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2-mScreenHeight, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft * 2 / 3
							+ xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
		} else if (Content.coin >= 10000 && Content.coin < 99999) {
			int a = Content.coin / 10000;// ��λ
			int b = Content.coin / 1000 % 10;// ǧλ
			int c = Content.coin / 100 % 10;// ��λ
			int d = Content.coin / 10 % 10;// ʮλ
			int e = Content.coin % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 - xLeft
							* 3 / 9, Content.MoveY + mScreenHeight * 3 / 4 - x1
							* 2 + yLeft * 2 / 3 + yLeft / 2 + x1 * 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 - xLeft * 3 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							+ x1 * 3-mScreenHeight), null);//

			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 5 - xLeft * 2 / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft / 5 - xLeft * 2 / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 5 - xLeft / 9, Content.MoveY + mScreenHeight
							* 3 / 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1
							* 2-mScreenHeight,
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[d][0],
					Content.num[d][1], Content.num[d][0] + Content.num[d][2],
					Content.num[d][1] + Content.num[d][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							/ 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2-mScreenHeight, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft * 2 / 3
							+ xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 + x1 * 3-mScreenHeight), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[e][0],
					Content.num[e][1], Content.num[e][0] + Content.num[e][2],
					Content.num[e][1] + Content.num[e][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft * 16 / 3 + xLeft / 6 + xLeft
							* 2 / 3 + xLeft * 2 / 5 - xLeft / 9, Content.MoveY
							+ mScreenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft / 2 + x1 * 2-mScreenHeight, Content.MoveX + x1 * 4
							+ xLeft * 16 / 3 + xLeft / 6 + xLeft + xLeft * 2
							/ 5 - xLeft / 9, Content.MoveY + mScreenHeight * 3
							/ 4 - x1 * 2 + yLeft * 2 / 3 + yLeft / 2 + x1 + x1
							* 3-mScreenHeight), null);//
		}
	}

	int countOdds = 0;

	// �������ֲ�,ÿ�β�ͬ
	private void drawOdds(Canvas canvas) {
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 - j; i++) {
				if (MyGameView.playStyle == 3 || MyGameView.playStyle == 5) {
					if (MyGameView.odds == Content.odds[count]) {
						countOdds++;
						if (countOdds == 3) {
							drawOdds2(canvas, j, i);
							countOdds = 1;
						}
					} else {
						drawOdds2(canvas, j, i);
					}
				} else if (MyGameView.playStyle == 1
						|| MyGameView.playStyle == 4) {
					drawOdds2(canvas, j, i);
				}

				// betsѹ����
				if (Content.bets[count] < 10) {
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[Content.bets[count]][0],
							Content.num[Content.bets[count]][1],
							Content.num[Content.bets[count]][0]
									+ Content.num[Content.bets[count]][2],
							Content.num[Content.bets[count]][1]
									+ Content.num[Content.bets[count]][3]),
							new RectF(Content.MoveX + x1 * 4 + xLeft / 3
									+ xLeft * 2 / 5 + xLeft * i, Content.MoveY
									+ yLeft / 2 + x1 * 5 + yLeft * j * 15 / 16
									+ yLeft / 2, Content.MoveX + x1 * 4 + xLeft
									/ 3 + xLeft * 3 / 5 + xLeft * i,
									Content.MoveY + x1 * 5 + yLeft + yLeft * j
											* 15 / 16 + yLeft / 2), null);//
				} else if (Content.bets[count] >= 10
						&& Content.bets[count] < 100) {
					int a = Content.bets[count] / 10;// ʮλ
					int b = Content.bets[count] % 10;// ��λ
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[a][0], Content.num[a][1],
							Content.num[a][0] + Content.num[a][2],
							Content.num[a][1] + Content.num[a][3]), new RectF(
							Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 2 / 5
									- xLeft / 9 + xLeft * i, Content.MoveY
									+ yLeft / 2 + x1 * 5 + yLeft * j * 15 / 16
									+ yLeft / 2,
							Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5
									- xLeft / 9 + xLeft * i, Content.MoveY + x1
									* 5 + yLeft + yLeft * j * 15 / 16 + yLeft
									/ 2), null);//
					canvas.drawBitmap(redFontBitmap, new Rect(
							Content.num[b][0], Content.num[b][1],
							Content.num[b][0] + Content.num[b][2],
							Content.num[b][1] + Content.num[b][3]), new RectF(
							Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5
									- xLeft / 9 + xLeft * i, Content.MoveY
									+ yLeft / 2 + x1 * 5 + yLeft * j * 15 / 16
									+ yLeft / 2,
							Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 4 / 5
									- xLeft / 9 + xLeft * i, Content.MoveY + x1
									* 5 + yLeft + yLeft * j * 15 / 16 + yLeft
									/ 2), null);//

				}
				count++;
				if (count == 15) {
					count = 0;
				}
			}

		}
	}

	private void drawOdds2(Canvas canvas, int j, int i) {
		// ������
		if (Content.odds[count] < 10) {
			canvas.drawBitmap(yellowFontBitmap, new Rect(
					Content.num[Content.odds[count]][0],
					Content.num[Content.odds[count]][1],
					Content.num[Content.odds[count]][0]
							+ Content.num[Content.odds[count]][2],
					Content.num[Content.odds[count]][1]
							+ Content.num[Content.odds[count]][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 2 / 5 + xLeft
							* i, Content.MoveY + yLeft / 2 + x1 * 5 + yLeft
							/ 12 + yLeft * j * 15 / 16, Content.MoveX + x1 * 4
							+ xLeft / 3 + xLeft * 3 / 5 + xLeft * i,
					Content.MoveY + x1 * 5 + yLeft + yLeft / 12 + yLeft * j
							* 15 / 16), null);//
		} else if (Content.odds[count] >= 10 && Content.odds[count] < 100) {
			int a = Content.odds[count] / 10;// ʮλ
			int b = Content.odds[count] % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 2 / 5 - xLeft
							/ 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft / 9
							+ xLeft * i, Content.MoveY + x1 * 5 + yLeft + yLeft
							/ 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft
							/ 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft * 4 / 5 - xLeft / 9
							+ xLeft * i, Content.MoveY + x1 * 5 + yLeft + yLeft
							/ 12 + yLeft * j * 15 / 16), null);//

		} else if (Content.odds[count] >= 100 && Content.odds[count] < 1000) {
			int a = Content.odds[count] / 100;// ��λ
			int b = Content.odds[count] % 100 / 10;// ʮλ
			int c = Content.odds[count] % 10;// ��λ
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[a][0],
					Content.num[a][1], Content.num[a][0] + Content.num[a][2],
					Content.num[a][1] + Content.num[a][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 2 / 5 - xLeft
							/ 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft / 9
							+ xLeft * i, Content.MoveY + x1 * 5 + yLeft + yLeft
							/ 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[b][0],
					Content.num[b][1], Content.num[b][0] + Content.num[b][2],
					Content.num[b][1] + Content.num[b][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft
							/ 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft * 4 / 5 - xLeft / 9
							+ xLeft * i, Content.MoveY + x1 * 5 + yLeft + yLeft
							/ 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[c][0],
					Content.num[c][1], Content.num[c][0] + Content.num[c][2],
					Content.num[c][1] + Content.num[c][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 4 / 5 - xLeft
							/ 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft - xLeft / 9 + xLeft
							* i, Content.MoveY + x1 * 5 + yLeft + yLeft / 12
							+ yLeft * j * 15 / 16), null);//

		} else if (Content.odds[count] == 1000) {
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[1][0],
					Content.num[1][1], Content.num[1][0] + Content.num[1][2],
					Content.num[1][1] + Content.num[1][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 2 / 5 - xLeft
							* 2 / 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1
							* 5 + yLeft / 12 + yLeft * j * 15 / 16,
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft
							* 2 / 9 + xLeft * i, Content.MoveY + x1 * 5 + yLeft
							+ yLeft / 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 3 / 5 - xLeft
							* 2 / 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1
							* 5 + yLeft / 12 + yLeft * j * 15 / 16,
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 4 / 5 - xLeft
							* 2 / 9 + xLeft * i, Content.MoveY + x1 * 5 + yLeft
							+ yLeft / 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft * 4 / 5 - xLeft
							* 2 / 9 + xLeft * i, Content.MoveY + yLeft / 2 + x1
							* 5 + yLeft / 12 + yLeft * j * 15 / 16,
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft - xLeft * 2 / 9
							+ xLeft * i, Content.MoveY + x1 * 5 + yLeft + yLeft
							/ 12 + yLeft * j * 15 / 16), null);//
			canvas.drawBitmap(yellowFontBitmap, new Rect(Content.num[0][0],
					Content.num[0][1], Content.num[0][0] + Content.num[0][2],
					Content.num[0][1] + Content.num[0][3]), new RectF(
					Content.MoveX + x1 * 4 + xLeft / 3 + xLeft - xLeft * 2 / 9
							+ xLeft * i, Content.MoveY + yLeft / 2 + x1 * 5
							+ yLeft / 12 + yLeft * j * 15 / 16, Content.MoveX
							+ x1 * 4 + xLeft / 3 + xLeft * 6 / 5 - xLeft * 2
							/ 9 + xLeft * i, Content.MoveY + x1 * 5 + yLeft
							+ yLeft / 12 + yLeft * j * 15 / 16), null);//
		}
	}

}
