package com.rp.studentws.soap.services;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.CUSTOM, customFaultCode="{ http://rptech.com/course}100 Course not found !!!" )
public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public CourseNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CourseNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CourseNotFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	

}
