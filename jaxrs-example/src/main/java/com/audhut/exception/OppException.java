package com.audhut.exception;


public class OppException extends Exception {

	ErrorObj errObj;
	
	public OppException(String errCode, String errMsg){
		errObj = new ErrorObj();
		errObj.setErrCode(errCode);
		errObj.setErrMsg(errMsg);
		
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	

	public ErrorObj getErrorObject() {
		
		return errObj;
	}
	

}
