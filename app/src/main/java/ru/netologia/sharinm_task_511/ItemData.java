package ru.netologia.sharinm_task_511;

import android.graphics.drawable.Drawable;

public class ItemData {

    private String title;
    private String subtitle;
    private Drawable image;

    public ItemData(String title, String subtitle, Drawable image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Drawable getImage() {
        return image;
    }
}
