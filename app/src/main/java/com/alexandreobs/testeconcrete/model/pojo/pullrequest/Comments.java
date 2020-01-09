
package com.alexandreobs.testeconcrete.model.pojo.pullrequest;

import com.google.gson.annotations.Expose;


@SuppressWarnings("unused")
public class Comments {

    @Expose
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
