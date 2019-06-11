package com.bgm.getlink.model;

public class PostLinkResult extends BaseRetData{
    private Result result;

    public PostLinkResult() {
        result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PostLinkResult{" +
                "result=" + result +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
