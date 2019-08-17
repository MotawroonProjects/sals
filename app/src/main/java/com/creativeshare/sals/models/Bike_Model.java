package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Bike_Model  implements Serializable {
    private List<Sizes> sizes;

    public List<Sizes> getSizes() {
        return sizes;
    }

    public class Sizes implements Serializable
        {
            private int id;
                private int parent_id;
                private String image;
                private String deleted_at;
                private String created_at;
                private String updated_at;
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

            public String getDeleted_at() {
                return deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public String getTitle() {
                return title;
            }

            public String getDesc() {
                return desc;
            }
        }
}
