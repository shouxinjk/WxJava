package me.chanjar.weixin.cp.tp.service.impl;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpGroupRobotService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import me.chanjar.weixin.cp.bean.message.WxCpGroupRobotMessage;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.WxCpTpConfigStorage;
import me.chanjar.weixin.cp.constant.WxCpApiPathConsts;
import me.chanjar.weixin.cp.tp.service.WxCpTpGroupRobotService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static me.chanjar.weixin.cp.constant.WxCpConsts.GroupRobotMsgType;

/**
 * 企业微信群机器人消息发送api 实现
 *
 * @author yr  created on  2020-08-20
 */
@RequiredArgsConstructor
public class WxCpTpGroupRobotServiceImpl implements WxCpTpGroupRobotService {
  private final WxCpTpService cpTpService;
//
//  private String getWebhookUrl() throws WxErrorException {
//    WxCpTpConfigStorage wxCpTpConfigStorage = this.cpTpService.getWxCpTpConfigStorage();
//    final String webhookKey = wxCpTpConfigStorage.getWebhookKey();
//    if (StringUtils.isEmpty(webhookKey)) {
//      throw new WxErrorException("请先设置WebhookKey");
//    }
//    return wxCpTpConfigStorage.getApiUrl(WxCpApiPathConsts.WEBHOOK_SEND) + webhookKey;
//  }
//
//  @Override
//  public void sendText(String content, List<String> mentionedList, List<String> mobileList) throws WxErrorException {
//    this.sendText(this.getWebhookUrl(), content, mentionedList, mobileList);
//  }
//
//  @Override
//  public void sendMarkdown(String content) throws WxErrorException {
//    this.sendMarkdown(this.getWebhookUrl(), content);
//  }
//
//  @Override
//  public void sendImage(String base64, String md5) throws WxErrorException {
//    this.sendImage(this.getWebhookUrl(), base64, md5);
//  }
//
//  @Override
//  public void sendNews(List<NewArticle> articleList) throws WxErrorException {
//    this.sendNews(this.getWebhookUrl(), articleList);
//  }

  @Override
  public void sendText(String webhookUrl, String content, List<String> mentionedList, List<String> mobileList) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, new WxCpGroupRobotMessage()
      .setMsgType(GroupRobotMsgType.TEXT)
      .setContent(content)
      .setMentionedList(mentionedList)
      .setMentionedMobileList(mobileList)
      .toJson());
  }

  @Override
  public void sendMarkdown(String webhookUrl, String content) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, new WxCpGroupRobotMessage()
      .setMsgType(GroupRobotMsgType.MARKDOWN)
      .setContent(content)
      .toJson());
  }

  @Override
  public void sendImage(String webhookUrl, String base64, String md5) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, new WxCpGroupRobotMessage()
      .setMsgType(GroupRobotMsgType.IMAGE)
      .setBase64(base64)
      .setMd5(md5).toJson());
  }

  @Override
  public void sendNews(String webhookUrl, List<NewArticle> articleList) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, new WxCpGroupRobotMessage()
      .setMsgType(GroupRobotMsgType.NEWS)
      .setArticles(articleList).toJson());
  }

  @Override
  public void sendFile(String webhookUrl, String mediaId) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, new WxCpGroupRobotMessage()
      .setMsgType(GroupRobotMsgType.FILE)
      .setMediaId(mediaId).toJson());
  }

  @Override
  public void sendTemplateCardMessage(String webhookUrl, WxCpGroupRobotMessage wxCpGroupRobotMessage) throws WxErrorException {
    this.cpTpService.postWithoutToken(webhookUrl, wxCpGroupRobotMessage.toJson());
  }

}
