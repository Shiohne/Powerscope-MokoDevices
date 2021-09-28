package com.moko.support.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.entity.OrderCHAR;
import com.moko.support.entity.ParamsKeyEnum;

import java.util.Calendar;


public class ParamsWriteTask extends OrderTask {
    public static final int HEADER_WRITE_SEND = 0xB2;
    public static final int HEADER_WRITE_GET = 0xB3;
    public byte[] data;

    public ParamsWriteTask() {
        super(OrderCHAR.CHAR_PARAMS_WRITE, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

    public void setAdvInterval(int advInterval) {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_ADV_INTERVAL.getParamsKey(),
                (byte) 0x01,
                (byte) advInterval
        };
    }

    public void setAdvName(String advName) {
        int length = 0;
        byte[] advNameBytes = advName.getBytes();
        int advNameLength = advNameBytes.length;
        length = 3 + advNameLength;
        data = new byte[length];
        data[0] = (byte) HEADER_WRITE_SEND;
        data[1] = (byte) ParamsKeyEnum.SET_ADV_NAME.getParamsKey();
        data[2] = (byte) advNameLength;
        for (int i = 0; i < advNameLength; i++) {
            data[3 + i] = advNameBytes[i];
        }
    }

    public void setCountdown(int countdown) {
        byte[] countdownBytes = MokoUtils.toByteArray(countdown, 4);
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_COUNTDOWN.getParamsKey(),
                (byte) 0x04,
                countdownBytes[0],
                countdownBytes[1],
                countdownBytes[2],
                countdownBytes[3],
        };
    }

    public void setSavedParams(int savedInterval, int savedPercent) {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_ENERGY_SAVED_PARAMS.getParamsKey(),
                (byte) 0x02,
                (byte) savedInterval,
                (byte) savedPercent
        };
    }

    public void setOverloadTopValue(int topValue) {
        byte[] topValueBytes = MokoUtils.toByteArray(topValue, 2);
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_OVERLOAD_TOP_VALUE.getParamsKey(),
                (byte) 0x02,
                topValueBytes[0],
                topValueBytes[1]
        };
    }

    public void setPowerState(int powerState) {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_POWER_STATE.getParamsKey(),
                (byte) 0x01,
                (byte) powerState
        };
    }

    public void setResetEnergyTotal() {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_RESET_ENERGY_TOTAL.getParamsKey(),
                (byte) 0x00,
        };
    }

    public void setReset() {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_RESET.getParamsKey(),
                (byte) 0x00,
        };
    }

    public void setSwitchState(int switchState) {
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_SWITCH_STATE.getParamsKey(),
                (byte) 0x01,
                (byte) switchState
        };
    }

    public void setSystemTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        byte[] yearBytes = MokoUtils.toByteArray(year, 2);
        data = new byte[]{
                (byte) HEADER_WRITE_SEND,
                (byte) ParamsKeyEnum.SET_SYSTEM_TIME.getParamsKey(),
                (byte) 0x07,
                yearBytes[0],
                yearBytes[1],
                (byte) month,
                (byte) date,
                (byte) hour,
                (byte) minute,
                (byte) second
        };
    }


    @Override
    public boolean parseValue(byte[] value) {
        final int header = value[0] & 0xFF;
        final int cmd = value[1] & 0xFF;
//        final int length = value[2] & 0xFF;
        if (header != HEADER_WRITE_GET)
            return false;
        ParamsKeyEnum configKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
        if (configKeyEnum == null)
            return false;
        return true;
    }
}
