package me.chanjar.weixin.cp.tp.service;

import lombok.NonNull;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.oa.doc.*;

/**
 * 企业微信文档相关接口.
 * <a href="https://developer.work.weixin.qq.com/document/path/97392">文档</a>
 *
 * @author Hugo
 */
public interface WxCpTpOaWeDocService {

  /**
   * 新建文档
   * 该接口用于新建文档和表格，新建收集表可前往 收集表管理 查看。
   * <p>
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/create_doc?access_token=ACCESS_TOKEN
   *
   * @param request 新建文档对应请求参数
   * @param corpId 企业id
   * @return url：新建文档的访问链接，docid：新建文档的docid
   * @throws WxErrorException the wx error exception
   */
  WxCpDocCreateData docCreate(@NonNull WxCpDocCreateRequest request, String corpId) throws WxErrorException;

  /**
   * 重命名文档/收集表
   * 该接口用于对指定文档/收集表进行重命名。
   * <p>
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/rename_doc?access_token=ACCESS_TOKEN
   *
   * @param request 重命名文档/收集表
   * @param corpId 企业id
   * @return wx cp base resp
   * @throws WxErrorException the wx error exception
   */
  WxCpBaseResp docRename(@NonNull WxCpDocRenameRequest request, String corpId) throws WxErrorException;

  /**
   * 删除文档/收集表
   * 该接口用于删除指定文档/收集表。
   * <p>
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/del_doc?access_token=ACCESS_TOKEN
   *
   * @param docId  文档docid（docid、formid只能填其中一个）
   * @param formId 收集表id（docid、formid只能填其中一个）
   * @param corpId 企业id
   * @return wx cp base resp
   * @throws WxErrorException the wx error exception
   */
  WxCpBaseResp docDelete(String docId, String formId, String corpId) throws WxErrorException;

  /**
   * 获取文档基础信息
   * 该接口用于获取指定文档的基础信息。
   * <p>
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/get_doc_base_info?access_token=ACCESS_TOKEN
   *
   * @param docId 文档docid
   * @param corpId 企业id
   * @return wx cp doc info
   * @throws WxErrorException the wx error exception
   */
  WxCpDocInfo docInfo(@NonNull String docId, String corpId) throws WxErrorException;

  /**
   * 分享文档
   * 该接口用于获取文档的分享链接。
   * <p>
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/doc_share?access_token=ACCESS_TOKEN
   *
   * @param docId 文档docid
   * @param corpId 企业id
   * @return url 文档分享链接
   * @throws WxErrorException the wx error exception
   */
  WxCpDocShare docShare(@NonNull String docId, String corpId) throws WxErrorException;

  /**
   * 获取文档数据
   * 该接口用于获取文档数据
   * 请求方式：POST（HTTPS）
   * 请求地址: https://qyapi.weixin.qq.com/cgi-bin/wedoc/document/get?access_token=ACCESS_TOKEN
   * @param docId
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpDocData getDocData(@NonNull String docId, @NonNull String corpId) throws WxErrorException;

  /**
   * 智能表格添加子表
   * 本接口用于在表格的某个位置添加一个智能表，该智能表不存在视图、记录和字段，可以使用 API 在该智能表中添加视图、记录和字段。
   * 请求方式：POST(HTTPS)
   * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/wedoc/smartsheet/add_sheet?access_token=ACCESS_TOKEN
   * @param request
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpDocSmartSheetCreatData addSheet(WxCpDocSmartSheetCreatRequest request, @NonNull String corpId) throws WxErrorException;

  /**
   * 智能表格修改子表
   * 本接口用于修改表格中某个子表的标题。
   * 请求方式：POST(HTTPS)
   * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/wedoc/smartsheet/update_sheet?access_token=ACCESS_TOKEN
   * @param request
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpDocSmartSheetCreatData updateSheet(WxCpDocSmartSheetCreatRequest request, @NonNull String corpId) throws WxErrorException;

  /**
   * 智能表格添加字段
   * 本接口用于在智能表中的某个子表里添加一列或多列新字段。单表最多允许有150个字段。。
   * 请求方式：POST(HTTPS)
   * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/wedoc/smartsheet/add_fields?access_token=ACCESS_TOKEN
   * @param request
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpDocSmartSheetCreateFieldsData addFields(WxCpDocSmartSheetCreateFieldsRequest request, @NonNull String corpId) throws WxErrorException;

  /**
   * 智能表格更新字段
   * 本接口用于更新智能中的某个子表里的一个或多个字段的标题和字段属性信息。
   * 请求方式：POST(HTTPS)
   * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/wedoc/smartsheet/update_fields?access_token=ACCESS_TOKEN
   * @param request
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpDocSmartSheetCreateFieldsData updateFields(WxCpDocSmartSheetCreateFieldsRequest request, @NonNull String corpId) throws WxErrorException;

  /**
   * 智能表格删除字段
   * 本接口用于删除智能表中的某个子表里的一列或多列字段。
   * 请求方式：POST(HTTPS)
   * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/wedoc/smartsheet/delete_fields?access_token=ACCESS_TOKEN
   * @param request
   * @param corpId
   * @return
   * @throws WxErrorException
   */
  WxCpBaseResp delFields(WxCpDocSmartSheetDelFieldsRequest request, @NonNull String corpId) throws WxErrorException;
}
