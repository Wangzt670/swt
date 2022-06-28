package com.cqu.swt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.Employee;
import com.cqu.swt.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工相关接口")
public class EmployeeController {

    //注入依赖
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录接口")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出接口")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    @ApiOperation(value = "员工新增接口")
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工,员工信息:{}",employee.toString());
        //使用md5加密设置初始密码12345，员工可自行修改
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获得当前登录用户的id
        Long empId=(Long)request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 分页查询员工信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "员工查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value="页码",required = true),
            @ApiImplicitParam(name = "pageSize",value="每页记录数",required = true),
            @ApiImplicitParam(name = "name",value="员工名称",required = false),

    })
    public R<Page> page(int page, int pageSize, String name){
    log.info("page={},pageSize={},name={}",page,pageSize,name);
    //分页构造器
    Page pageInfo=new Page(page,pageSize);
    //条件构造器
    LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
    //添加过滤条件
    queryWrapper.like(org.apache.commons.lang.StringUtils.isNotEmpty(name) ,Employee::getName,name);
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    //执行查询
    employeeService.page(pageInfo,queryWrapper);
    return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    @ApiOperation(value = "员工修改接口")
    public  R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        Long empID=(Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empID);
        employeeService.updateById(employee);
        return  R.success("修改成功");

    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
    log.info("id查询");
    Employee employee=employeeService.getById(id);
    if (employee!=null){
        return R.success(employee);
    }
    else
        return R.error("查询失败");
    }
}
