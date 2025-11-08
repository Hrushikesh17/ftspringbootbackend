package com.hkp.freetre.ExceptionHandle;

import java.time.LocalDateTime;

public class ErrorDetails {
	  private int statusCode;
	    private LocalDateTime timeStamp;
	    private String errMessage;
	    private String status;
	    
	    public ErrorDetails() {
	    	
	    }
		public ErrorDetails(int statusCode, LocalDateTime timeStamp, String errMessage, String status) {
			super();
			this.statusCode = statusCode;
			this.timeStamp = timeStamp;
			this.errMessage = errMessage;
			this.status = status;
		}
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		public LocalDateTime getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(LocalDateTime timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getErrMessage() {
			return errMessage;
		}
		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}



}
