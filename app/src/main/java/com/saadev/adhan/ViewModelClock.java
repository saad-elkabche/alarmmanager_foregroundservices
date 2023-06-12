package com.saadev.adhan;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelClock extends ViewModel {
    private MutableLiveData<List<ClockModel>> listClock=new MutableLiveData<>();
    private List<ClockModel> list=new ArrayList<>();
    private SharedPreferences msharedFile;


    public MutableLiveData<List<ClockModel>> getListClock() {
        return listClock;
    }
    public void setListClock(List<ClockModel> list){
        listClock.setValue(list);
    }
    private void fetchClocks(){
        setListClock(list);
        String key="clock";
        int count=1;
        boolean existe=true;
        if(msharedFile!=null) {
            list.clear();
            do {
                if (existe = msharedFile.contains(key + count)) {
                    String[] clock = msharedFile.getString(key + count, "").split("/");
                    if (clock.length == 3) {
                        list.add(new ClockModel(clock[0], clock[1], clock[2]));
                    }
                }

            } while (existe);
        }
    }

    public void setMsharedFile(SharedPreferences msharedFile) {
        this.msharedFile = msharedFile;
    }
}
