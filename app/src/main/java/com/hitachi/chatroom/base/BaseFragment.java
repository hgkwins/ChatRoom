package com.hitachi.chatroom.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hitachi.chatroom.util.OnlinePermissionHelper;


public class BaseFragment extends Fragment{

	public Context mContext;
	public OnlinePermissionHelper onlinePermissionHelper;
	public int networkState;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		System.out.println("mContext.getCacheDir() = " + mContext.getCacheDir());
		mContext = getActivity();
		onlinePermissionHelper = new OnlinePermissionHelper(mContext);
		updateNetworkState();
	}
	
	public void updateNetworkState() {
		networkState = onlinePermissionHelper.checkNetworkStatus();
	}
}
