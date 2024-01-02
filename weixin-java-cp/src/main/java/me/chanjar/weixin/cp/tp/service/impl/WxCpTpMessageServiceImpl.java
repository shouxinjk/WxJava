package me.chanjar.weixin.cp.tp.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpMessageService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.*;
import me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Message;
import me.chanjar.weixin.cp.tp.service.WxCpTpMessageService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 消息推送接口实现类.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a> created on  2020-08-30
 */
@RequiredArgsConstructor
public class WxCpTpMessageServiceImpl implements WxCpTpMessageService {
  private final WxCpTpService mainService;

  @Override
  public WxCpMessageSendResult send(String corpId, WxCpMessage message) throws WxErrorException {
//    Integer agentId = message.getAgentId();
//    if (null == agentId) {
//      message.setAgentId(this.mainService.getWxCpTpConfigStorage().getAgentId());
//    }

    String url = Message.MESSAGE_SEND;
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpMessageSendResult.fromJson(this.mainService.post(this.mainService.getWxCpTpConfigStorage()
      .getApiUrl(url), message.toJson()));
  }

  @Override
  public WxCpMessageSendStatistics getStatistics(String corpId, int timeType) throws WxErrorException {
	String url = Message.GET_STATISTICS;
	url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpMessageSendStatistics.fromJson(this.mainService.post(this.mainService.getWxCpTpConfigStorage().getApiUrl(url),
      WxCpGsonBuilder.create().toJson(ImmutableMap.of("time_type", timeType))));
  }

  @Override
  public WxCpLinkedCorpMessageSendResult sendLinkedCorpMessage(String corpId, WxCpLinkedCorpMessage message) throws WxErrorException {
//    Integer agentId = message.getAgentId();
//    if (null == agentId) {
//      message.setAgentId(this.mainService.getWxCpTpConfigStorage().getAgentId());
//    }
    
    String url = Message.LINKEDCORP_MESSAGE_SEND;
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpLinkedCorpMessageSendResult.fromJson(this.mainService.post(this.mainService.getWxCpTpConfigStorage()
      .getApiUrl(url), message.toJson()));
  }

  @Override
  public WxCpSchoolContactMessageSendResult sendSchoolContactMessage(String corpId, WxCpSchoolContactMessage message) throws WxErrorException {
//    if (null == message.getAgentId()) {
//      message.setAgentId(this.mainService.getWxCpTpConfigStorage().getAgentId());
//    }
    String url = Message.EXTERNAL_CONTACT_MESSAGE_SEND;
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpSchoolContactMessageSendResult.fromJson(this.mainService.post(this.mainService.getWxCpTpConfigStorage()
      .getApiUrl(url), message.toJson()));
  }

  @Override
  public void recall(String corpId, String msgId) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("msgid", msgId);
    String url = Message.MESSAGE_RECALL;
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String apiUrl = this.mainService.getWxCpTpConfigStorage().getApiUrl(url);
    this.mainService.post(apiUrl, jsonObject.toString());
  }

}
