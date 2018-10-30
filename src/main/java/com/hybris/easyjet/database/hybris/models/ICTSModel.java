package com.hybris.easyjet.database.hybris.models;


import lombok.Data;

/**
 * Created by kikekolade on 30/08/2018.
 */
@Data
public class ICTSModel {

    private String hjmpTS;
    private String createdTS;
    private String modifiedTS;
    private String TypePkString;
    private String OwnerPkString;
    private String PK;
    private String p_messagecode;
    private String p_messagestatuscode;
    private Boolean p_enableforad;
    private String p_messagetext;
    private String p_messagecolorstatus;
    private String aCLTS;
    private String propTS;

    public ICTSModel(String p_messagecolorstatus, String p_messagetext){

        this.p_messagecolorstatus = p_messagecolorstatus;
        this.p_messagetext = p_messagetext;
    }
}
