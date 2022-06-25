package com.cqu.swt.dto;


import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
