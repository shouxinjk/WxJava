package me.chanjar.weixin.cp.api.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpChatDataService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.chatdata.*;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.ChatData.*;

/**
 * 企业微信会话存档相关接口.
 * @author Gorgeous
 */
@Slf4j
@RequiredArgsConstructor
public class WxCpChatDataServiceImpl implements WxCpChatDataService {
  private final WxCpService cpService;

  @Override
  public WxCpChatDataAuthUserList authUserList(@NonNull WxCpChatDataAuthUserRequest request) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_AUTH_USER_LIST);
    String responseContent = this.cpService.post(apiUrl, request.toJson());
    return WxCpChatDataAuthUserList.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp setPublicKey(@NonNull WxCpChatDataSetPublicKey request) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(SET_PUBLIC_KEY);
    String responseContent = this.cpService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataMsgList syncMsg(@NonNull WxCpChatDataSyncMsg request) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(SYNC_MSG);
    String responseContent = this.cpService.post(apiUrl, request.toJson());
    return WxCpChatDataMsgList.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataGroupChatInfo groupChatInfo(@NonNull WxCpChatDataGroupChatRequest request) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GROUP_CHAT_GET);
    String responseContent = this.cpService.post(apiUrl, request.toJson());
    return WxCpChatDataGroupChatInfo.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataAgreeInfo agreeStatus(@NonNull WxCpChatDataAgreeRequest request) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_AGREE_STATUS);
    String responseContent = this.cpService.post(apiUrl, request.toJson());
    return WxCpChatDataAgreeInfo.fromJson(responseContent);
  }
}
