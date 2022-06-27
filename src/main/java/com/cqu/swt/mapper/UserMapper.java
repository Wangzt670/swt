package com.cqu.swt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wkf
 * @version 1.0
 * @date 2022/6/24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
