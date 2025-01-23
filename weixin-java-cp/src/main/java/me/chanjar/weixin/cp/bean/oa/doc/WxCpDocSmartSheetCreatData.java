package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 智能表格添加/修改子表返回数据.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocSmartSheetCreatData extends WxCpBaseResp implements Serializable {
  private static final long serialVersionUID = -5028321623142879581L;

  /**
   * 智能表属性
   */
  @SerializedName("properties")
  private Object properties;

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocSmartSheetCreatData fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocSmartSheetCreatData.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
