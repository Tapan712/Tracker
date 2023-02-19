package com.example.tracker.dto;

import androidx.annotation.NonNull;

public class ChannelDto {
    private int iconId;
    private String chName;

    public ChannelDto(int iconId, String chName) {
        this.iconId = iconId;
        this.chName = chName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    @NonNull
    @Override
    public String toString() {
        return chName;
    }
}
