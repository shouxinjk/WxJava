package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取会话记录请求对象
 * https://developer.work.weixin.qq.com/document/path/97240
 * 1.消息发送者或者接收者必须在会话存档接口权限授权范围以及“会话内容存档”配置的“企业版”或者“服务版”中（金融行业为“基础版”跟“企业版”）
 * 2. 只可获取5天内的企业客户会话记录数据
 * 3. 一次拉取调用上限1000条会话记录，可以通过分页拉取的方式来依次拉取
 * @author Gorgeous
 */
@Data
public class WxCpChatDataSyncMsg implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 上一次调用时返回的next_cursor，第一次拉取可以不填
   */
  @SerializedName("cursor")
  private String cursor;

  /**
   * 回调事件返回的 token 字段，10分钟内有效；建议都从回调事件中取出token填上，
   * 否则接口会有严格的频率限制。不多于128字节
   */
  @SerializedName("token")
  private String token;

  /**
   * 本次查询返回的最大条数。不超过1000，默认200条
   */
  @SerializedName("limit")
  private Integer limit;

  public static WxCpChatDataSyncMsg fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataSyncMsg.class);
  }

  /**
   * To json string.
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
