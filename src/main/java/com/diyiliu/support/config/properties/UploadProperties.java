package com.diyiliu.support.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * Description: UploadProperties
 * Author: DIYILIU
 * Update: 2018-05-16 17:05
 */

@ConfigurationProperties("upload")
public class UploadProperties {

    /** 上传图片路径 */
    private Resource imagePath;

    /** 未知图片默认路径 */
    private Resource unknownImg;

    public Resource getImagePath() {
        return imagePath;
    }

    public void setImagePath(Resource imagePath) {
        this.imagePath = imagePath;
    }

    public Resource getUnknownImg() {
        return unknownImg;
    }

    public void setUnknownImg(Resource unknownImg) {
        this.unknownImg = unknownImg;
    }
}
