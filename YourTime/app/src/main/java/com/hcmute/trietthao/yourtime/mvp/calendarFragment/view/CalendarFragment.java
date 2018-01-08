package com.hcmute.trietthao.yourtime.mvp.calendarFragment.view;

import android.util.Log;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.base.BaseFragment;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.TasksFragment;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends BaseFragment  {

    private static ArrayList<CongViecModel> mListWork;
    List<WeekViewEvent> events;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        mListWork = TasksFragment.mListCV;
        Calendar temp = Calendar.getInstance();

        Log.e("calenderFragment::","Vao onMonthChange------");
        events = new ArrayList<WeekViewEvent>();

        try{
            for (int i=0; i<mListWork.size();i++){
                CongViecModel congViecModel = mListWork.get(i);

                WeekViewEvent event = new WeekViewEvent();
                if(mListWork.get(i).getThoiGianBatDau()!=null) {

                    try {
                        if (newYear==temp.get(Calendar.YEAR)-1) {
                            event = new WeekViewEvent(mListWork.get(i).getIdCongViec(), congViecModel.getTenCongViec(),
                                    DateUtils.converStringToCalendar(congViecModel.getThoiGianBatDau()),
                                    DateUtils.converStringToCalendar(congViecModel.getThoiGianKetThuc()));
                            if (mListWork.get(i).getCoUuTien() == 1)
                                event.setColor(getResources().getColor(R.color.colorRed));
                            else{
                                if(mListWork.get(i).getIdCongViec()%5 == 0)
                                    event.setColor(getResources().getColor(R.color.event_color_01));
                                if(mListWork.get(i).getIdCongViec()%5 == 1)
                                    event.setColor(getResources().getColor(R.color.colorBlue));
                                if(mListWork.get(i).getIdCongViec()%5 == 2)
                                    event.setColor(getResources().getColor(R.color.event_color_04));
                                if(mListWork.get(i).getIdCongViec()%5 == 3)
                                    event.setColor(getResources().getColor(R.color.colorYellow100));
                                if(mListWork.get(i).getIdCongViec()%5 == 4)
                                    event.setColor(getResources().getColor(R.color.colorGray600));

                            }
                            events.add(event);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return events;
    }

}
