package com.moko.bluetoothplug.dialog;

import android.view.View;
import android.widget.TextView;

import com.moko.bluetoothplug.R;
import com.moko.bluetoothplug.view.WheelView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Date 2020/4/25
 * @Author wenzheng.liu
 * @Description
 * @ClassPath com.moko.bluetoothplug.dialog.TimerDialog
 */
public class TimerDialog extends BaseDialog {
    public static final String TAG = TimerDialog.class.getSimpleName();

    @BindView(R.id.tv_switch_state)
    TextView tvSwitchState;
    @BindView(R.id.wv_hour)
    WheelView wvHour;
    @BindView(R.id.wv_minute)
    WheelView wvMinute;
    private boolean on_off;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_timer;
    }

    @Override
    public void bindView(View v) {
        ButterKnife.bind(this, v);
        tvSwitchState.setText(on_off ? R.string.countdown_timer_off : R.string.countdown_timer_on);
        initWheelView();
    }
    private void initWheelView() {
        ArrayList<String> hour = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i > 1) {
                hour.add(i + " hours");
            } else {
                hour.add(i + " hour");
            }
        }
        wvHour.setData(hour);
        wvHour.setDefault(0);
        ArrayList<String> minute = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i > 1) {
                minute.add(i + " mins");
            } else {
                minute.add(i + " min");

            }
        }
        wvMinute.setData(minute);
        wvMinute.setDefault(0);
    }

    public int getWvHour() {
        return wvHour.getSelected();
    }

    public int getWvMinute() {
        return wvMinute.getSelected();
    }

    @OnClick({R.id.tv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                dismiss();
                break;
            case R.id.tv_confirm:
                listener.onConfirmClick(this);
                break;
        }
    }

    private TimerListener listener;

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public interface TimerListener {
        void onConfirmClick(TimerDialog dialog);
    }

    public void setOnoff(boolean on_off) {
        this.on_off = on_off;
    }



    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public float getDimAmount() {
        return 0.7f;
    }

}
