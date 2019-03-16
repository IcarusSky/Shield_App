package com.crec.shield.entity.project.quality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diaokaibin@gmail.com on 2017/8/2.
 */

public class QualityUpDataBean {

    private String up1;
    private String up2;

    private List<PhotoBean> photo = new ArrayList<>();

    public String getUp1() {
        return up1;
    }

    public void setUp1(String up1) {
        this.up1 = up1;
    }

    public String getUp2() {
        return up2;
    }

    public void setUp2(String up2) {
        this.up2 = up2;
    }

    public List<PhotoBean> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoBean> photo) {
        this.photo = photo;
    }


    public static class PhotoBean {
        public PhotoBean(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
