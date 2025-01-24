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
 * 查询智能表格记录请求.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WxCpDocGetRecordsRequest implements Serializable {
  private static final long serialVersionUID = -4960239393895454138L;

  /**
   * 文档ID
   */
  @SerializedName("docid")
  private String docId;

  /**
   * Smartsheet 子表ID
   */
  @SerializedName("sheet_id")
  private String sheetId;

  /**
   * 视图 ID
   */
  @SerializedName("view_id")
  private String viewId;

  /**
   * 由记录 ID 组成的 JSON 数组
   */
  @SerializedName("record_ids")
  private String[] recordIds;

  /**
   * 返回记录中单元格的key类型
   */
  @SerializedName("key_type")
  private String keyType;

  /**
   * 返回指定列，由字段标题组成的 JSON 数组 ，
   * key_type 为 CELL_VALUE_KEY_TYPE_FIELD_TITLE 时有效
   */
  @SerializedName("field_titles")
  private String[] fieldTitles;

  /**
   * 返回指定列，由字段 ID 组成的 JSON 数组 ，
   * key_type 为 CELL_VALUE_KEY_TYPE_FIELD_ID 时有效
   */
  @SerializedName("field_ids")
  private String[] fieldIds;

  /**
   * 对返回记录进行排序
   */
  @SerializedName("sort")
  private Object[] sort;

  /**
   * 偏移量，初始值为 0
   */
  @SerializedName("offset")
  private Integer offset;

  /**
   * 分页大小 , 每页返回多少条数据；当不填写该参数或将该参数设置为 0 时，
   * 如果总数大于 1000，一次性返回 1000 行记录，当总数小于 1000 时，返回全部记录；
   * limit 最大值为 1000
   */
  @SerializedName("limit")
  private Integer limit;

  /**
   * From json wx cp space create request.
   *
   * @param json the json
   * @return the wx cp space create request
   */
  public static WxCpDocGetRecordsRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocGetRecordsRequest.class);
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
