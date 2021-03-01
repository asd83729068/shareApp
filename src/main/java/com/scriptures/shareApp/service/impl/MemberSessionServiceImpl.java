package com.scriptures.shareApp.service.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import com.scriptures.shareApp.service.MemcachedService;

import com.scriptures.shareApp.contants.Const;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.util.ExceptionUtil;
import com.scriptures.shareApp.util.HttpUtil;
import com.scriptures.shareApp.util.StringUtil;
import com.scriptures.shareApp.service.MemberSessionService;

@Service("memberSessionService")
//@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MemberSessionServiceImpl implements MemberSessionService{

	@Autowired  
    private MemcachedService memcachedService; 


  /**
   * 缓存用户信息，JSON格式
   * 
   * @param request
   * @param user
   */
  @Override
  public void sessionMember(HttpServletRequest request, Member member) {
    String key = getUserSessionKey(request);
    // JSON格式
    String userJson = JSON.toJSONString(member);
    
   
    memcachedService.set(key, Const.SERVER_USER_EXP_KEY, userJson);
    // 注销其他登陆
    String accesskey = Const.SERVER_USER_KEY + member.getId();
    String _key = (String) memcachedService.get(accesskey);
    if (StringUtil.isNotBlank(_key) && !key.equals(_key)) {
    	memcachedService.delete(_key);
    }
    // 存入新accessKey
    memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
  }

  /**
   * 获取登录用户
   */
  @Override
  public Member getSessionMember(HttpServletRequest request) {
    String key = getUserSessionKey(request);
    String jsonStr = (String) memcachedService.get(key);
    
    if (StringUtil.isBlank(jsonStr)) {
      ExceptionUtil.throwException(Errors.SYSTEM_NOT_LOGIN);
    }
    Member member = JSON.parseObject(jsonStr, Member.class);
    if (member != null) {
      memcachedService.set(key, Const.SERVER_USER_EXP_KEY, jsonStr);
      String accesskey = Const.SERVER_USER_KEY + member.getId();
      memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
    }
    return member;
  }

  /**
   * 获取登录用户
   */
  @Override
  public Member getSessionMember(String accessToken) {
    String key = Const.SERVER_USER_KEY + accessToken;

    String jsonStr = (String) memcachedService.get(key);
    if (StringUtil.isBlank(jsonStr)) {
      return null;
    }
    return JSON.parseObject(jsonStr, Member.class);
  }

  /**
   * 清除登录用户
   */
  @Override
  public void deleteSessionMember(HttpServletRequest request) {
    String key = getUserSessionKey(request);
    String jsonStr = (String) memcachedService.get(key);
    if (StringUtil.isNotBlank(jsonStr)) {
      String accesskey = Const.SERVER_USER_KEY + (JSON.parseObject(jsonStr, Member.class)).getId();
      memcachedService.delete(accesskey);
      memcachedService.delete(key);
    }
  }

  /**
   * 获取用户缓存key
   * 
   * @param request
   * @return
   */
  private String getUserSessionKey(HttpServletRequest request) {
    String key = Const.SERVER_USER_KEY + getSessionKey(request);
    return key;
  }

  /**
   * <pre>
   * 获取缓存key
   * 同时使用，使用token保存登录信息，优先使用token，如果获取失败则取session
   * </pre>
   *
   * @param request
   */
  private String getSessionKey(HttpServletRequest request) {
    String sessionId = "";
    Object sessionIdAttribute = request.getAttribute(Const.ACCESS_TOKEN_HEADER_NAME);
    
    if (StringUtil.isNotBlank(sessionIdAttribute)) {
      sessionId = sessionIdAttribute.toString();
    }
    if (StringUtil.isBlank(sessionId)) {
      sessionId = request.getHeader(Const.ACCESS_TOKEN_HEADER_NAME);
    }
    if (StringUtil.isBlank(sessionId)) {
      sessionId = request.getParameter("token");
    }
    if (StringUtil.isBlank(sessionId)) {
      sessionId = request.getSession().getId();
    }
    return sessionId;
  }

  /**
   * 生成token sessionid+uuid
   */
  @Override
  public String generateAccessToken(HttpServletRequest request) {
    return request.getSession().getId() + StringUtil.uuidNotLine();
  }

  /**
   * 返回登录用户，未登录返回null
   * 
   * @param request
   * @return
   */
  @Override
  public Member getLoginMember(HttpServletRequest request) {
    String key = getUserSessionKey(request);
    String jsonStr = (String) memcachedService.get(key);
    if (StringUtil.isBlank(jsonStr)) {
      return null;
    }
    Member member = JSON.parseObject(jsonStr, Member.class);
    if (member != null) {
      memcachedService.set(key, Const.SERVER_USER_EXP_KEY, jsonStr);
      String accesskey = Const.SERVER_USER_KEY + member.getId();
      memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
    }
    return member;
  }

  /**
   * 获取真实ip
   * 
   * @param request
   * @return
   */
  public String getRemoteIP(HttpServletRequest request) {
	  	String ip = new HttpUtil().getClientIp(request);
	  	return ip;
  }

@Override
public Admin getSessionAdmin(HttpServletRequest request) {
	String key = getUserSessionKey(request);
    String jsonStr = (String) memcachedService.get(key);
    
    if (StringUtil.isBlank(jsonStr)) {
      ExceptionUtil.throwException(Errors.SYSTEM_NOT_LOGIN);
    }
    Admin admin = JSON.parseObject(jsonStr, Admin.class);
    if (admin != null) {
      memcachedService.set(key, Const.SERVER_USER_EXP_KEY, jsonStr);
      String accesskey = Const.SERVER_USER_KEY + admin.getId();
      memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
    }
    return admin;
}

@Override
public Admin getSessionAdmin(String accessToken) {
	String key = Const.SERVER_USER_KEY + accessToken;

    String jsonStr = (String) memcachedService.get(key);
    if (StringUtil.isBlank(jsonStr)) {
      return null;
    }
    return JSON.parseObject(jsonStr, Admin.class);
}

@Override
public void deleteSessionAdmin(HttpServletRequest request) {
	 String key = getUserSessionKey(request);
	    String jsonStr = (String) memcachedService.get(key);
	    if (StringUtil.isNotBlank(jsonStr)) {
	      String accesskey = Const.SERVER_USER_KEY + (JSON.parseObject(jsonStr, Admin.class)).getId();
	      memcachedService.delete(accesskey);
	      memcachedService.delete(key);
	    }
	
}

@Override
public Admin getLoginAdmin(HttpServletRequest request) {
	String key = getUserSessionKey(request);
    String jsonStr = (String) memcachedService.get(key);
    if (StringUtil.isBlank(jsonStr)) {
      return null;
    }
    Admin admin = JSON.parseObject(jsonStr, Admin.class);
    if (admin != null) {
      memcachedService.set(key, Const.SERVER_USER_EXP_KEY, jsonStr);
      String accesskey = Const.SERVER_USER_KEY + admin.getId();
      memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
    }
    return admin;
}

@Override
public void sessionAdmin(HttpServletRequest request, Admin admin) {
	 String key = getUserSessionKey(request);
	    // JSON格式
	    String userJson = JSON.toJSONString(admin);
	    
	   
	    memcachedService.set(key, Const.SERVER_USER_EXP_KEY, userJson);
	    // 注销其他登陆
	    String accesskey = Const.SERVER_USER_KEY + admin.getId();
	    String _key = (String) memcachedService.get(accesskey);
	    if (StringUtil.isNotBlank(_key) && !key.equals(_key)) {
	    	memcachedService.delete(_key);
	    }
	    // 存入新accessKey
	    memcachedService.set(accesskey, Const.SERVER_USER_EXP_KEY, key);
	
	}

}
