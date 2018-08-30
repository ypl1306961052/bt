package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 */

public class ItemEvent implements Serializable{
    int itemHeight;
    int itemCount;
    public void setItemHeight(int itemHeight){

        this.itemHeight  =itemHeight;
    }

    public void setCount(int itemCount){
        this.itemCount = itemCount;
    }

    public int getItemHeight(){

        return itemHeight;
    }

    public int getCount(){
        return itemCount;
    }
}
