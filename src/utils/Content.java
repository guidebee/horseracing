package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��Ÿ�������
 * 
 * @author Administrator
 * 
 */
public class Content {

	//����ѹע������ͼƬ������x,y,width,height
	//(0,1,2,3,4,5,6,7,8,9,x,-)
	//rect(x,y,width,height)
	public static int[][] num = { { 67, 0, 54, 66 }, { 110, 134, 42, 66 },
			{ 122, 0, 54, 66 }, { 177, 0, 54, 66 }, { 0, 67, 54, 66 },
			{ 55, 67, 54, 66 }, { 110, 67, 54, 66 }, { 165, 67, 54, 66 },
			{ 0, 134, 54, 66 }, { 55, 134, 54, 66 }, { 0, 0, 66, 66 },
			{ 153, 134, 42, 17 } };
	
	public static int[][] horseRightNum = {{166,0,336,108},{0,112,166,218},{332,116,498,208}};
	public static int[][] horseRightDownNum = {{0, 329, 142, 438},{146, 350, 287, 452},{122, 450, 242, 540},{ 285, 425, 406, 537 }};
	public static int[][] horseDownNum = {{0, 553, 112, 608},{0, 612, 112, 688},{0, 690, 105, 778},{ 106, 766, 191, 846 }};
	public static int[][] horseLeftDownNum = {{161, 241, 313, 350},{ 0, 439, 119, 550 },{308, 319, 449, 424}};
	public static int[][] horseLeftNum = {{0,0,166,108},{336,0,499,108},{167,110,332,241}};

	
	
	//public static int[] times = {3,4,5,8,10,20,30,60,80,100,125,175,250,500,1000};
	
	/**
	 * 15�����ʷ��������л�ʱ�������������
	 */
	public static int[] odds = {3,4,5,8,10,20,30,60,80,100,125,175,250,500,1000};
	//ÿ������ֵ������ѹ�ķ�ֵ
	public static int[] bets = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//��ʼ��ҷ���
	public static int coin = 500;
	//ÿ�������н����±�
	public static int resultNum = 0;
	//�����н���ķ���ֵ
	public static int resultWonNum = 0;
	
	//��������������
	public static int[] result = {0,0,0};
	
	public static int MoveX = 0;
	public static int MoveY = 0;
	
	
	
}
