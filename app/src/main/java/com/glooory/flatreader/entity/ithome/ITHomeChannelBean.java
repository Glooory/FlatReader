package com.glooory.flatreader.entity.ithome;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by Glooory on 2016/10/12 0012 12:37.
 */

public class ITHomeChannelBean {

    @ElementList(inline = true, name = "items")
    ArrayList<ITHomeItemBean> items;

    public ArrayList<ITHomeItemBean> getItems() {
        return items;
    }

    public void setItems(ArrayList<ITHomeItemBean> items) {
        this.items = items;
    }
}
