package me.chanjar.weixin.cp.bean.external;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpConclusionAdapter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 「联系我」列表查询对象
 *
 * @author element
 */
@Data
@NoArgsConstructor
public class WxCpContactWayListRequest implements Serializable {
  private static final long serialVersionUID = -8697184659526210472L;

  /**
   * <pre>
   * 非必填
   * 「联系我」创建起始时间戳, 默认为90天前
   * </pre>
   */
  @SerializedName("start_time")
  private Long startTime;

  /**
   * <pre>
   * 非必填
   * 「联系我」创建结束时间戳, 默认为当前时间
   * </pre>
   */
  @SerializedName("end_time")
  private Long endTime;

  /**
   * <pre>
   * 非必填
   * 分页查询使用的游标，为上次请求返回的 next_cursor
   * </pre>
   */
  @SerializedName("cursor")
  private String cursor;

  /**
   * <pre>
   * 非必填
   * 每次查询的分页大小，默认为100条，最多支持1000条
   * </pre>
   */
  @SerializedName("limit")
  private Integer limit;
  
  /**
   * From json wx cp contact way info.
   *
   * @param json the json
   * @return the wx cp contact way info
   */
  public static WxCpContactWayListRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpContactWayListRequest.class);
  }

  /**
   * To json string.
   *
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
