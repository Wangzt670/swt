package com.cqu.swt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 查询订单详细信息
     * @param id
     * @return
     */
    R<List<OrderDetail>> getByOrderId(Long id);
}
