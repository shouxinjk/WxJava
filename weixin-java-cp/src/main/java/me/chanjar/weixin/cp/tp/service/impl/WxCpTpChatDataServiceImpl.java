package me.chanjar.weixin.cp.tp.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.chatdata.*;
import me.chanjar.weixin.cp.tp.service.WxCpTpChatDataService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.ChatData.*;

/**
 * 企业微信会话存档接口实现类.
 * @author Gorgeous
 */
@Slf4j
@RequiredArgsConstructor
public class WxCpTpChatDataServiceImpl implements WxCpTpChatDataService {
  private final WxCpTpService mainService;

  @Override
  public WxCpChatDataAuthUserList authUserList(@NonNull WxCpChatDataAuthUserRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(GET_AUTH_USER_LIST);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpChatDataAuthUserList.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp setPublicKey(@NonNull WxCpChatDataSetPublicKey request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SET_PUBLIC_KEY);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataMsgList syncMsg(@NonNull WxCpChatDataSyncMsg request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SYNC_MSG);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpChatDataMsgList.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataGroupChatInfo groupChatInfo(@NonNull WxCpChatDataGroupChatRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_GET);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpChatDataGroupChatInfo.fromJson(responseContent);
  }

  @Override
  public WxCpChatDataAgreeInfo agreeStatus(@NonNull WxCpChatDataAgreeRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(GET_AGREE_STATUS);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpChatDataAgreeInfo.fromJson(responseContent);
  }
}
