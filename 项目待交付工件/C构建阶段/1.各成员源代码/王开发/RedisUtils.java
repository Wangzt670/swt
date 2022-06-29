package com.cqu.swt.common;

import com.cqu.swt.dto.DishDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 将图片名称存储到FOOD_PIC_DB_RESOURCES
     * @param pic
     */
    public void save2Db(String pic){
        redisTemplate.opsForSet().add(RedisConstant.FOOD_PIC_DB_RESOURCES,pic);
    }

    /**
     * 将图片名称存储到FOOD_PIC_RESOURCES
     * @param pic
     */
    public void save2Qiniu(String pic){
        redisTemplate.opsForSet().add(RedisConstant.FOOD_PIC_RESOURCES,pic);
    }

    public void removePicFromRedis(String pic){
        redisTemplate.opsForSet().remove(RedisConstant.FOOD_PIC_DB_RESOURCES,pic);
    }

    public void saveValidateCode2Redis(String phone,String code){
        redisTemplate.opsForValue().set(phone+RedisConstant.SENDTYPE_LOGIN,code.toString(),600, TimeUnit.SECONDS);
    }
    public String getValidateCodeFromRedis(String phone){
        String s = redisTemplate.opsForValue().get(phone + RedisConstant.SENDTYPE_LOGIN).toString();
        return s;
    }
    public void saveUserInfo2Redis(String phone,String userInfo){
        redisTemplate.opsForValue().set(phone,userInfo,30,TimeUnit.MINUTES);
    }
    public boolean  saveEmployee2Redis(String value){
        Long ifAdded = redisTemplate.opsForSet().add("employee", value);
        return ifAdded == 1L;

    }
    public boolean deleteEmployeeFromRedis(String value){
        Long aLong = redisTemplate.opsForSet().remove("employee", value);
        return aLong == 1L;

    }
    public boolean isEmployeeInRedis(String value){
        Boolean employee = redisTemplate.opsForSet().isMember("employee", value);
        return employee;
    }


    public boolean saveUser2Redis(String value){
        Long ifAdded = redisTemplate.opsForSet().add("user", value);
        return ifAdded == 1L;
    }
    public boolean deleteUserFromRedis(String value){
        Long aLong = redisTemplate.opsForSet().remove("user", value);
        return aLong == 1L;

    }
    public boolean isUserInRedis(String value){
        Boolean employee = redisTemplate.opsForSet().isMember("user", value);
        return employee;
    }
    public String getUserFromRedis(String phone){
        String s = redisTemplate.opsForValue().get(phone).toString();
        return s;
    }
    public void deleteLoginValidateCodeFromRedis(String phone){
        redisTemplate.delete(phone+RedisConstant.SENDTYPE_LOGIN);
    }
    public void saveDish2Redis(String key,List<DishDto> dishDtoList){
        redisTemplate.opsForValue().set(key,dishDtoList,60,TimeUnit.MINUTES);

    }


    public List<DishDto> getDishFromRedis(String key) {
        return (List<DishDto>) redisTemplate.opsForValue().get(key);
    }

    public void deleteDishFromRedis(String key) {
        redisTemplate.delete(key);
    }
}
