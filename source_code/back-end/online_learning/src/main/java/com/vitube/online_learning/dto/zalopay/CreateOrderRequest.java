package com.vitube.online_learning.dto.zalopay;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequest {
    @JsonProperty("app_id")
    private int appId;
    @JsonProperty("app_user")
    private String appUser;
    @JsonProperty("app_time")
    private long appTime;
    private long amount;
    @JsonProperty("app_trans_id")
    private String appTransId;
    @JsonProperty("embed_data")
    private String embedData;
    private String item;
    private String mac;
    @JsonProperty("bank_code")
    private String bankCode;
    private String description;

    private String phone;
    private String email;
    private String address;
    @JsonProperty("callback_url")
    private String callbackUrl;


}
