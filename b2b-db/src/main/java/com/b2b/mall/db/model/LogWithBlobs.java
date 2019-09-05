package com.b2b.mall.db.model;

public class LogWithBlobs extends Log {
    private String operation;

    private String method;

    private String params;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Override
    public String toString() {
        return "LogWithBlobs{" +
                "operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}