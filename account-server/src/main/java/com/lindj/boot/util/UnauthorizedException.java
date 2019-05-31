package com.lindj.boot.util;

/**
 * @author lindj
 * @date 2019/5/29 0029
 * @description
 */
public class UnauthorizedException extends RuntimeException {


    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
