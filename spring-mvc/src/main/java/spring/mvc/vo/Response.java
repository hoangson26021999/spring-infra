package spring.mvc.vo;


import lombok.*;
import lombok.experimental.Accessors;
import spring.mvc.util.Const;

import java.io.Serializable;
import java.time.Instant;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Response<T> implements Serializable {
    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    @Getter
    private long ts = Instant.now().toEpochMilli() / 1000;


    public static <T> Response<T> ok() {
        return restResult(null, Const.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data) {
        return restResult(data, Const.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data, String msg) {
        return restResult(data, Const.SUCCESS, msg);
    }

    public static <T> Response<T> ok(String msg) {
        return restResult(null, Const.SUCCESS, msg);
    }

    public static <T> Response<T> failed() {
        return restResult(null, Const.FAIL, null);
    }

    public static <T> Response<T> failed(String msg) {
        return restResult(null, Const.FAIL, msg);
    }

    public static <T> Response<T> failed(Integer code, T data, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> Response<T> failed(Integer code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Response<T> failed(T data) {
        return restResult(data, Const.FAIL, null);
    }

    public static <T> Response<T> failed(T data, String msg) {
        return restResult(data, Const.FAIL, msg);
    }

    public static <T> Response<T> ok(Integer code, T data, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> Response<T> ok(Integer code, T data) {
        return restResult(data, code, null);
    }

    public static <T> Response<T> ok(Integer code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> Response<T> restResult(T data, int code, String msg) {
        Response<T> apiResult = new Response<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
