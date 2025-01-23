package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取文档数据.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocData extends WxCpBaseResp implements Serializable {
  private static final long serialVersionUID = -5028321623142879581L;

  /**
   * 文档版本
   */
  @SerializedName("version")
  private Integer version;

  /**
   * 文档内容根节点
   */
  @SerializedName("document")
  private Object document;

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocData fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocData.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
