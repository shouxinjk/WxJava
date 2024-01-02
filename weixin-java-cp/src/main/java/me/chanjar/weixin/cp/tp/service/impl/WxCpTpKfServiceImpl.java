package me.chanjar.weixin.cp.tp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.kf.*;
import me.chanjar.weixin.cp.tp.service.WxCpTpKfService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Kf.*;

/**
 * 微信客服接口-服务实现
 *
 * @author Fu  created on  2022/1/19 19:41
 */
@RequiredArgsConstructor
public class WxCpTpKfServiceImpl implements WxCpTpKfService {
  private final WxCpTpService mainService;
  private static final Gson GSON = new GsonBuilder().create();

  @Override
  public WxCpKfAccountAddResp addAccount(String corpId, WxCpKfAccountAdd add) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(ACCOUNT_ADD);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, WxCpGsonBuilder.create().toJson(add));
    return WxCpKfAccountAddResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp updAccount(String corpId, WxCpKfAccountUpd upd) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(ACCOUNT_UPD);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, WxCpGsonBuilder.create().toJson(upd));
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp delAccount(String corpId, WxCpKfAccountDel del) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(ACCOUNT_DEL);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, WxCpGsonBuilder.create().toJson(del));
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfAccountListResp listAccount(String corpId, Integer offset, Integer limit) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(ACCOUNT_LIST);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    if (offset != null) {
      json.addProperty("offset", offset);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
    String responseContent = mainService.post(url, json.toString());
    return WxCpKfAccountListResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfAccountLinkResp getAccountLink(String corpId, WxCpKfAccountLink link) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(ADD_CONTACT_WAY);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, WxCpGsonBuilder.create().toJson(link));
    return WxCpKfAccountLinkResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfServicerOpResp addServicer(String corpId, String openKfid, List<String> userIdList) throws WxErrorException {
    return servicerOp(corpId, openKfid, userIdList, SERVICER_ADD);
  }

  @Override
  public WxCpKfServicerOpResp delServicer(String corpId, String openKfid, List<String> userIdList) throws WxErrorException {
    return servicerOp(corpId, openKfid, userIdList, SERVICER_DEL);
  }

  private WxCpKfServicerOpResp servicerOp(String corpId, String openKfid, List<String> userIdList, String uri) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(uri);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    JsonArray userIdArray = new JsonArray();
    userIdList.forEach(userIdArray::add);
    json.add("userid_list", userIdArray);

    String responseContent = mainService.post(url, json.toString());
    return WxCpKfServicerOpResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfServicerListResp listServicer(String corpId, String openKfid) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SERVICER_LIST + openKfid);
    url = url + "&access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.get(url, null);
    return WxCpKfServicerListResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfServiceStateResp getServiceState(String corpId, String openKfid, String externalUserId)
    throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SERVICE_STATE_GET);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    json.addProperty("external_userid", externalUserId);

    String responseContent = mainService.post(url, json.toString());
    return WxCpKfServiceStateResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfServiceStateTransResp transServiceState(String corpId, String openKfid, String externalUserId,
                                                       Integer serviceState, String servicerUserId) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SERVICE_STATE_TRANS);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    json.addProperty("external_userid", externalUserId);
    json.addProperty("service_state", serviceState);
    json.addProperty("servicer_userid", servicerUserId);

    String responseContent = mainService.post(url, json.toString());
    return WxCpKfServiceStateTransResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfMsgListResp syncMsg(String corpId, String cursor, String token, Integer limit, Integer voiceFormat)
    throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SYNC_MSG);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    if (cursor != null) {
      json.addProperty("cursor", cursor);
    }
    if (token != null) {
      json.addProperty("token", token);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
    if (voiceFormat != null) {
      json.addProperty("voice_format", voiceFormat);
    }

    String responseContent = mainService.post(url, json);
    return WxCpKfMsgListResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfMsgListResp syncMsg(String corpId, String cursor, String token, Integer limit, Integer voiceFormat, String openKfId) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SYNC_MSG);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    if (cursor!=null) {
      json.addProperty("cursor", cursor);
    }
    if (token!=null) {
      json.addProperty("token", token);
    }
    if (limit!=null) {
      json.addProperty("limit", limit);
    }
    if (voiceFormat!=null) {
      json.addProperty("voice_format", voiceFormat);
    }
    if (openKfId != null) {
      json.addProperty("open_kfid", openKfId);
    }

    String responseContent = mainService.post(url, json);
    return WxCpKfMsgListResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfMsgSendResp sendMsg(String corpId, WxCpKfMsgSendRequest request) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SEND_MSG);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, GSON.toJson(request));

    return WxCpKfMsgSendResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfMsgSendResp sendMsgOnEvent(String corpId, WxCpKfMsgSendRequest request) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(SEND_MSG_ON_EVENT);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, GSON.toJson(request));

    return WxCpKfMsgSendResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfCustomerBatchGetResp customerBatchGet(String corpId, List<String> externalUserIdList)
    throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_BATCH_GET);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonArray array = new JsonArray();

    externalUserIdList.forEach(array::add);
    JsonObject json = new JsonObject();
    json.add("external_userid_list", array);
    String responseContent = mainService.post(url, json.toString());
    return WxCpKfCustomerBatchGetResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfServiceUpgradeConfigResp getUpgradeServiceConfig(String corpId) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_GET_UPGRADE_SERVICE_CONFIG);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String response = mainService.get(url, null);
    return WxCpKfServiceUpgradeConfigResp.fromJson(response);
  }

  @Override
  public WxCpBaseResp upgradeMemberService(String corpId, String openKfid, String externalUserId,
                                           String userid, String wording) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_UPGRADE_SERVICE);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    json.addProperty("external_userid", externalUserId);
    json.addProperty("type", 1);

    JsonObject memberJson = new JsonObject();
    memberJson.addProperty("userid", userid);
    memberJson.addProperty("wording", wording);
    json.add("member", memberJson);

    String response = mainService.post(url, json);
    return WxCpBaseResp.fromJson(response);
  }

  @Override
  public WxCpBaseResp upgradeGroupchatService(String corpId, String openKfid, String externalUserId,
                                              String chatId, String wording) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_UPGRADE_SERVICE);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    json.addProperty("external_userid", externalUserId);
    json.addProperty("type", 2);

    JsonObject groupchatJson = new JsonObject();
    groupchatJson.addProperty("chat_id", chatId);
    groupchatJson.addProperty("wording", wording);
    json.add("groupchat", groupchatJson);

    String response = mainService.post(url, json);
    return WxCpBaseResp.fromJson(response);
  }

  @Override
  public WxCpBaseResp cancelUpgradeService(String corpId, String openKfid, String externalUserId)
    throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_CANCEL_UPGRADE_SERVICE);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.addProperty("open_kfid", openKfid);
    json.addProperty("external_userid", externalUserId);
    String response = mainService.post(url, json);
    return WxCpBaseResp.fromJson(response);
  }

  @Override
  public WxCpKfGetCorpStatisticResp getCorpStatistic(String corpId, WxCpKfGetCorpStatisticRequest request) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(GET_CORP_STATISTIC);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, GSON.toJson(request));
    return WxCpKfGetCorpStatisticResp.fromJson(responseContent);
  }

  @Override
  public WxCpKfGetServicerStatisticResp getServicerStatistic(String corpId, WxCpKfGetServicerStatisticRequest request) throws WxErrorException {
    String url = mainService.getWxCpTpConfigStorage().getApiUrl(GET_SERVICER_STATISTIC);
    url = url + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = mainService.post(url, GSON.toJson(request));
    return WxCpKfGetServicerStatisticResp.fromJson(responseContent);
  }

}
