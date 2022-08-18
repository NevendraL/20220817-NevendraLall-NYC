package org.invescent.a20220817_nevendralall_nyc.model;

public class HighSchoolModel {
    private String school_name;
    private String phone_number;
    private String borough;

    public HighSchoolModel() {
    }

    public HighSchoolModel(String school_name, String phone_number, String borough) {
        this.school_name = school_name;
        this.phone_number = phone_number;
        this.borough = borough;
    }

    public String getSchool_name() {
        return school_name;
    }


    public String getSchool_number() {
        return phone_number;
    }


    public String getBorough() {
        return borough;
    }

}
