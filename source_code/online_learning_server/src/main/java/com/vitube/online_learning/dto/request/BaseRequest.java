package com.vitube.online_learning.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Lớp cơ sở cho các yêu cầu API.
 * Chứa thông tin chung như thẻ (tag) để phân loại hoặc đánh dấu yêu cầu.
 */
@Getter
@Setter
public class BaseRequest {
    /**
     * Thẻ dùng để phân loại hoặc đánh dấu yêu cầu.
     */
    private String tag;
}