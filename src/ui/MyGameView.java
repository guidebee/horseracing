package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.xin_game_horse2.R;

import utils.BackGroundManager;
import utils.Content;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//�Զ����mySurfaceView�࣬�̳�sufaceViewʵ��surfaceHolder.clllback���߳�Runnable
public class MyGameView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {

	private SurfaceHolder sh;// SurfaceView������
	private Canvas iCanvas;// ����
	private Paint ipaint;// ����
	private Path ipath;// ·��
	private static boolean isRunning = true;// ������������ѭ���ύ��ͼ������ֱ���˳�false
	private float screenWidth, screenHeight;// ��Ļ��/��
	float x, y;// С�����ʱ��x,y����
	Bitmap bitmap_Bird;
	Context mContext;
	private BackGroundManager backGroundManager = null;// ����������

	private static MediaPlayer waittingPlay = null;// ���ֲ�����--�ȴ�
	private static MediaPlayer runPlay = null;// ���ֲ�����--����ʱ
	private static MediaPlayer win1Play = null;// ���ֲ�����--�н�ʱ
	private static SoundPool soundPool;// ��Ч������(�̵ķ�Ӧ�ٶ�Ҫ��ߵ�����)
	//static int waittingSound;// �ȴ���ʼ�����ļ�
	static int betSound;// ��ע�����ļ�
	static int reBetSound;// ������ע���������ļ�
	Bitmap bitmap_ready, bitmap_over;
	public static boolean isPlay = false;
	public static int playStyle = 1;// ��Ϸ����״̬��1��ѹעʱ��2������ʱ��3�����ʱ��
	static boolean isGameOver = false;

	private int x1;// ��ɫ��������Ļ�ľ���
	private float xLeft;// ��ע��һ������
	private float yLeft;// ��ע��һ������
	private boolean isBet = false;
	private int betNum = 1;// ���ʣ�������ҵ���仯
	private boolean isStart = false;
	static Random random = new Random();
	int temp;


	public static List<int[]> resultLists;
	int[] result = new int[3];
	Thread myThread = new Thread();
	
	
	static SharedPreferences share;// ����
	SharedPreferences.Editor shareEdit;
	int creditNum = 0;// ��ʼ����

	// �������ĲεĹ��췽����������ʵ�������
	public MyGameView(Context context) {
		super(context);
		System.out.println("MyGameView");
		mContext = context;
		
		

		share = context.getSharedPreferences("xin_game_horse",
				Activity.MODE_PRIVATE);
		shareEdit = share.edit(); // �༭�ļ�
		Content.coin = share.getInt("score", 500);
		//Content.coin = 500;
		
		

		resultLists = new ArrayList<int[]>();


		waittingPlay = MediaPlayer.create(context, R.raw.waitting);// ����Ч
		waittingPlay.setLooping(true);// ����ѭ��	
		win1Play = MediaPlayer.create(context, R.raw.winer);// ����Ч
		win1Play.setLooping(true);// ����ѭ��
		runPlay = MediaPlayer.create(context, R.raw.background_music);// ����Ч
		runPlay.setLooping(true);// ����ѭ��

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		//waittingSound = soundPool.load(context, R.raw.waitting, 1);
		betSound = soundPool.load(context, R.raw.bet, 1);
		reBetSound = soundPool.load(context, R.raw.button, 1);
		// pointSound = soundPool.load(context, R.raw.point, 1);

		sh = this.getHolder();// ��surfaceHolder������
		ipaint = new Paint();// ��ȡ����
		ipath = new Path();// ��·��
		ipaint.setColor(Color.RED);// ���û�����ɫ
		ipaint.setStyle(Style.STROKE);// ���û�����ʽ()
		ipaint.setStrokeWidth(5);// ���û��ʵĿ��

		sh.addCallback(this);// ����������(��ӻص�����)
		this.setFocusable(true);// ���õ�ǰ����

		// ������Ϸ��������Timer,��10���ÿ��10�������һ��MyTimerTask��run�����������Ǹ����ڷ�+1
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 10000, 10000);
	}
	
	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (Content.coin < 30) {
				Content.coin = Content.coin + 1;
			}
		}

	}

	// ��ѭ��ÿ��100��������Զ�����廭�ķ�����ֱ���˳���Ϊfalse
	@Override
	public void run() {
		while (isRunning) {
			drawView();// �����Զ�����廭�ķ���
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	boolean isAddResult = true;
	// �Զ��廭��
	private void drawView() {
		try {
			if (sh != null) {
				iCanvas = sh.lockCanvas();// ��ȡ����
				// ���û��ʺ�λͼû�о��
				iCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
						Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
				//System.out.println("playStyle="+playStyle);
				
				if(playStyle == 5){
					if(Content.MoveY<screenHeight){
						Content.MoveY = Content.MoveY + 80;
						backGroundManager.drawTrackMoveDown(iCanvas);
					}else{
						backGroundManager.isOne = true;
						Content.MoveY = 0;
						playStyle = 1;
						isPlay = false;
						getSequence();// �仯������
						clearBets();// �������ѹע����ȫ����0
						//Content.coin = Content.resultWonNum+Content.coin;
					}
				}
				
				if(playStyle == 4){
					if(Math.abs(Content.MoveX)<screenWidth){						
						Content.MoveX = Content.MoveX-160;
						backGroundManager.drawTrackMoveLeft(iCanvas);
					}else{
						Content.MoveX = 0;
						isPlay = true;
						playStyle = 2;//����2������
						isAddResult = true;
					}
				}
				
				if (playStyle == 2) {
					if(waittingPlay.isPlaying()){
						waittingPlay.pause();
					}
					
					if(isAddResult){
						getResult();
						isAddResult = false;
					}
					if(BackGroundManager.isEnd){
						if(runPlay.isPlaying()){
							System.out.println("��ʼ2����������");
							runPlay.pause();//��ͣ��������
						}
						if(Content.bets[Content.resultNum]>0){						
							System.out.println("�н����ֲ���ʱ��ѹע�֣�"+Content.bets[Content.resultNum]);
							win1Play.start();//�����н�����							
						}
					}else{
						if(!runPlay.isPlaying()){
							System.out.println("��ʼ2����������");
							runPlay.start();//��ʼ��������
						}
					}
					backGroundManager.drawTrack(iCanvas);
				} else {
					if(runPlay.isPlaying()){
						System.out.println("��ʼ2����������");
						runPlay.pause();//ֹͣ��������
					}
					backGroundManager.drawBackGround(iCanvas, this, isBet,
							betNum, isStart);// ������
					if(playStyle==1){
						if(!waittingPlay.isPlaying()){
							waittingPlay.start();
						}
					}
					if(playStyle==3){
						if(win1Play.isPlaying()){
							win1Play.pause();
						}
						if(Content.resultWonNum>0){
							Content.resultWonNum--;
							Content.coin++;
						}
					}
				}

			}

		} catch (Exception e) {
		} finally {
			if (iCanvas != null) {
				sh.unlockCanvasAndPost(iCanvas);// �����������ύ
			}
		}

	}

	private float mouseX, mouseY;// �����ʱ������x,y

	// �����¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// ����ȥ
			// System.out.println("down");
			mouseX = event.getX();
			mouseY = event.getY();
			if (playStyle == 1) {// ��ѹעʱ�����ܵ��rebet
				if (mouseX >= x1 * 4 + xLeft * 8 / 3
						&& mouseX <= x1 * 4 + xLeft * 8 / 3 + xLeft
						&& mouseY >= x1 * 4 + yLeft * 9 / 2
						&& mouseY <= x1 * 4 + yLeft * 9 / 2 + yLeft) {
					System.out.println("���ڵ��rebet");
					isBet = true;

				}
			}
			if (mouseX >= x1 * 4 + xLeft * 5 / 2
					&& mouseX <= x1 * 4 + xLeft + xLeft * 7 / 2
					&& mouseY >= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
					&& mouseY <= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft) {
				System.out.println("���ڵ��start");
				isStart = true;

			}

			break;
		case MotionEvent.ACTION_UP:// �ſ�
//			if (playStyle == 2) {
//				System.out.println("����2222222222");
//				isPlay = false;
//				playStyle = 3;// 
//				//getResult();
//			}
			// ���rebet
			if (playStyle == 1) {// ��ѹעʱ�����ܵ��rebet
				if (mouseX >= x1 * 4 + xLeft * 8 / 3
						&& mouseX <= x1 * 4 + xLeft * 8 / 3 + xLeft
						&& mouseY >= x1 * 4 + yLeft * 9 / 2
						&& mouseY <= x1 * 4 + yLeft * 9 / 2 + yLeft) {
					System.out.println("���ڷſ�������rebet");
					soundPool.play(reBetSound, 1, 1, 1, 0, 1);
					isBet = false;
					if (betNum == 1) {
						betNum = 2;
					} else if (betNum == 2) {
						betNum = 5;
					} else if (betNum == 5) {
						betNum = 10;
					} else if (betNum == 10) {
						betNum = 80;
					} else if (betNum == 80) {
						betNum = 1;
					}
					System.out.println("�ɿ�rebet��betNum=" + betNum);
				}
			}
			// ���start
			if (mouseX >= x1 * 4 + xLeft * 5 / 2
					&& mouseX <= x1 * 4 + xLeft + xLeft * 7 / 2
					&& mouseY >= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
					&& mouseY <= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft) {
				System.out.println("���ڵ��start");				
				isStart = false;
				if (playStyle == 3) {
					soundPool.play(reBetSound, 1, 1, 1, 0, 1);
					
					playStyle = 5;//�����ƶ����л���playStyle=1;

//					playStyle = 1;
//					isPlay = false;
//					getSequence();// �仯������
//					clearBets();// �������ѹע����ȫ����0
//					Content.coin = Content.resultWonNum+Content.coin;
				}

				if (!isPlay && playStyle == 1 && getbetNoNull()) {// ��ѹעstyle���Ծ���ע�����ܵ����ʼ
					soundPool.play(reBetSound, 1, 1, 1, 0, 1);
					playStyle = 4;//�����ƶ�����ʼ����
					
//					isPlay = true;
//					playStyle = 2;//����2������
//					isAddResult = true;
					
					

				}

			}
		

			if (playStyle == 1) {// ��ѹעstyleʱ�����ܵ�������ע��
				// ��ע�����������һ��15�����ʷ���
				for (int j = 0; j < 5; j++) {
					for (int i = 0; i < 5 - j; i++) {
						if (mouseX >= x1 * 4 + xLeft / 3 + xLeft * i
								&& mouseX <= x1 * 4 + xLeft / 3 + xLeft + xLeft
										* i
								&& mouseY >= yLeft / 2 + x1 * 5 + yLeft * j
								&& mouseY <= yLeft / 2 + x1 * 5 + yLeft + yLeft
										* j) {
							soundPool.play(betSound, 1, 1, 1, 0, 1);

							// System.out.println("�ҵ����ѹע������Ϊj="+j+"  i="+i);
							int indext = getIndext2XY(j, i);
							System.out.println("�±�indext=" + indext);
							if (Content.coin > 0) {
								if (Content.coin >= betNum
										&& betNum + Content.bets[indext] <= 80) {
									Content.bets[indext] = betNum
											+ Content.bets[indext];
									Content.coin = Content.coin - betNum;
								} else {
									if(Content.bets[indext]+Content.coin<=80){
										System.out.println("zzzzzzzzzzzzzzzzzzzzz");
										Content.bets[indext] = Content.bets[indext]+Content.coin;
										Content.coin = 0;
									}else{
										System.out.println("xxxxxxxxxxxxxxxxxx");
										if(Content.bets[indext]!=80){											
											Content.coin =Content.coin-( 80 - Content.bets[indext]);
											Content.bets[indext] = 80;
										}
									}
								}

							}

						}

					}
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:// �ƶ�
			break;
		default:
			break;
		}
		// invalidate();//�����ػ�
		return true;// ����true���ύ����ǰ��ͼ

	}
	

	// ����Ƿ�����ע,�Ƿ���true
	private boolean isBet() {
		for (int i = 0; i < Content.bets.length; i++) {
			if (Content.bets[i] > 0) {
				return true;
			}
		}
		return false;
	}

	public static int odds = 0;
	
	private void getResult() {
		// TODO �Զ����ɵķ������
		//��ȡ�ܳ��������±�
		Content.resultNum = getIndex(getRandomCoin());
		System.out.println("resultNum=" + Content.resultNum);
		System.out.println("��ע����Ϊ="+Content.bets[Content.resultNum]);
		odds = Content.odds[Content.resultNum];
		System.out.println("�ܳ��ķ�����=" + odds);
		int[] result = getResultNum2Indext(Content.resultNum);
		int x = result[0];
		int y = result[1];
		System.out.println("x=" + x);
		System.out.println("y=" + y);

		int[] resultAAA = new int[3];
		Content.result[0] = x;
		Content.result[1] = y;
		Content.result[2] = odds;
		resultAAA[0] = x;
		resultAAA[1] = y;
		resultAAA[2] = odds;
		resultLists.add(resultAAA);
		
		Content.resultWonNum = odds*Content.bets[Content.resultNum];
		

	}
	//ͨ���ܳ��ķ������ó����±�
	private int getIndex(int num){		
		for(int i = 0;i<15;i++){
			if(Content.odds[i]==num){
				return i;
			}
		}
		return -1;
	}
	//��ȡ�������������ͬ�ķ����в�ͬ�ķ��ر��ʣ�3��4��5��60%����������500��100��1%��
	private int getRandomCoin(){
		int result;
		int num = random.nextInt(100);
		System.out.println("num="+num);
		if(num<50){
			result = random.nextInt(3);
			switch (result) {
			case 0:
				return 3;				
			case 1:
				return 4;
			case 2:
				return 5;

			default:
				break;
			}
			
		}else if(num>=50 && num<75){
			result = random.nextInt(2);
			switch (result) {
			case 0:
				return 8;
			case 1:
				return 10;

			default:
				break;
			}
		}else if(num>=75 && num<90){
			result = random.nextInt(4);
			switch (result) {
			case 0:
				return 20;
			case 1:
				return 30;
			case 2:
				return 60;
			case 3:
				return 80;

			default:
				break;
			}
		}else if(num>=90 && num<99){
			result = random.nextInt(4);
			switch (result) {
			case 0:
				return 100;
			case 1:
				return 125;
			case 2:
				return 175;
			case 3:
				return 250;

			default:
				break;
			}
		}else {
			result = random.nextInt(2);
			switch (result) {
			case 0:
				return 500;
			case 1:
				return 1000;
			default:
				break;
			}
		}
		return -1;
		
	}

	// ���ѹע��
	private void clearBets() {
		for (int i = 0; i < Content.bets.length; i++) {
			if (Content.bets[i] > 0) {
				Content.bets[i] = 0;
			}
		}

	}

	private boolean getbetNoNull() {
		for (int i = 0; i < Content.bets.length; i++) {
			if (Content.bets[i] > 0) {
				return true;
			}
		}

		return false;

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(this).start();// �����߳�
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		screenWidth = (float) getWidth();
		screenHeight = (float) getHeight();
		System.out.println("screenWidth=" + screenWidth);
		System.out.println("screenHeight=" + screenHeight);
		x1 = (int) (screenWidth / 80);
		xLeft = (screenWidth * 3 / 4 - x1 * 5) * 3 / 16;
		yLeft = (screenHeight * 3 / 4 - x1 * 6) * 2 / 11;

		backGroundManager = new BackGroundManager(this, screenWidth,
				screenHeight, x1, xLeft, yLeft);

		getSequence();
		

	}
	
	

	// ��ȡ���׬����
	private void getSequence() {
		for (int i = 0; i < 15; i++) {
			int num = random.nextInt(15);
			temp = Content.odds[i];
			Content.odds[i] = Content.odds[num];
			Content.odds[num] = temp;
		}
		for (int i = 0; i < 15; i++) {
			System.out.println("odds=" + Content.odds[i]);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// �˳����棬����ѭ������Ϊfalse�����߳�ͣ�ˣ���˯��300���룬�������˳�
		
		Content.coin = Content.coin + Content.resultWonNum;
		shareEdit.putInt("score", Content.coin); // �˳�ʱ���������
		shareEdit.commit(); // �ύ
		isRunning = false;
		//zhandouPlay.stop();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isRunning = false;
			waittingPlay.stop();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		return true;
		// return super.onKeyDown(keyCode, event);
	}

	public static void gameOver() {
		System.out.println("gameOver");
		waittingPlay.stop();
		// soundPool.play(dieSound, 1, 1, 1, 0, 1);
		isGameOver = true;

	}

	int[] results = new int[2];

	// �����ܳ��Ľ�����������ֵ��������x,y������
	private int[] getResultNum2Indext(int resultNum) {
		switch (resultNum) {
		case 0:
			results[0] = 1;
			results[1] = 6;
			return results;
		case 1:
			results[0] = 1;
			results[1] = 5;
			return results;
		case 2:
			results[0] = 1;
			results[1] = 4;
			return results;
		case 3:
			results[0] = 1;
			results[1] = 3;
			return results;
		case 4:
			results[0] = 1;
			results[1] = 2;
			return results;
		case 5:
			results[0] = 2;
			results[1] = 6;
			return results;
		case 6:
			results[0] = 2;
			results[1] = 5;
			return results;
		case 7:
			results[0] = 2;
			results[1] = 4;
			return results;
		case 8:
			results[0] = 2;
			results[1] = 3;
			return results;
		case 9:
			results[0] = 3;
			results[1] = 6;
			return results;
		case 10:
			results[0] = 3;
			results[1] = 5;
			return results;
		case 11:
			results[0] = 3;
			results[1] = 4;
			return results;
		case 12:
			results[0] = 4;
			results[1] = 6;
			return results;
		case 13:
			results[0] = 4;
			results[1] = 5;
			return results;
		case 14:
			results[0] = 5;
			results[1] = 6;
			return results;

		default:
			break;
		}
		return null;
	}

	// ����j,i������㣬����bets������±�
	private int getIndext2XY(int j, int i) {

		if (j == 0) {
			switch (i) {
			case 0:
				return 0;
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
			default:
				break;
			}
		} else if (j == 1) {
			switch (i) {
			case 0:
				return 5;
			case 1:
				return 6;
			case 2:
				return 7;
			case 3:
				return 8;
			default:
				break;
			}
		} else if (j == 2) {
			switch (i) {
			case 0:
				return 9;
			case 1:
				return 10;
			case 2:
				return 11;
			default:
				break;
			}
		} else if (j == 3) {
			switch (i) {
			case 0:
				return 12;
			case 1:
				return 13;
			default:
				break;
			}
		} else if (j == 4) {
			return 14;
		}

		return -1;
	}

}
