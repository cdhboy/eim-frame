package com.eim.domain.common;

public class FileEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private int id;
    private int key;
    private String type;
    private String main;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
