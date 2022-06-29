package com.cqu.swt.service.impl;

        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import com.cqu.swt.entity.Employee;
        import com.cqu.swt.mapper.EmployeeMapper;
        import com.cqu.swt.service.EmployeeService;
        import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
