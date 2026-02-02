package com.vitube.online_learning.dto.zalopay;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderResponse {

    private int return_code;
    private String return_message;
    private int sub_return_code ;
    private String sub_return_message ;
    private String zp_trans_token;
    private String order_url;
    private String order_token;
    private String qr_code;

}
