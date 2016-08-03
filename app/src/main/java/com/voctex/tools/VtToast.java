package com.voctex.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Voctex on 2015-06-05
 */
public class VtToast {

	private static Toast toast = null;
	/**
	 * 测试用的话，就改为true给人用的话就为false
	 */
	private final static boolean isShow = false;

	/**
	 * 显示toast，默认为不显示，只给调试人员看，根据isShow来控制
	 * 
	 * @param mContext
	 * @param charSequence
	 */
	public static void ds(Context mContext, CharSequence charSequence) {
		if (isShow) {
			makeText(mContext, charSequence, Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 根据传进来的flag来判断是否显示Toast内容
	 * 
	 * @param mContext
	 * @param charSequence
	 */
	public static void s(Context mContext, CharSequence charSequence) {
		makeText(mContext, charSequence, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示toast，默认为不显示，只给调试人员看，根据isShow来控制
	 * 
	 * @param mContext
	 * @param resource
	 *            字符串资源ID
	 */
	public static void ds(Context mContext, int resource) {
		if (isShow) {
			makeText(mContext, resource, Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 根据传进来的flag来判断是否显示Toast内容
	 * 
	 * @param mContext
	 * @param resource
	 *            字符串资源ID
	 */
	public static void s(Context mContext, int resource) {
		makeText(mContext, resource, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示toast，默认为不显示，只给调试人员看，根据isShow来控制
	 * 
	 * @param mContext
	 * @param charSequence
	 */
	public static void dl(Context mContext, CharSequence charSequence) {
		if (isShow) {
			makeText(mContext, charSequence, Toast.LENGTH_LONG);
		}
	}

	/**
	 * 显示toast，默认为不显示，只给调试人员看，根据isShow来控制
	 * 
	 * @param mContext
	 * @param resource
	 *            字符串资源ID
	 */
	public static void dl(Context mContext, int resource) {
		if (isShow) {
			makeText(mContext, resource, Toast.LENGTH_LONG);
		}
	}

	/**
	 * 根据传进来的flag来判断是否显示Toast内容
	 * 
	 * @param mContext
	 * @param charSequence
	 */
	public static void l(Context mContext, CharSequence charSequence) {
		makeText(mContext, charSequence, Toast.LENGTH_LONG);
	}

	/**
	 * 根据传进来的flag来判断是否显示Toast内容
	 * 
	 * @param mContext
	 * @param resource
	 *            字符串资源ID
	 */
	public static void l(Context mContext, int resource) {
		makeText(mContext, resource, Toast.LENGTH_LONG);
	}

	/**
	 * 本人根据源代码研究出来的可以马上显示下一个Toast内容的方法
	 * 
	 * @param mContext
	 * @param charSequence
	 * @param time
	 */
	private static void makeText(Context mContext, CharSequence charSequence,
			int time) {
		// TODO Auto-generated method stub
		if (toast == null) {
			toast = Toast.makeText(mContext, charSequence, time);
			toast.show();
		} else {
			toast.cancel();
			toast = Toast.makeText(mContext, charSequence, time);
			toast.show();
		}
	}

	/**
	 * 本人根据源代码研究出来的可以马上显示下一个Toast内容的方法
	 * 
	 * @param mContext
	 * @param resource
	 * @param time
	 */
	private static void makeText(Context mContext, int resource, int time) {
		// TODO Auto-generated method stub
		if (toast == null) {
			toast = Toast.makeText(mContext, resource, time);
			toast.show();
		} else {
			toast.cancel();
			toast = Toast.makeText(mContext, resource, time);
			toast.show();
		}
	}
}
