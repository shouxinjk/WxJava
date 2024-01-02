package me.chanjar.weixin.cp.tp.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxCpErrorMsgEnum;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.common.util.BeanUtils;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.external.*;
import me.chanjar.weixin.cp.bean.external.acquisition.*;
import me.chanjar.weixin.cp.bean.external.contact.*;
import me.chanjar.weixin.cp.bean.external.interceptrule.WxCpInterceptRule;
import me.chanjar.weixin.cp.bean.external.interceptrule.WxCpInterceptRuleAddRequest;
import me.chanjar.weixin.cp.tp.service.WxCpTpExternalContactService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Department.DEPARTMENT_LIST;
import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.ExternalContact.*;

/**
 * The type Wx cp external contact service.
 *
 * @author 曹祖鹏 & yuanqixun & Mr.Pan & Wang_Wong
 */
@RequiredArgsConstructor
public class WxCpTpExternalContactServiceImpl implements WxCpTpExternalContactService {
  private final WxCpTpService mainService;
	
	@Override
	public WxCpListContactWayResult listContactWay(String corpId, WxCpContactWayListRequest request) throws WxErrorException {
		String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(LIST_CONTACT_WAY);
	    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
	    return WxCpListContactWayResult.fromJson(this.mainService.post(url, request.toJson()));
	}

  @Override
  public WxCpContactWayResult addContactWay(String corpId, WxCpContactWayInfo info) throws WxErrorException {

    if (info.getContactWay().getUsers() != null && info.getContactWay().getUsers().size() > 100) {
      throw new WxRuntimeException("「联系我」使用人数默认限制不超过100人(包括部门展开后的人数)");
    }

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_CONTACT_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpContactWayResult.fromJson(this.mainService.post(url, info.getContactWay().toJson()));
  }

  @Override
  public WxCpContactWayInfo getContactWay(String corpId, String configId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("config_id", configId);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_CONTACT_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpContactWayInfo.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp updateContactWay(String corpId, WxCpContactWayInfo info) throws WxErrorException {
    if (StringUtils.isBlank(info.getContactWay().getConfigId())) {
      throw new WxRuntimeException("更新「联系我」方式需要指定configId");
    }
    if (info.getContactWay().getUsers() != null && info.getContactWay().getUsers().size() > 100) {
      throw new WxRuntimeException("「联系我」使用人数默认限制不超过100人(包括部门展开后的人数)");
    }

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UPDATE_CONTACT_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);

    return WxCpBaseResp.fromJson(this.mainService.post(url, info.getContactWay().toJson()));
  }

  @Override
  public WxCpBaseResp deleteContactWay(String corpId, String configId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("config_id", configId);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(DEL_CONTACT_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp closeTempChat(String corpId, String userId, String externalUserId) throws WxErrorException {

    JsonObject json = new JsonObject();
    json.addProperty("userid", userId);
    json.addProperty("external_userid", externalUserId);


    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CLOSE_TEMP_CHAT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);

    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpExternalContactInfo getExternalContact(String corpId, String userId) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_EXTERNAL_CONTACT + userId);
    url += "&access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpExternalContactInfo.fromJson(this.mainService.get(url, null));
  }

  @Override
  public WxCpExternalContactInfo getContactDetail(String corpId, String userId, String cursor) throws WxErrorException {
    String params = userId;
    if (StringUtils.isNotEmpty(cursor)) {
      params = params + "&cursor=" + cursor;
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_CONTACT_DETAIL + params);
    url += "&access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpExternalContactInfo.fromJson(this.mainService.get(url, null));
  }

  @Override
  public String convertToOpenid(String corpId, String externalUserId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("external_userid", externalUserId);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CONVERT_TO_OPENID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, json.toString());
    return GsonParser.parse(responseContent).get("openid").getAsString();
  }

  @Override
  public String unionidToExternalUserid(String corpId, String unionid, String openid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("unionid", unionid);
    if (StringUtils.isNotEmpty(openid)) {
      json.addProperty("openid", openid);
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UNIONID_TO_EXTERNAL_USERID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, json.toString());
    return GsonParser.parse(responseContent).get("external_userid").getAsString();
  }

  @Override
  public String toServiceExternalUserid(String corpId, String externalUserid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("external_userid", externalUserid);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(TO_SERVICE_EXTERNAL_USERID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, json.toString());
    return GsonParser.parse(responseContent).get("external_userid").getAsString();
  }

  @Override
  public String fromServiceExternalUserid(String corpId, String externalUserid, String sourceAgentId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("external_userid", externalUserid);
    json.addProperty("source_agentid", sourceAgentId);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(FROM_SERVICE_EXTERNAL_USERID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, json.toString());
    return GsonParser.parse(responseContent).get("external_userid").getAsString();
  }

  @Override
  public WxCpExternalUserIdList unionidToExternalUserid3rd(String corpId, String unionid, String openid,
                                                           String corpid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("unionid", unionid);
    json.addProperty("openid", openid);
    if (StringUtils.isNotEmpty(corpid)) {
      json.addProperty("corpid", corpid);
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UNIONID_TO_EXTERNAL_USERID_3RD);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpExternalUserIdList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpNewExternalUserIdList getNewExternalUserId(String corpId, String[] externalUserIdList) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(externalUserIdList)) {
      json.add("external_userid_list", new Gson().toJsonTree(externalUserIdList).getAsJsonArray());
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_NEW_EXTERNAL_USERID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpNewExternalUserIdList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp finishExternalUserIdMigration(String corpid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("corpid", corpid);
    final String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(FINISH_EXTERNAL_USERID_MIGRATION);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public String opengidToChatid(String corpId, String opengid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("opengid", opengid);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(OPENID_TO_CHATID);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, json.toString());
    return GsonParser.parse(responseContent).get("chat_id").getAsString();
  }

  @Override
  public WxCpExternalContactBatchInfo getContactDetailBatch(String corpId, String[] userIdList, String cursor, Integer limit)
    throws WxErrorException {
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_CONTACT_DETAIL_BATCH);
    url += "access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject json = new JsonObject();
    json.add("access_token", new Gson().toJsonTree(userIdList).getAsJsonArray());
    json.add("userid_list", new Gson().toJsonTree(userIdList).getAsJsonArray());
    if (StringUtils.isNotBlank(cursor)) {
      json.addProperty("cursor", cursor);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
    String responseContent = this.mainService.post(url, json.toString());
    return WxCpExternalContactBatchInfo.fromJson(responseContent);
  }

  @Override
  public void updateRemark(String corpId, WxCpUpdateRemarkRequest request) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UPDATE_REMARK);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url, request.toJson());
  }

  @Override
  public List<String> listExternalContacts(String corpId, String userId) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(LIST_EXTERNAL_CONTACT + userId);
    url += "&access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    try {
      String responseContent = this.mainService.get(url, null);
      return WxCpUserExternalContactList.fromJson(responseContent).getExternalUserId();
    } catch (WxErrorException e) {
      // not external contact,无客户则返回空列表
      if (e.getError().getErrorCode() == WxCpErrorMsgEnum.CODE_84061.getCode()) {
        return Collections.emptyList();
      }
      throw e;
    }
  }

  @Override
  public List<String> listFollowers(String corpId) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_FOLLOW_USER_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.get(url, null);
    return WxCpUserWithExternalPermission.fromJson(responseContent).getFollowers();
  }

  @Override
  public WxCpUserExternalUnassignList listUnassignedList(String corpId, Integer pageIndex, String cursor, Integer pageSize) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (pageIndex != null) {
      json.addProperty("page_id", pageIndex);
    }
    json.addProperty("cursor", StringUtils.isEmpty(cursor) ? "" : cursor);
    json.addProperty("page_size", pageSize == null ? 1000 : pageSize);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(LIST_UNASSIGNED_CONTACT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalUnassignList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp transferExternalContact(String corpId, String externalUserid, String handOverUserid, String takeOverUserid) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("external_userid", externalUserid);
    json.addProperty("handover_userid", handOverUserid);
    json.addProperty("takeover_userid", takeOverUserid);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(TRANSFER_UNASSIGNED_CONTACT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserTransferCustomerResp transferCustomer(String corpId, WxCpUserTransferCustomerReq req) throws WxErrorException {
    BeanUtils.checkRequiredFields(req);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(TRANSFER_CUSTOMER);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    final String result = this.mainService.post(url, req.toJson());
    return WxCpUserTransferCustomerResp.fromJson(result);
  }

  @Override
  public WxCpUserTransferResultResp transferResult(String corpId, String handOverUserid, String takeOverUserid,
                                                   String cursor) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("cursor", cursor);
    json.addProperty("handover_userid", handOverUserid);
    json.addProperty("takeover_userid", takeOverUserid);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(TRANSFER_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserTransferResultResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserTransferCustomerResp resignedTransferCustomer(String corpId, WxCpUserTransferCustomerReq req)
    throws WxErrorException {
    BeanUtils.checkRequiredFields(req);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(RESIGNED_TRANSFER_CUSTOMER);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserTransferCustomerResp.fromJson(this.mainService.post(url, req.toJson()));
  }

  @Override
  public WxCpUserTransferResultResp resignedTransferResult(String corpId, String handOverUserid,
                                                           String takeOverUserid, String cursor)
    throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("cursor", cursor);
    json.addProperty("handover_userid", handOverUserid);
    json.addProperty("takeover_userid", takeOverUserid);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(RESIGNED_TRANSFER_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserTransferResultResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatList listGroupChat(String corpId, Integer pageIndex, Integer pageSize, int status,
                                                     String[] userIds, String[] partyIds) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("offset", pageIndex == null ? 0 : pageIndex);
    json.addProperty("limit", pageSize == null ? 100 : pageSize);
    json.addProperty("status_filter", status);
    if (ArrayUtils.isNotEmpty(userIds) || ArrayUtils.isNotEmpty(partyIds)) {
      JsonObject ownerFilter = new JsonObject();
      if (ArrayUtils.isNotEmpty(userIds)) {
        ownerFilter.add("userid_list", new Gson().toJsonTree(userIds).getAsJsonArray());
      }
      if (ArrayUtils.isNotEmpty(partyIds)) {
        ownerFilter.add("partyid_list", new Gson().toJsonTree(partyIds).getAsJsonArray());
      }
      json.add("owner_filter", ownerFilter);
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatList listGroupChat(String corpId, Integer limit, String cursor, int status, String[] userIds) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("cursor", cursor == null ? "" : cursor);
    json.addProperty("limit", limit == null ? 100 : limit);
    json.addProperty("status_filter", status);
    if (ArrayUtils.isNotEmpty(userIds)) {
      JsonObject ownerFilter = new JsonObject();
      if (ArrayUtils.isNotEmpty(userIds)) {
        ownerFilter.add("userid_list", new Gson().toJsonTree(userIds).getAsJsonArray());
      }
      json.add("owner_filter", ownerFilter);
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatInfo getGroupChat(String corpId, String chatId, Integer needName) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("chat_id", chatId);
    json.addProperty("need_name", needName);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_INFO);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatInfo.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatTransferResp transferGroupChat(String corpId, String[] chatIds, String newOwner) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(chatIds)) {
      json.add("chat_id_list", new Gson().toJsonTree(chatIds).getAsJsonArray());
    }
    json.addProperty("new_owner", newOwner);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_TRANSFER);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatTransferResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatTransferResp onjobTransferGroupChat(String corpId, String[] chatIds, String newOwner) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(chatIds)) {
      json.add("chat_id_list", new Gson().toJsonTree(chatIds).getAsJsonArray());
    }
    json.addProperty("new_owner", newOwner);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_CHAT_ONJOB_TRANSFER);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatTransferResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalUserBehaviorStatistic getUserBehaviorStatistic(String corpId, Date startTime, Date endTime,
                                                                        String[] userIds, String[] partyIds) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("start_time", startTime.getTime() / 1000);
    json.addProperty("end_time", endTime.getTime() / 1000);
    if (ArrayUtils.isNotEmpty(userIds) || ArrayUtils.isNotEmpty(partyIds)) {
      if (ArrayUtils.isNotEmpty(userIds)) {
        json.add("userid", new Gson().toJsonTree(userIds).getAsJsonArray());
      }
      if (ArrayUtils.isNotEmpty(partyIds)) {
        json.add("partyid", new Gson().toJsonTree(partyIds).getAsJsonArray());
      }
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(LIST_USER_BEHAVIOR_DATA);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalUserBehaviorStatistic.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalGroupChatStatistic getGroupChatStatistic(String corpId, Date startTime, Integer orderBy, Integer orderAsc,
                                                                  Integer pageIndex, Integer pageSize,
                                                                  String[] userIds, String[] partyIds) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("day_begin_time", startTime.getTime() / 1000);
    json.addProperty("order_by", orderBy == null ? 1 : orderBy);
    json.addProperty("order_asc", orderAsc == null ? 0 : orderAsc);
    json.addProperty("offset", pageIndex == null ? 0 : pageIndex);
    json.addProperty("limit", pageSize == null ? 500 : pageSize);
    if (ArrayUtils.isNotEmpty(userIds) || ArrayUtils.isNotEmpty(partyIds)) {
      JsonObject ownerFilter = new JsonObject();
      if (ArrayUtils.isNotEmpty(userIds)) {
        ownerFilter.add("userid_list", new Gson().toJsonTree(userIds).getAsJsonArray());
      }
      if (ArrayUtils.isNotEmpty(partyIds)) {
        ownerFilter.add("partyid_list", new Gson().toJsonTree(partyIds).getAsJsonArray());
      }
      json.add("owner_filter", ownerFilter);
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(LIST_GROUP_CHAT_DATA);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalGroupChatStatistic.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpMsgTemplateAddResult addMsgTemplate(String corpId, WxCpMsgTemplate wxCpMsgTemplate) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_MSG_TEMPLATE);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpMsgTemplateAddResult.fromJson(this.mainService.post(url, wxCpMsgTemplate.toJson()));
  }

  @Override
  public WxCpBaseResp remindGroupMsgSend(String corpId, String msgId) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("msgid", msgId);
    String url = this.mainService.getWxCpTpConfigStorage()
                                       .getApiUrl(REMIND_GROUP_MSG_SEND);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, params.toString()));
  }

  @Override
  public WxCpBaseResp cancelGroupMsgSend(String corpId, String msgId) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("msgid", msgId);
    String url = this.mainService.getWxCpTpConfigStorage()
                                       .getApiUrl(CANCEL_GROUP_MSG_SEND);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, params.toString()));
  }

  @Override
  public void sendWelcomeMsg(String corpId, WxCpWelcomeMsg msg) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(SEND_WELCOME_MSG);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url, msg.toJson());
  }

  @Override
  public WxCpUserExternalTagGroupList getCorpTagList(String corpId, String[] tagId) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(tagId)) {
      json.add("tag_id", new Gson().toJsonTree(tagId).getAsJsonArray());
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_CORP_TAG_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalTagGroupList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalTagGroupList getCorpTagList(String corpId, String[] tagId, String[] groupId) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(tagId)) {
      json.add("tag_id", new Gson().toJsonTree(tagId).getAsJsonArray());
    }
    if (ArrayUtils.isNotEmpty(groupId)) {
      json.add("group_id", new Gson().toJsonTree(groupId).getAsJsonArray());
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_CORP_TAG_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalTagGroupList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpUserExternalTagGroupInfo addCorpTag(String corpId, WxCpUserExternalTagGroupInfo tagGroup) throws WxErrorException {

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_CORP_TAG);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpUserExternalTagGroupInfo.fromJson(this.mainService.post(url, tagGroup.getTagGroup().toJson()));
  }

  @Override
  public WxCpBaseResp editCorpTag(String corpId, String id, String name, Integer order) throws WxErrorException {

    JsonObject json = new JsonObject();
    json.addProperty("id", id);
    json.addProperty("name", name);
    json.addProperty("order", order);
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(EDIT_CORP_TAG);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp delCorpTag(String corpId, String[] tagId, String[] groupId) throws WxErrorException {
    JsonObject json = new JsonObject();
    if (ArrayUtils.isNotEmpty(tagId)) {
      json.add("tag_id", new Gson().toJsonTree(tagId).getAsJsonArray());
    }
    if (ArrayUtils.isNotEmpty(groupId)) {
      json.add("group_id", new Gson().toJsonTree(groupId).getAsJsonArray());
    }

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(DEL_CORP_TAG);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp markTag(String corpId, String userid, String externalUserid, String[] addTag, String[] removeTag) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("userid", userid);
    json.addProperty("external_userid", externalUserid);

    if (ArrayUtils.isNotEmpty(addTag)) {
      json.add("add_tag", new Gson().toJsonTree(addTag).getAsJsonArray());
    }
    if (ArrayUtils.isNotEmpty(removeTag)) {
      json.add("remove_tag", new Gson().toJsonTree(removeTag).getAsJsonArray());
    }

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(MARK_TAG);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpAddMomentResult addMomentTask(String corpId, WxCpAddMomentTask task) throws WxErrorException {
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_MOMENT_TASK);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpAddMomentResult.fromJson(this.mainService.post(url, task.toJson()));
  }

  @Override
  public WxCpGetMomentTaskResult getMomentTaskResult(String corpId, String jobId) throws WxErrorException {
    String params = "&jobid=" + jobId;
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_TASK_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentTaskResult.fromJson(this.mainService.get(url, params));
  }

  @Override
  public WxCpGetMomentList getMomentList(String corpId, Long startTime, Long endTime, String creator, Integer filterType,
                                         String cursor, Integer limit) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("start_time", startTime);
    json.addProperty("end_time", endTime);
    if (!StringUtils.isEmpty(creator)) {
      json.addProperty("creator", creator);
    }
    if (filterType != null) {
      json.addProperty("filter_type", filterType);
    }
    if (!StringUtils.isEmpty(cursor)) {
      json.addProperty("cursor", cursor);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGetMomentTask getMomentTask(String corpId, String momentId, String cursor, Integer limit)
    throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("moment_id", momentId);
    if (!StringUtils.isEmpty(cursor)) {
      json.addProperty("cursor", cursor);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_TASK);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentTask.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGetMomentCustomerList getMomentCustomerList(String corpId, String momentId, String userId,
                                                         String cursor, Integer limit) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("moment_id", momentId);
    json.addProperty("userid", userId);
    if (!StringUtils.isEmpty(cursor)) {
      json.addProperty("cursor", cursor);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_CUSTOMER_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentCustomerList.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGetMomentSendResult getMomentSendResult(String corpId, String momentId, String userId,
                                                     String cursor, Integer limit) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("moment_id", momentId);
    json.addProperty("userid", userId);
    if (!StringUtils.isEmpty(cursor)) {
      json.addProperty("cursor", cursor);
    }
    if (limit != null) {
      json.addProperty("limit", limit);
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_SEND_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentSendResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGetMomentComments getMomentComments(String corpId, String momentId, String userId)
    throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("moment_id", momentId);
    json.addProperty("userid", userId);
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_MOMENT_COMMENTS);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGetMomentComments.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGroupMsgListResult getGroupMsgListV2(String corpId, String chatType, Date startTime, Date endTime,
                                                  String creator, Integer filterType, Integer limit, String cursor) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("chat_type", chatType);
    json.addProperty("start_time", startTime.getTime() / 1000);
    json.addProperty("end_time", endTime.getTime() / 1000);
    json.addProperty("creator", creator);
    json.addProperty("filter_type", filterType);
    json.addProperty("limit", limit);
    json.addProperty("cursor", cursor);

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_GROUP_MSG_LIST_V2);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupMsgListResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGroupMsgSendResult getGroupMsgSendResult(String corpId, String msgid, String userid, Integer limit, String cursor) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("msgid", msgid);
    json.addProperty("userid", userid);
    json.addProperty("limit", limit);
    json.addProperty("cursor", cursor);

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_GROUP_MSG_SEND_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupMsgSendResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGroupMsgResult getGroupMsgResult(String corpId, String msgid, Integer limit, String cursor) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("msgid", msgid);
    json.addProperty("limit", limit);
    json.addProperty("cursor", cursor);

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_GROUP_MSG_RESULT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupMsgResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpGroupMsgTaskResult getGroupMsgTask(String corpId, String msgid, Integer limit, String cursor) throws WxErrorException {

     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_GROUP_MSG_TASK);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupMsgTaskResult.fromJson(this.mainService.post(url,
      GsonHelper.buildJsonObject("msgid", msgid,
        "limit", limit,
        "cursor", cursor)));
  }

  @Override
  public String addGroupWelcomeTemplate(String corpId, WxCpGroupWelcomeTemplateResult template) throws WxErrorException {
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_WELCOME_TEMPLATE_ADD);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    final String responseContent = this.mainService.post(url, template.toJson());
    return GsonParser.parse(responseContent).get("template_id").getAsString();
  }

  @Override
  public WxCpBaseResp editGroupWelcomeTemplate(String corpId, WxCpGroupWelcomeTemplateResult template) throws WxErrorException {
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_WELCOME_TEMPLATE_EDIT);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupWelcomeTemplateResult.fromJson(this.mainService.post(url, template.toJson()));
  }

  @Override
  public WxCpGroupWelcomeTemplateResult getGroupWelcomeTemplate(String corpId, String templateId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("template_id", templateId);
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_WELCOME_TEMPLATE_GET);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupWelcomeTemplateResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpBaseResp delGroupWelcomeTemplate(String corpId, String templateId, String agentId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("template_id", templateId);
    if (!StringUtils.isEmpty(agentId)) {
      json.addProperty("agentid", agentId);
    }
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GROUP_WELCOME_TEMPLATE_DEL);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpProductAlbumListResult getProductAlbumList(String corpId, Integer limit, String cursor) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("limit", limit);
    json.addProperty("cursor", cursor);
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_PRODUCT_ALBUM_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpProductAlbumListResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxCpProductAlbumResult getProductAlbum(String corpId, String productId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("product_id", productId);
     String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_PRODUCT_ALBUM);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpProductAlbumResult.fromJson(this.mainService.post(url, json.toString()));
  }

  @Override
  public WxMediaUploadResult uploadAttachment(String corpId, String mediaType, String fileType, Integer attachmentType,
                                              InputStream inputStream) throws WxErrorException, IOException {
    return uploadAttachment(corpId, mediaType, attachmentType, FileUtils.createTmpFile(inputStream,
      UUID.randomUUID().toString(), fileType));
  }

  @Override
  public WxMediaUploadResult uploadAttachment(String corpId, String mediaType, Integer attachmentType, File file)
    throws WxErrorException {
    String params = "?media_type=" + mediaType + "&attachment_type=" + attachmentType;
    params += "&access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    final String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UPLOAD_ATTACHMENT + params);
    return this.mainService.execute(MediaUploadRequestExecutor.create(
      this.mainService.getRequestHttp()), url, file);
  }

  @Override
  public String addInterceptRule(String corpId, WxCpInterceptRuleAddRequest ruleAddRequest) throws WxErrorException {
	String url = this.mainService.getWxCpTpConfigStorage()
		      .getApiUrl(ADD_INTERCEPT_RULE);
	url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, ruleAddRequest);
    return GsonParser.parse(responseContent).get("rule_id").getAsString();
  }

  @Override
  public void updateInterceptRule(String corpId, WxCpInterceptRule interceptRule) throws WxErrorException {
		String url = this.mainService.getWxCpTpConfigStorage()
			      .getApiUrl(UPDATE_INTERCEPT_RULE);
		url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url,
      interceptRule);
  }

  @Override
  public void delInterceptRule(String corpId, String ruleId) throws WxErrorException {
	String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(DEL_INTERCEPT_RULE);
	url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url,
      GsonHelper.buildJsonObject("rule_id", ruleId));
  }

  @Override
  public String addProductAlbum(String corpId, WxCpProductAlbumInfo wxCpProductAlbumInfo) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_PRODUCT_ALBUM);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(url, wxCpProductAlbumInfo.toJson());
    return GsonParser.parse(responseContent).get("product_id").getAsString();
  }

  @Override
  public void updateProductAlbum(String corpId, WxCpProductAlbumInfo wxCpProductAlbumInfo) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UPDATE_PRODUCT_ALBUM);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url, wxCpProductAlbumInfo.toJson());
  }

  @Override
  public void deleteProductAlbum(String corpId, String productId) throws WxErrorException {
    JsonObject o = new JsonObject();
    o.addProperty("product_id", productId);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(DELETE_PRODUCT_ALBUM);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    this.mainService.post(url, o.toString());
  }

  @Override
  public WxCpCustomerAcquisitionList customerAcquisitionLinkList(String corpId, Integer limit, String cursor) throws WxErrorException {
    JsonObject o = new JsonObject();
    o.addProperty("limit", limit);
    o.addProperty("cursor", cursor);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_LINK_LIST);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpCustomerAcquisitionList.fromJson(this.mainService.post(url, o));
  }

  @Override
  public WxCpCustomerAcquisitionInfo customerAcquisitionLinkGet(String corpId, String linkId) throws WxErrorException {
    JsonObject o = new JsonObject();
    o.addProperty("link_id", linkId);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_LINK_GET);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpCustomerAcquisitionInfo.fromJson(this.mainService.post(url, o));
  }

  @Override
  public WxCpCustomerAcquisitionCreateResult customerAcquisitionLinkCreate(String corpId, WxCpCustomerAcquisitionRequest wxCpCustomerAcquisitionRequest) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_LINK_CREATE);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpCustomerAcquisitionCreateResult.fromJson(this.mainService.post(url, wxCpCustomerAcquisitionRequest.toJson()));
  }

  @Override
  public WxCpBaseResp customerAcquisitionUpdate(String corpId, WxCpCustomerAcquisitionRequest wxCpCustomerAcquisitionRequest) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_LINK_UPDATE);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, wxCpCustomerAcquisitionRequest.toJson()));
  }

  @Override
  public WxCpBaseResp customerAcquisitionLinkDelete(String corpId, String linkId) throws WxErrorException {
    JsonObject o = new JsonObject();
    o.addProperty("link_id", linkId);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_LINK_DELETE);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, o));
  }

  @Override
  public WxCpCustomerAcquisitionCustomerList customerAcquisitionCustomer(String corpId, String linkId, Integer limit, String cursor) throws WxErrorException {
    JsonObject o = new JsonObject();
    o.addProperty("link_id", linkId);
    o.addProperty("limit", limit);
    o.addProperty("cursor", cursor);

    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_CUSTOMER);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpCustomerAcquisitionCustomerList.fromJson(this.mainService.post(url, o));
  }

  @Override
  public WxCpCustomerAcquisitionQuota customerAcquisitionQuota(String corpId) throws WxErrorException {
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(CUSTOMER_ACQUISITION_QUOTA);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpCustomerAcquisitionQuota.fromJson(this.mainService.get(url, null));
  }

  @Override
  public WxCpGroupJoinWayResult addJoinWay(String corpId, WxCpGroupJoinWayInfo wxCpGroupJoinWayInfo) throws WxErrorException {
    if (wxCpGroupJoinWayInfo.getJoinWay().getChatIdList() != null && wxCpGroupJoinWayInfo.getJoinWay().getChatIdList().size() > 5) {
      throw new WxRuntimeException("使用该配置的客户群ID列表，支持5个");
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(ADD_JOIN_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);

    return WxCpGroupJoinWayResult.fromJson(this.mainService.post(url, wxCpGroupJoinWayInfo.getJoinWay().toJson()));
  }

  @Override
  public WxCpBaseResp updateJoinWay(String corpId, WxCpGroupJoinWayInfo wxCpGroupJoinWayInfo) throws WxErrorException {
    if (wxCpGroupJoinWayInfo.getJoinWay().getChatIdList() != null && wxCpGroupJoinWayInfo.getJoinWay().getChatIdList().size() > 5) {
      throw new WxRuntimeException("使用该配置的客户群ID列表，支持5个");
    }
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(UPDATE_JOIN_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, wxCpGroupJoinWayInfo.getJoinWay().toJson()));
  }

  @Override
  public WxCpGroupJoinWayInfo getJoinWay(String corpId, String configId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("config_id", configId);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(GET_JOIN_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpGroupJoinWayInfo.fromJson(this.mainService.post(url, json));
  }

  @Override
  public WxCpBaseResp delJoinWay(String corpId, String configId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("config_id", configId);
    String url = this.mainService.getWxCpTpConfigStorage().getApiUrl(DEL_JOIN_WAY);
    url += "?access_token=" + this.mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    return WxCpBaseResp.fromJson(this.mainService.post(url, json));
  }

}
