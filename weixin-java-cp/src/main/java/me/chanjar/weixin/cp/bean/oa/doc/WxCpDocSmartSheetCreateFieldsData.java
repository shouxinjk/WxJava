package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 智能表格添加/更新字段返回数据.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocSmartSheetCreateFieldsData extends WxCpBaseResp implements Serializable {
  private static final long serialVersionUID = -5028321623142879581L;

  /**
   * 字段详情
   *  "fields": [{
   * 		"field_id": "FIELDID",
   * 		"field_title": "TITLE",
   * 		"field_type": "FIELD_TYPE_TEXT"
   *   }]
   */
  @SerializedName("fields")
  private Object[] fields;

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocSmartSheetCreateFieldsData fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocSmartSheetCreateFieldsData.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
