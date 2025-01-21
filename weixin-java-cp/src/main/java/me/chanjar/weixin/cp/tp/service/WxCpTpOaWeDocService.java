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
}
