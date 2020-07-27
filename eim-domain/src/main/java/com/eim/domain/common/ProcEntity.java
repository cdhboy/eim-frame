package com.eim.domain.common;

public class ProcEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String proc;
    private Object[] objs;
    private int output;

    public String getProc() {
        return proc;
    }

    public void setProc(String proc) {
        this.proc = proc;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}
