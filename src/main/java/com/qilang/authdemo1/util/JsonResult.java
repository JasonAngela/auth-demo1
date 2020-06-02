package com.qilang.authdemo1.util;

import lombok.*;

import java.io.Serializable;

/**
 * 请求处理返回内容
 *
 * @author huql
 * @date 2020-03-25
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -3287373790680030696L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息说明
     */
    private String msg;

    /**
     * 业务数据
     */
    @SuppressWarnings("squid:S1948")
    private T data;

    public static Builder builder() {
        return new Builder();
    }

    @ToString
    public static class Builder<T> {

        private int code = 200;
        private String msg = "访问成功";
        private T data;

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public JsonResult build() {
            return new JsonResult(this.code, this.msg, this.data);
        }
    }

}
