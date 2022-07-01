package com.cqu.swt.dto;


import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("订单Dto")
public class OrdersDto extends Orders {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("用户地址")
    private String address;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单详情")
    private List<OrderDetail> orderDetails;
	
}
