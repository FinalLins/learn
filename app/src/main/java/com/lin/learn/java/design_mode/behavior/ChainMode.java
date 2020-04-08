package com.lin.learn.java.design_mode.behavior;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式
 * 一个请求发送到接收者，接收者成连式结构，沿着链式结构传递请求，直到有对象处理请求。
 */
public class ChainMode {
    public interface HttpCallback {
        void success(String response);

        void fail(int code, String error);
    }

    public static class Http {

        private List<Interceptor> interceptorList;

        public Http() {
            interceptorList = new ArrayList<>();
            //真正请求的拦截器
            interceptorList.add(new HttpRequestInterceptor());
        }

        public void addInterceptor(Interceptor interceptor) {
            this.interceptorList.add(0, interceptor);
        }

        public void get(String url, HttpCallback callback) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Response response = new Chain(new Request(url), interceptorList).call();
            if (response.getCode() == Response.SUCCESS) {
                if (callback != null) callback.success(response.getData());
            } else {
                if (callback != null) callback.fail(response.getCode(), response.getMsg());
            }
        }

    }

    public static class Request {
        private String url;

        public Request(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class Response {
        public static final int SUCCESS = 1;
        public static final int FAIL = 0;

        private int code;
        private String msg;
        private String data;

        public Response(int code, String msg, String data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public String getData() {
            return data;
        }

        @Override
        public String toString() {
            return "code : " + code + ", msg : " + msg + " , data: " + data;
        }

        public static Response buildSuccess(String data) {
            return new Response(SUCCESS, "成功", data);
        }

        public static Response buildFail() {
            return new Response(FAIL, "失败", null);
        }

        public static Response buildFail(String msg) {
            return new Response(FAIL, msg, null);
        }
    }

    public static class Chain {
        private Request request;
        private List<Interceptor> interceptorList;
        private int index;

        public Chain(Request request, List<Interceptor> interceptorList) {
            this.request = request;
            this.interceptorList = interceptorList;
        }

        public Interceptor next() {
            if (index >= this.interceptorList.size()) {
                return null;
            }
            return this.interceptorList.get(index++);
        }

        public Request getRequest() {
            return request;
        }

        public Response call() {
            return next().intercept(this);
        }
    }

    public interface Interceptor {
        Response intercept(Chain chain);
    }

    public static class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) {
            Request request = chain.getRequest();
            System.out.println("=======================");
            System.out.println("request url : " + request.getUrl());
            Interceptor interceptor = chain.next();
            Response response;
            if (interceptor == null) {
                response = Response.buildFail("逻辑异常");
            } else {
                response = interceptor.intercept(chain);
            }
            System.out.println("response :" + response.toString());
            System.out.println("=======================");
            System.out.println();
            return response;
        }
    }

    public static class NetInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) {
            if (NetUtil.isNetWork()) {
                Interceptor interceptor = chain.next();
                if (interceptor == null) {
                    return Response.buildFail("逻辑异常");
                }
                return interceptor.intercept(chain);
            }
            return Response.buildFail("网络异常");

        }
    }

    public static class HttpRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) {
            //http 请求过程省略
            //...
            if ((System.currentTimeMillis() & 1) == 0) {
                return Response.buildSuccess("数据");
            }
            return Response.buildFail("服务器异常");
        }
    }

    public static class NetUtil {
        public static boolean isNetWork() {
            return (System.currentTimeMillis() & 1) == 0;
        }
    }

    public static void test() {
        Http http = new Http();

        http.addInterceptor(new NetInterceptor());  //网络判断拦截器
        http.addInterceptor(new LogInterceptor());  //日志拦截器
        http.get("192.168.0.88:8080/getUsers", new HttpCallback() {
            @Override
            public void success(String response) {
                System.out.println("请求成功");
            }

            @Override
            public void fail(int code, String error) {
                System.out.println("请求失败");
            }
        });
    }
}
