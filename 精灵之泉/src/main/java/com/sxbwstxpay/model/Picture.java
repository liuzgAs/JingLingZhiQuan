package com.sxbwstxpay.model;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * Created by zhangjiebo on 2018/2/3/003.
 *
 * @author ZhangJieBo
 */

public class Picture {
    private int type;
    private LocalMedia localMedia;

    public Picture(int type, LocalMedia localMedia) {
        this.type = type;
        this.localMedia = localMedia;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalMedia getLocalMedia() {
        return localMedia;
    }

    public void setLocalMedia(LocalMedia localMedia) {
        this.localMedia = localMedia;
    }
}
