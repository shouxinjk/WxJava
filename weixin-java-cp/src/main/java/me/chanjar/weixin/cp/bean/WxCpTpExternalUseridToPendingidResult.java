package me.chanjar.weixin.cp.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

@Getter
@Setter
public class WxCpTpExternalUseridToPendingidResult extends WxCpBaseResp {
  private static final long serialVersionUID = -615358914415497369L;

  @SerializedName("result")
  private List<Item> result;
  
  @Getter
  @Setter
  public static class Item {

    /***
     * external Userid
     */
    @SerializedName("external_userid")
    private String externalUserid;

    /**
     * pengdingId
     */
    @SerializedName("pending_id")
    private String pendingId;
  }


  public static WxCpTpExternalUseridToPendingidResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpExternalUseridToPendingidResult.class);
  }


}
