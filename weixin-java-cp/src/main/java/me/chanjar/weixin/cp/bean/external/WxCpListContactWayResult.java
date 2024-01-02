package me.chanjar.weixin.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.external.moment.MomentInfo;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

/**
 * 企业「联系我」列表
 *
 * @author leiin  created on  2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxCpListContactWayResult extends WxCpBaseResp {
  private static final long serialVersionUID = 106159971765109008L;

  @SerializedName("next_cursor")
  private String nextCursor;
  @SerializedName("contact_way")
  private List<Item> contactWay;
  
  @Getter
  @Setter
  public static class Item {

    /***
     * configId
     */
    @SerializedName("config_id")
    private String configId;
  }
  
  /**
   * From json wx cp get moment list.
   *
   * @param json the json
   * @return the wx cp get moment list
   */
  public static WxCpListContactWayResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpListContactWayResult.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }
}
