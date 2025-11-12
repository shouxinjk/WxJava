package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取会话记录返回对象
 * https://developer.work.weixin.qq.com/document/path/97240
 * @author Gorgeous
 */
@Data
public class WxCpChatDataMsgList implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 错误码
   */
  @SerializedName("errcode")
  private Integer errcode;

  /**
   * 错误码说明
   */
  @SerializedName("errmsg")
  private String errmsg;

  /**
   * 下一次查询时使用，将值填到请求包的cursor字段中
   */
  @SerializedName("next_cursor")
  private String nextCursor;

  /**
   * 是否还有更多未拉取的数据，1：是；0：否
   */
  @SerializedName("has_more")
  private Integer hasMore;

  /**
   * 消息列表，按消息发送时间升序排序
   */
  @SerializedName("msg_list")
  private List<Object> msgList;

  public static WxCpChatDataMsgList fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataMsgList.class);
  }

  /**
   * To json string.
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
