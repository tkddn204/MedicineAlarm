package com.ssangwoo.medicationalarm.controllers.viewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.util.AppDateFormat;
import com.ssangwoo.medicationalarm.views.dialogs.EditAlarmTimeDialog;

/**
 * Created by ssangwoo on 2017-12-04.
 */

public class AlarmItemViewHolder extends BindingViewHolder<Alarm>
    implements View.OnClickListener, View.OnLongClickListener {

    private RelativeLayout alarmItemContainer;
    private ImageView imageAlarmSwitch;
    private TextView textAlarmTime;
    private ImageView imageTakeMedicine;

    private Context context;

    private Alarm alarm;

    public AlarmItemViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context)
                .inflate(R.layout.layout_alarm_recycler_item,
                        parent, false));
        this.context = context;

        alarmItemContainer = itemView.findViewById(R.id.alarm_item_container);
        imageAlarmSwitch = itemView.findViewById(R.id.image_alarm_switch);
        textAlarmTime = itemView.findViewById(R.id.text_alarm_time);
        imageTakeMedicine = itemView.findViewById(R.id.image_alarm_take_medicine);
    }

    @Override
    public void bindViewHolder(Alarm alarm) {
        this.alarm = alarm;

        setAlarmEnable(alarm.isEnable(), true);
        textAlarmTime.setText(AppDateFormat.ALARM_TIME.format(alarm.getDate()));
        imageTakeMedicine.setImageResource(alarm.getTakeMedicineEnum().getImageRes());

        alarmItemContainer.setOnClickListener(this);
        alarmItemContainer.setOnLongClickListener(this);

        imageAlarmSwitch.setOnClickListener(this);
        imageTakeMedicine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.alarm_item_container) {
            new EditAlarmTimeDialog(context, alarm.getMedicine()).make(alarm);
        } else if(view.getId() == R.id.image_alarm_switch) {
            setAlarmEnable(!alarm.isEnable(), false);
        } else if(view.getId() == R.id.image_alarm_take_medicine) {
            nextTakeMedicineState();
        }
    }

    private void setAlarmEnable(boolean alarmEnable, boolean isInit) {
        alarm.setEnable(alarmEnable);
        AlarmController alarmController = new AlarmController(context);
        if(alarmEnable) {
            AlarmInfo alarmInfo = AppDatabaseDAO.selectTodayAlarmInfo(alarm.getId());
            if (!isInit && !alarm.getTakeMedicineEnum().equals(TakeMedicineEnum.TAKE)) {
                alarmController.startAlarm(alarm.getDate().getTime(), alarm.getId(), alarmInfo);
                Toast.makeText(context, String.format(
                        context.getString(R.string.start_alarm_toast),
                        AppDateFormat.ALARM_TIME.format(alarm.getDate())),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            alarmController.cancelAlarm(alarm.getId());
            if(!isInit) {
                Toast.makeText(context, String.format(
                        context.getString(R.string.stop_alarm_toast),
                        AppDateFormat.ALARM_TIME.format(
                                AppDatabaseDAO.selectAlarm(alarm.getId()).getDate())),
                        Toast.LENGTH_SHORT).show();
            }
        }
        int switchRes = alarm.isEnable() ?
                R.drawable.ic_alarm_on_black : R.drawable.ic_alarm_off_black;
        imageAlarmSwitch.setImageResource(switchRes);
    }

    private void nextTakeMedicineState() {
        int nextTakeMedicineOrdinal = (alarm.getTakeMedicineEnum().ordinal() + 1)
                % TakeMedicineEnum.values().length;
        TakeMedicineEnum currentTakeMedicineEnum
                = TakeMedicineEnum.values()[nextTakeMedicineOrdinal];
        AppDatabaseDAO.updateTakeMedicine(alarm, currentTakeMedicineEnum);
        imageTakeMedicine.setImageResource(currentTakeMedicineEnum.getImageRes());
    }

    @Override
    public boolean onLongClick(View view) {
        if(view.getId() == R.id.alarm_item_container) {
            new AlertDialog.Builder(context)
                    .setTitle(AppDateFormat.ALARM_TIME.format(alarm.getDate()) + " 삭제")
                    .setMessage(context.getText(R.string.alarm_delete_question))
                    .setPositiveButton(context.getText(R.string.alarm_delete_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppDatabaseDAO.deleteAlarm(alarm.getId());
                        }
                    })
                    .setNegativeButton(context.getText(R.string.alarm_delete_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // nothing
                        }
                    }).create().show();
            return true;
        }
        return false;
    }
}
