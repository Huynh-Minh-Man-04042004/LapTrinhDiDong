package com.example.baitap04;

public class MonHoc {
    private String name;
    private String desc;
    private int pic; // ID ảnh

    // Constructor
    public MonHoc(String name, String desc, int pic) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    // Getter & Setter
    public String getName() { return name; }

    public String getDesc() { return desc; }

    public int getPic() { return pic; }
}
