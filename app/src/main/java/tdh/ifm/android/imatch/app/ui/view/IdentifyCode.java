package tdh.ifm.android.imatch.app.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import tdh.ifm.android.imatch.app.R;


@SuppressLint("NewApi")
public class IdentifyCode extends CountDownTimer {
	private TextView btnIdentifyCode;
	private Activity c;

	public IdentifyCode(long millisInFuture, long countDownInterval, TextView btnIdentifyCode, Activity c) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		this.c = c;
		this.btnIdentifyCode = btnIdentifyCode;
	}

	@Override
	public void onFinish() {
		btnIdentifyCode.setClickable(true);
		btnIdentifyCode.setText(c.getResources().getString(R.string.get_identify_code));
	}

	@Override
	public void onTick(long millisUntilFinished) {
		btnIdentifyCode.setClickable(false);
		btnIdentifyCode.setText(millisUntilFinished / 1000 + c.getResources().getString(R.string.txt_again_code));
	}

}