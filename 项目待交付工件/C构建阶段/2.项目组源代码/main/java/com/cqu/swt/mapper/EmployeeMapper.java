package com.cqu.swt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqu.swt.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//基于MyBatis-Plus，继承父类即可
public interface EmployeeMapper extends BaseMapper<Employee>{
}
