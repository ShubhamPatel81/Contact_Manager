package com.example.Contact_manager_web.Forms;

import lombok.*;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactSearchForm {
    private String field;
    private String keyword;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String value) {
        this.keyword= value;
    }
}
