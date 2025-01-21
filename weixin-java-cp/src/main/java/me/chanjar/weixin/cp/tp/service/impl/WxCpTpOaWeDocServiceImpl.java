package me.chanjar.weixin.cp.tp.service.impl;

import com.google.gson.JsonObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.oa.doc.*;
import me.chanjar.weixin.cp.tp.service.WxCpTpOaWeDocService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Oa.*;

/**
 * 企业微信微盘接口实现类.
 *
 * @author Wang_Wong  created on  2022-04-22
 */
@Slf4j
@RequiredArgsConstructor
public class WxCpTpOaWeDocServiceImpl implements WxCpTpOaWeDocService {
  private final WxCpTpService mainService;

  @Override
  public WxCpDocCreateData docCreate(@NonNull WxCpDocCreateRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(WEDOC_CREATE_DOC);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpDocCreateData.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp docRename(@NonNull WxCpDocRenameRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(WEDOC_RENAME_DOC);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp docDelete(String docId, String formId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(WEDOC_DEL_DOC);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("docid", docId);
    jsonObject.addProperty("formid", formId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpDocInfo docInfo(@NonNull String docId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(WEDOC_GET_DOC_BASE_INFO);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("docid", docId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpDocInfo.fromJson(responseContent);
  }

  @Override
  public WxCpDocShare docShare(@NonNull String docId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(WEDOC_DOC_SHARE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("docid", docId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpDocShare.fromJson(responseContent);
  }
}
