package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 智能表格删除字段请求.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocSmartSheetDelFieldsRequest extends WxCpBaseResp implements Serializable {
  private static final long serialVersionUID = -5028321623142879581L;

  /**
   * 文档的docid
   */
  @SerializedName("docid")
  private String docId;

  /**
   * 表格ID
   */
  @SerializedName("sheet_id")
  private String sheetId;

  /**
   * 需要删除的字段id列表
   */
  @SerializedName("field_ids")
  private String[] fieldIds;

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocSmartSheetDelFieldsRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocSmartSheetDelFieldsRequest.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
