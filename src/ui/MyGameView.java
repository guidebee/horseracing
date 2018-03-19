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

//自定义的mySurfaceView类，继承sufaceView实现surfaceHolder.clllback和线程Runnable
public class MyGameView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {

	private SurfaceHolder sh;// SurfaceView控制器
	private Canvas iCanvas;// 画布
	private Paint ipaint;// 画笔
	private Path ipath;// 路径
	private static boolean isRunning = true;// 布尔参数，死循环提交绘图方法，直到退出false
	private float screenWidth, screenHeight;// 屏幕宽/高
	float x, y;// 小鸟出现时的x,y坐标
	Bitmap bitmap_Bird;
	Context mContext;
	private BackGroundManager backGroundManager = null;// 背景管理类

	private static MediaPlayer waittingPlay = null;// 音乐播放器--等待
	private static MediaPlayer runPlay = null;// 音乐播放器--跑马时
	private static MediaPlayer win1Play = null;// 音乐播放器--中奖时
	private static SoundPool soundPool;// 音效播放器(短的反应速度要求高的声音)
	//static int waittingSound;// 等待开始声音文件
	static int betSound;// 下注声音文件
	static int reBetSound;// 更改下注倍数声音文件
	Bitmap bitmap_ready, bitmap_over;
	public static boolean isPlay = false;
	public static int playStyle = 1;// 游戏几种状态（1＝压注时，2＝跑马时，3＝结果时）
	static boolean isGameOver = false;

	private int x1;// 红色红区离屏幕的距离
	private float xLeft;// 下注区一个横格宽
	private float yLeft;// 下注区一个横格高
	private boolean isBet = false;
	private int betNum = 1;// 赔率，根据玩家点击变化
	private boolean isStart = false;
	static Random random = new Random();
	int temp;


	public static List<int[]> resultLists;
	int[] result = new int[3];
	Thread myThread = new Thread();
	
	
	static SharedPreferences share;// 共享
	SharedPreferences.Editor shareEdit;
	int creditNum = 0;// 初始分数

	// 带上下文参的构造方法，在里面实例化组件
	public MyGameView(Context context) {
		super(context);
		System.out.println("MyGameView");
		mContext = context;
		
		

		share = context.getSharedPreferences("xin_game_horse",
				Activity.MODE_PRIVATE);
		shareEdit = share.edit(); // 编辑文件
		Content.coin = share.getInt("score", 500);
		//Content.coin = 500;
		
		

		resultLists = new ArrayList<int[]>();


		waittingPlay = MediaPlayer.create(context, R.raw.waitting);// 绑定音效
		waittingPlay.setLooping(true);// 音乐循环	
		win1Play = MediaPlayer.create(context, R.raw.winer);// 绑定音效
		win1Play.setLooping(true);// 音乐循环
		runPlay = MediaPlayer.create(context, R.raw.background_music);// 绑定音效
		runPlay.setLooping(true);// 音乐循环

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		//waittingSound = soundPool.load(context, R.raw.waitting, 1);
		betSound = soundPool.load(context, R.raw.bet, 1);
		reBetSound = soundPool.load(context, R.raw.button, 1);
		// pointSound = soundPool.load(context, R.raw.point, 1);

		sh = this.getHolder();// 获到surfaceHolder控制器
		ipaint = new Paint();// 获取画笔
		ipath = new Path();// 获到路径
		ipaint.setColor(Color.RED);// 设置画笔颜色
		ipaint.setStyle(Style.STROKE);// 设置画笔样式()
		ipaint.setStrokeWidth(5);// 设置画笔的宽度

		sh.addCallback(this);// 监听控制器(添加回调函数)
		this.setFocusable(true);// 设置当前焦点

		// 开启游戏，就启动Timer,在10秒后，每隔10秒就运行一次MyTimerTask的run操做，这里是给现在分+1
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

	// 死循环每隔100毫秒调用自定义具体画的方法，直到退出设为false
	@Override
	public void run() {
		while (isRunning) {
			drawView();// 调用自定义具体画的方法
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	boolean isAddResult = true;
	// 自定义画法
	private void drawView() {
		try {
			if (sh != null) {
				iCanvas = sh.lockCanvas();// 获取画布
				// 设置画笔和位图没有锯齿
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
						getSequence();// 变化赔率区
						clearBets();// 清除上轮压注区，全部归0
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
						playStyle = 2;//跳到2跑马画面
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
							System.out.println("开始2号跑马音乐");
							runPlay.pause();//暂停播放音乐
						}
						if(Content.bets[Content.resultNum]>0){						
							System.out.println("中奖音乐播放时的压注分＝"+Content.bets[Content.resultNum]);
							win1Play.start();//播放中奖音乐							
						}
					}else{
						if(!runPlay.isPlaying()){
							System.out.println("开始2号跑马音乐");
							runPlay.start();//开始播放音乐
						}
					}
					backGroundManager.drawTrack(iCanvas);
				} else {
					if(runPlay.isPlaying()){
						System.out.println("开始2号跑马音乐");
						runPlay.pause();//停止播放音乐
					}
					backGroundManager.drawBackGround(iCanvas, this, isBet,
							betNum, isStart);// 画背景
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
				sh.unlockCanvasAndPost(iCanvas);// 解锁画布并提交
			}
		}

	}

	private float mouseX, mouseY;// 鼠标点击时的坐标x,y

	// 触屏事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 按下去
			// System.out.println("down");
			mouseX = event.getX();
			mouseY = event.getY();
			if (playStyle == 1) {// 在压注时，才能点击rebet
				if (mouseX >= x1 * 4 + xLeft * 8 / 3
						&& mouseX <= x1 * 4 + xLeft * 8 / 3 + xLeft
						&& mouseY >= x1 * 4 + yLeft * 9 / 2
						&& mouseY <= x1 * 4 + yLeft * 9 / 2 + yLeft) {
					System.out.println("我在点击rebet");
					isBet = true;

				}
			}
			if (mouseX >= x1 * 4 + xLeft * 5 / 2
					&& mouseX <= x1 * 4 + xLeft + xLeft * 7 / 2
					&& mouseY >= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
					&& mouseY <= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft) {
				System.out.println("我在点击start");
				isStart = true;

			}

			break;
		case MotionEvent.ACTION_UP:// 放开
//			if (playStyle == 2) {
//				System.out.println("我是2222222222");
//				isPlay = false;
//				playStyle = 3;// 
//				//getResult();
//			}
			// 点击rebet
			if (playStyle == 1) {// 在压注时，才能点击rebet
				if (mouseX >= x1 * 4 + xLeft * 8 / 3
						&& mouseX <= x1 * 4 + xLeft * 8 / 3 + xLeft
						&& mouseY >= x1 * 4 + yLeft * 9 / 2
						&& mouseY <= x1 * 4 + yLeft * 9 / 2 + yLeft) {
					System.out.println("我在放开开开开rebet");
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
					System.out.println("松开rebet后betNum=" + betNum);
				}
			}
			// 点击start
			if (mouseX >= x1 * 4 + xLeft * 5 / 2
					&& mouseX <= x1 * 4 + xLeft + xLeft * 7 / 2
					&& mouseY >= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
					&& mouseY <= screenHeight * 3 / 4 - x1 * 2 + yLeft * 2 / 3
							+ yLeft) {
				System.out.println("我在点击start");				
				isStart = false;
				if (playStyle == 3) {
					soundPool.play(reBetSound, 1, 1, 1, 0, 1);
					
					playStyle = 5;//向下移动，切换到playStyle=1;

//					playStyle = 1;
//					isPlay = false;
//					getSequence();// 变化赔率区
//					clearBets();// 清除上轮压注区，全部归0
//					Content.coin = Content.resultWonNum+Content.coin;
				}

				if (!isPlay && playStyle == 1 && getbetNoNull()) {// 在压注style且以经下注，才能点击开始
					soundPool.play(reBetSound, 1, 1, 1, 0, 1);
					playStyle = 4;//向左移动，开始跑马
					
//					isPlay = true;
//					playStyle = 2;//跳到2跑马画面
//					isAddResult = true;
					
					

				}

			}
		

			if (playStyle == 1) {// 在压注style时，才能点击点击下注区
				// 下注（点击赔率区一共15个赔率方格）
				for (int j = 0; j < 5; j++) {
					for (int i = 0; i < 5 - j; i++) {
						if (mouseX >= x1 * 4 + xLeft / 3 + xLeft * i
								&& mouseX <= x1 * 4 + xLeft / 3 + xLeft + xLeft
										* i
								&& mouseY >= yLeft / 2 + x1 * 5 + yLeft * j
								&& mouseY <= yLeft / 2 + x1 * 5 + yLeft + yLeft
										* j) {
							soundPool.play(betSound, 1, 1, 1, 0, 1);

							// System.out.println("我点击了压注区坐标为j="+j+"  i="+i);
							int indext = getIndext2XY(j, i);
							System.out.println("下标indext=" + indext);
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
		case MotionEvent.ACTION_MOVE:// 移动
			break;
		default:
			break;
		}
		// invalidate();//立刻重绘
		return true;// 返回true，提交给当前视图

	}
	

	// 检查是否以下注,是返回true
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
		// TODO 自动生成的方法存根
		//获取跑出分数的下标
		Content.resultNum = getIndex(getRandomCoin());
		System.out.println("resultNum=" + Content.resultNum);
		System.out.println("下注分数为="+Content.bets[Content.resultNum]);
		odds = Content.odds[Content.resultNum];
		System.out.println("跑出的分数是=" + odds);
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
	//通过跑出的分数，得出其下标
	private int getIndex(int num){		
		for(int i = 0;i<15;i++){
			if(Content.odds[i]==num){
				return i;
			}
		}
		return -1;
	}
	//获取随机分数，按不同的分数有不同的返回比率，3－4－5（60%）－－－－500，100（1%）
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

	// 清除压注区
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
		new Thread(this).start();// 启动线程
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
	
	

	// 获取随机赚数组
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
		// 退出界面，先设循环参数为false，等线程停了，再睡个300毫秒，再真正退出
		
		Content.coin = Content.coin + Content.resultWonNum;
		shareEdit.putInt("score", Content.coin); // 退出时，保存分数
		shareEdit.commit(); // 提交
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

	// 传进跑出的结果（随机的数值），返回x,y的坐标
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

	// 传进j,i的坐标点，返回bets数组的下标
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
