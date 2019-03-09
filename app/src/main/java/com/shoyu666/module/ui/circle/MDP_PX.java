package com.shoyu666.module.ui.circle;
/**
 * @author xining
 * @date 2019/3/1
 */
public class MDP_PX {
	public static MAPP mMAPP;
	/**
	 *  将dp转换成px 
	 *  
	 */
	public static int dip2px(float dipValue) {
		float scale = mMAPP.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}