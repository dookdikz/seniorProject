package com.example.tanawat.eleccontrol.manager;

import android.content.Context;

import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.view.ButtonListItem;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonItemManager {
    private Context mContext;
    private ButtonItemCollectionCms cms;
    public  ButtonItemManager(){
        mContext = Contextor.getInstance().getContext();

    }

    public ButtonItemCollectionCms getCms() {
        return cms;
    }

    public void setCms(ButtonItemCollectionCms cms) {
        this.cms = cms;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getCount(){
        return cms.getData().size();
    }
}
