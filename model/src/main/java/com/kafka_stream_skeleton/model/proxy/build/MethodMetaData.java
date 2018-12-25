package com.kafka_stream_skeleton.model.proxy.build;

/**
 * Created by Ronis on 4/2/2017.
 */
public class MethodMetaData {
    private MethodType type;
    private boolean isAnonymous;

    public  MethodMetaData(){}
    public  MethodMetaData(MethodType type, boolean isAnonimus){
        this.type = type;
        this.isAnonymous =isAnonimus;
    }

    public void setType(MethodType type){this.type =type;}
    public MethodType getType(){return type;}
    public void setAnonymous(boolean isAnonymous){this.isAnonymous =isAnonymous;}
    public boolean isAnonymous(){return isAnonymous;}


}
