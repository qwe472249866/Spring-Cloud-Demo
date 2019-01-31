package com.zp.demo.util;

import com.zp.demo.models.Api;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author zp
 * @Title: ApiUtil
 * @Description: TODO
 * @date 2019/1/3113:54
 */
public class ApiUtil {



    public static  String genHash(Api api) throws UnsupportedEncodingException {
        String path_method = api.getPath() + api.getMethod();
        final String hash = DigestUtils.md5DigestAsHex(path_method.getBytes("UTF-8"));
        return hash;
    }

}
