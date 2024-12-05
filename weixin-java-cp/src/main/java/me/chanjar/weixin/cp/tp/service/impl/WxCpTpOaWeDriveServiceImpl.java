package me.chanjar.weixin.cp.tp.service.impl;

import com.google.gson.JsonObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.oa.wedrive.*;
import me.chanjar.weixin.cp.tp.service.WxCpTpOaWeDriveService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;

import java.util.List;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Oa.*;

/**
 * 企业微信微盘接口实现类.
 *
 * @author Wang_Wong  created on  2022-04-22
 */
@Slf4j
@RequiredArgsConstructor
public class WxCpTpOaWeDriveServiceImpl implements WxCpTpOaWeDriveService {
  private final WxCpTpService mainService;

  @Override
  public WxCpSpaceCreateData spaceCreate(@NonNull WxCpSpaceCreateRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_CREATE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpSpaceCreateData.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp spaceRename(@NonNull WxCpSpaceRenameRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_RENAME);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp spaceDismiss(@NonNull String userId, @NonNull String spaceId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_DISMISS);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("spaceid", spaceId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpSpaceInfo spaceInfo(@NonNull String userId, @NonNull String spaceId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_INFO);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("spaceid", spaceId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpSpaceInfo.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp spaceAclAdd(@NonNull WxCpSpaceAclAddRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_ACL_ADD);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp spaceAclDel(@NonNull WxCpSpaceAclDelRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_ACL_DEL);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp spaceSetting(@NonNull WxCpSpaceSettingRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_SETTING);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpSpaceShare spaceShare(@NonNull String userId, @NonNull String spaceId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(SPACE_SHARE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("spaceid", spaceId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpSpaceShare.fromJson(responseContent);
  }

  @Override
  public WxCpFileList fileList(@NonNull WxCpFileListRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_LIST);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpFileList.fromJson(responseContent);
  }

  @Override
  public WxCpFileUpload fileUpload(@NonNull WxCpFileUploadRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_UPLOAD);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpFileUpload.fromJson(responseContent);
  }

  @Override
  public WxCpFileDownload fileDownload(String userId, @NonNull String fileId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_DOWNLOAD);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    if(userId!= null) {
      jsonObject.addProperty("userid", userId);
    }
    jsonObject.addProperty("fileid", fileId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpFileDownload.fromJson(responseContent);
  }

  @Override
  public WxCpFileRename fileRename(@NonNull String userId, @NonNull String fileId, @NonNull String newName,
                                   String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_RENAME);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("fileid", fileId);
    jsonObject.addProperty("new_name", newName);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpFileRename.fromJson(responseContent);
  }

  @Override
  public WxCpFileCreate fileCreate(String userId, @NonNull String spaceId, @NonNull String fatherId,
                                   @NonNull Integer fileType, @NonNull String fileName, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_CREATE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    if(userId!= null) {
      jsonObject.addProperty("userid", userId);
    }
    jsonObject.addProperty("spaceid", spaceId);
    jsonObject.addProperty("fatherid", fatherId);
    jsonObject.addProperty("file_type", fileType);
    jsonObject.addProperty("file_name", fileName);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpFileCreate.fromJson(responseContent);
  }

  @Override
  public WxCpFileMove fileMove(@NonNull WxCpFileMoveRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_MOVE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpFileMove.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp fileDelete(@NonNull String userId, @NonNull List<String> fileId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_DELETE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    WxCpFileDeleteRequest request = new WxCpFileDeleteRequest(userId, fileId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp fileAclAdd(@NonNull WxCpFileAclAddRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_ACL_ADD);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp fileAclDel(@NonNull WxCpFileAclDelRequest request, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_ACL_DEL);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    String responseContent = this.mainService.post(apiUrl, request.toJson());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpBaseResp fileSetting(@NonNull String userId, @NonNull String fileId, @NonNull Integer authScope,
                                  Integer auth, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_SETTING);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("fileid", fileId);
    jsonObject.addProperty("auth_scope", authScope);
    if (auth != null) {
      jsonObject.addProperty("auth", auth);
    }
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpBaseResp.fromJson(responseContent);
  }

  @Override
  public WxCpFileShare fileShare(String userId, @NonNull String fileId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_SHARE);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    if(userId != null) {
      jsonObject.addProperty("userid", userId);
    }
    jsonObject.addProperty("fileid", fileId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpFileShare.fromJson(responseContent);
  }

  @Override
  public WxCpFileInfo fileInfo(@NonNull String userId, @NonNull String fileId, String corpId) throws WxErrorException {
    String apiUrl = mainService.getWxCpTpConfigStorage().getApiUrl(FILE_INFO);
    apiUrl = apiUrl + "?access_token=" + mainService.getWxCpTpConfigStorage().getAccessToken(corpId);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    jsonObject.addProperty("fileid", fileId);
    String responseContent = this.mainService.post(apiUrl, jsonObject.toString());
    return WxCpFileInfo.fromJson(responseContent);
  }

}
