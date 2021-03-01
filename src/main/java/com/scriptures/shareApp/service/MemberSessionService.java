package com.scriptures.shareApp.service;

import javax.servlet.http.HttpServletRequest;

import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.entity.Member;


/**
 * @Title: UserService.java
 * @Package cc.uworks.library.service
 * @author liyuchang
 * @Description: 用户缓存，具体实现可以是session或memcached中
 * @date 2017年1月23日
 * @version V1.0
 */
public interface MemberSessionService {

  /**
   * 缓存用户信息，JSON格式
   *
   * @param request
   * @param user
   */
  void sessionMember(HttpServletRequest request, Member member);
  
  void sessionAdmin(HttpServletRequest request, Admin admin);

  /**
   * 获取缓存用户，不为空，重新设置缓存中用户的过期时间
   *
   * @param request
   * @return
   */
  Member getSessionMember(HttpServletRequest request);
  
  Admin getSessionAdmin(HttpServletRequest request);

  /**
   * 根据accessToken获取缓存用户
   * 
   * @param accessToken
   * @return
   */
  Member getSessionMember(String accessToken);

  Admin getSessionAdmin(String accessToken);
  /**
   * 删除缓存中得用户信息
   *
   * @param request
   */
  void deleteSessionMember(HttpServletRequest request);
  
  void deleteSessionAdmin(HttpServletRequest request);

  /**
   * 生成登录权限token
   *
   * @param request
   * @return
   */
  String generateAccessToken(HttpServletRequest request);

  /**
   * 返回登录用户，未登录返回null
   * 
   * @param request
   * @return
   */
  Member getLoginMember(HttpServletRequest request);
  
 Admin getLoginAdmin(HttpServletRequest request);
  
  /**
   * 获取真实ip
   * 
   * @param request
   * @return
   */
  public String getRemoteIP(HttpServletRequest request);

}
