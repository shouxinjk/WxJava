package me.chanjar.weixin.cp.tp.service;

import lombok.NonNull;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.chatdata.*;

/**
 * 企业微信文档相关接口.
 * <a href="https://developer.work.weixin.qq.com/document/path/97392">文档</a>
 *
 * @author Hugo
 */
public interface WxCpTpChatDataService {

  /**
   * 获取授权存档的成员列表
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/chatdata/get_auth_user_list?access_token=ACCESS_TOKEN
   *
   * @param request 获取授权存档的成员列表查询对象
   * @return WxCpChatDataAuthUserList：获取授权存档的成员列表返回对象
   * @throws WxErrorException the wx error exception
   */
  WxCpChatDataAuthUserList authUserList(@NonNull WxCpChatDataAuthUserRequest request, String corpId) throws WxErrorException;

  /**
   * 设置公钥
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/chatdata/set_public_key?access_token=ACCESS_TOKEN
   *
   * @param request 设置公钥请求对象
   * @throws WxErrorException the wx error exception
   */
  WxCpBaseResp setPublicKey(@NonNull WxCpChatDataSetPublicKey request, String corpId) throws WxErrorException;

  /**
   * 获取会话记录
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/chatdata/sync_msg?access_token=ACCESS_TOKEN
   *
   * @param request 获取会话记录请求对象
   * @return WxCpChatDataMsgList：获取会话记录返回对象
   * @throws WxErrorException the wx error exception
   */
  WxCpChatDataMsgList syncMsg(@NonNull WxCpChatDataSyncMsg request, String corpId) throws WxErrorException;

  /**
   * 获取内部群信息
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/chatdata/groupchat/get?access_token=ACCESS_TOKEN
   *
   * @param request 获取内部群信息请求对象
   * @return WxCpChatDataGroupChatInfo：获取内部群信息返回对象
   * @throws WxErrorException the wx error exception
   */
  WxCpChatDataGroupChatInfo groupChatInfo(@NonNull WxCpChatDataGroupChatRequest request, String corpId) throws WxErrorException;


  /**
   * 获取单聊会话同意情况
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/chatdata/getagreestatus/single?access_token=ACCESS_TOKEN
   *
   * @param request 获取单聊会话同意情况请求对象
   * @return WxCpChatDataAgreeInfo：获取会话同意情况返回对象
   * @throws WxErrorException the wx error exception
   */
  WxCpChatDataAgreeInfo agreeStatus(@NonNull WxCpChatDataAgreeRequest request, String corpId) throws WxErrorException;

}
