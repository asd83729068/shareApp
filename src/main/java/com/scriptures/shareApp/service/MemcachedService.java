package com.scriptures.shareApp.service;

/**
 * @Description: 缓存业务
 * @date 2017年1月23日
 * @version V1.0
 */
public interface MemcachedService {

  /**
   * 设置缓存
   * 
   * @param key：关键字
   * @param exprieTime：过期时间，单位秒（例如exprieTime=30，为30秒）
   * @param value：值
   */
  void set(String key, int exprieTime, Object value);

  /**
   * 删除缓存
   * 
   * @param key
   */
    void delete(String key);

  /**
   * 获取缓存值
   * 
   * @param key
   * @return
   */
  Object get(String key);

  /**
   * 获得缓存中的数据并重置其过期时间.
   * 
   * @param key
   * @param exprieTime
   */
    Object refresh(String key, int exprieTime);

}
