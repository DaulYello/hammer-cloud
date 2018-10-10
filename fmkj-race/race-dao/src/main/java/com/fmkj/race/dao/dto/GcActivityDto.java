package com.fmkj.race.dao.dto;

import com.fmkj.race.dao.domain.GcActivity;

public class GcActivityDto extends GcActivity {

    private String type;

    private String imageurl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }





}
