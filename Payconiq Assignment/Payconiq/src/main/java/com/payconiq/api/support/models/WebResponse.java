package com.payconiq.api.support.models;

public class WebResponse {

  private String responseType;
  private int responseCode;
  private String currentBody;

  //Setters
  public void setResponseCode(int responseCode){
    this.responseCode = responseCode;
  }

  public void setResponseType(String responseType){
    this.responseType = responseType;
  }

  public void setCurrentBody(String body){
    this.currentBody = body;
  }


  //Getters
  public int getResponseCode(){
    return this.responseCode;
  }

  public String getResponseType(){
    return this.responseType;
  }

  public String getCurrentBody(){
    return this.currentBody;
  }
}
