package me.chanjar.weixin.cp.bean.oa.doc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 智能表格查询字段字段请求.
 *
 * @author Wang_Wong
 */
@Data
public class WxCpDocSmartSheetGetFieldsRequest extends WxCpBaseResp implements Serializable {
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
   * 偏移量，初始值为 0
   */
  @SerializedName("offset")
  private Integer offset;

  /**
   * 分页大小 , 每页返回多少条数据；当不填写该参数或将该参数设置为 0 时，
   * 如果总数大于 1000，一次性返回 1000 个字段，当总数小于 1000 时，返回全部字段；
   * limit 最大值为 1000
   */
  @SerializedName("limit")
  private Integer limit;

  /**
   * From json wx cp space info.
   *
   * @param json the json
   * @return the wx cp space info
   */
  public static WxCpDocSmartSheetGetFieldsRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDocSmartSheetGetFieldsRequest.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
