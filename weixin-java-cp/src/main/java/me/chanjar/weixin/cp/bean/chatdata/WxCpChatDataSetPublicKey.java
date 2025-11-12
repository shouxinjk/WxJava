package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 设置公钥请求对象
 * https://developer.work.weixin.qq.com/document/path/99587
 * @author Gorgeous
 */
@Data
public class WxCpChatDataSetPublicKey implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 开发者为该企业生成的公钥
   */
  @SerializedName("public_key")
  private String publicKey;

  /**
   * 公钥对应的版本号，当重复调用该接口更换公钥时要求比旧公钥版本号大
   */
  @SerializedName("public_key_ver")
  private String publicKeyVer;

  public static WxCpChatDataSetPublicKey fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataSetPublicKey.class);
  }

  /**
   * To json string.
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
