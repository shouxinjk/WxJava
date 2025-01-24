package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 查询智能表格记录返回值.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WxCpDocGetRecordsData implements Serializable {
  private static final long serialVersionUID = -4960239393895454138L;

  /**
   * 符合筛选条件的视图总数
   */
  @SerializedName("total")
  private Integer total;

  /**
   * Smartsheet 是否还有更多项
   */
  @SerializedName("has_more")
  private boolean hasMore;

  /**
   * 下次下一个搜索结果的偏移量
   */
  @SerializedName("next")
  private Integer next;

  /**
   * 由查询记录的具体内容组成的 JSON 数组
   */
  @SerializedName("records")
  private Object[] records;

  /**
   * From json wx cp space create request.
   *
   * @param json the json
   * @return the wx cp space create request
   */
  public static WxCpDocGetRecordsData fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocGetRecordsData.class);
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
