package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 智能表格添加/修改子表.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocSmartSheetCreatRequest extends WxCpBaseResp implements Serializable {
  private static final long serialVersionUID = -5028321623142879581L;

  /**
   * 文档的docid
   */
  @SerializedName("docid")
  private String docId;

  /**
   * 智能表属性
   */
  @SerializedName("properties")
  private Object properties;
//  properties.sheet_id	string	智能表 ID，创建子表时生成的 6 位随机 ID
//  properties.title	string	智能表标题
//  properties.index	int32	智能表下标

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocSmartSheetCreatRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocSmartSheetCreatRequest.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
