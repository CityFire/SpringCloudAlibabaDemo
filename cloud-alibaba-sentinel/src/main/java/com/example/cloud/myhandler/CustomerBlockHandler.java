package com.example.cloud.myhandler;

import com.sun.deploy.security.BlockedException;

public class CustomerBlockHandler {
    public static String handlerException(BlockedException exception) {
        return "4444按客户自定义，global handlerException-----1";
    }

    public static String handlerException2(BlockedException exception) {
        return "4444按客户自定义，global handlerException-----2";
    }
}
