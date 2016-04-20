package com.hitachi.chatroom.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.hitachi.chatroom.R;
import com.hitachi.chatroom.fragment.ContactFragment;
import com.hitachi.chatroom.fragment.HomeFragment;
import com.hitachic.customer.bottom.view.NMGToolBar;

public class MainFrameActivity extends FragmentActivity implements NMGToolBar.OnNMGToolBarListener {

	HomeFragment homeFragment;
	ContactFragment contactFragment;
	private NMGToolBar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment);
		toolbar = (NMGToolBar) this.findViewById(R.id.toolbar);
		toolbar.setOnNMGToolBarListener(this);
		toolbar.changeToolBarIndex(0);  //默認第一個頁面
	}

	@Override
	public void onItemTouched(int position) {
		showDetails(position, null);
	}
	
	
	private void showDetails(int index , String changeFlag) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		homeFragment = (HomeFragment) getSupportFragmentManager()
				.findFragmentByTag("homeFragment");
		contactFragment = (ContactFragment) getSupportFragmentManager()
				.findFragmentByTag("contactFragment");
		if (homeFragment != null) {
			ft.hide(homeFragment);
		}
		if (contactFragment != null) {
			ft.hide(contactFragment);
		}
		switch (index) {
		case 0:
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
				ft.add(R.id.details, homeFragment, "homeFragment");
			} else {
				ft.show(homeFragment);
			}
			break;
		case 1:
			if (contactFragment == null) {
				contactFragment = new ContactFragment();
				ft.add(R.id.details, contactFragment, "contactFragment");
			} else {
				ft.show(contactFragment);
			}
			break;
		case 2:
			break;
		case 3:
			break;
		}
		// Execute a transaction, replacing any existing
		// fragment with this one inside the frame.

		// ft.replace(R.id.details, details);
		// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		// ft.addToBackStack(null);//这行代码可以返回之前的操作（横屏的情况下，即两边都显示的情况下）
		ft.commit();
	}
}
