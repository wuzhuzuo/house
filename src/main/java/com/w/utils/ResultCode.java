package com.w.utils;


public enum ResultCode {

    //处理成功
    SUCCESS(200, "success", true),


    //处理失败
    FAILED(500, "fail", false),

    /*  //未登录
      NOT_LOGIN(401, "not login"),

      //未激活
      NOT_ACTIVED(402, "account not actived"),

      //访问拒绝
      ACCESS_DENIED(403, "Access denied"),

      //数据库错误
      DB_ERROR(503, "Error querying database"),
  */
    //参数错误
    PARAM_PARAMETER_ERROR(501, "Parameter error", true),

   /* //参数为空
    PARAM_PARAMETER_IS_NULL(502, "Parameter is null"),

    ERROR_WECHAT(465, "微信请求失败"),*/;

    private int code;

    private String message;
    private boolean success;

    ResultCode(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String message) {
        return String.format(this.message, message == null ? "" : message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess(boolean success) {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
