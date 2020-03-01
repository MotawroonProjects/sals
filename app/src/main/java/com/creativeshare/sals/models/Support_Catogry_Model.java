package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Support_Catogry_Model implements Serializable {
    private List<Support_Catogry_Model.support_cats> support_cats;

    public List<Support_Catogry_Model.support_cats> getSupport_cats() {
        return support_cats;
    }

    public class support_cats implements Serializable {
        private int id;
        private int parent_id;
        private String image;

        private String title;
        private String desc;

        public int getId() {
            return id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }
    }
}
