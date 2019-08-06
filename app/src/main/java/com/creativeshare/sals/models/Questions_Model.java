package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Questions_Model  implements Serializable {
    private List<Faqs> faqs;

    public List<Faqs> getFaqs() {
        return faqs;
    }

    public class  Faqs
    {
        private int id;
            private String created_at;
            private String updated_at;
            private String question;
            private String answer;

        public int getId() {
            return id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
