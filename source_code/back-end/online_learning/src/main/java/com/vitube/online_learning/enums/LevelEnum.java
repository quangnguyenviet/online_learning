package com.vitube.online_learning.enums;

public enum LevelEnum {
    BEGINNER("Mới bắt đầu"),
    INTERMEDIATE("Trung cấp"),
    ADVANCED("Nâng cao"),
    ALL_LEVELS("Tất cả cấp độ");
    private final String description;

    LevelEnum(String description) {
        this.description = description;
    }
}
