package com.hitachi.chatroom.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OnlinePermissionHelper {

	public static final int MODE_NETWORK_WIFI = 1;
	public static final int MODE_NETWORK_MOBLIE = 2;
	public static final int MODE_NETWORK_NO = 0;

	public Context mContext;

	public OnlinePermissionHelper(Context context) {
		super();

		mContext = context;
	}

	public int checkNetworkStatus() {
		// 0-No Internet, 1-Wifi, 2-Mobile
		int networkStatus = MODE_NETWORK_NO;
		ConnectivityManager connect = null;
		connect = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connect != null) {
			if (connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnectedOrConnecting()) {
				networkStatus = MODE_NETWORK_WIFI;
			} else {
				NetworkInfo result = connect
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (result != null && result.isConnectedOrConnecting()) {
					networkStatus = MODE_NETWORK_MOBLIE;
				}
			}
		}

		return networkStatus;
	}
}
