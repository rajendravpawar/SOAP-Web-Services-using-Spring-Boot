package com.rp.studentws.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.rp.studentws.soap.bean.Course;
import com.rp.studentws.soap.bean.CourseStatus;
import com.rp.studentws.soap.services.CourseNotFoundException;
import com.rp.studentws.soap.services.CourseService;
import com.rptech.course.CourseDetails;
import com.rptech.course.DeleteCourseRequest;
import com.rptech.course.DeleteCourseResponse;
import com.rptech.course.GetAllCourseDetailsResponse;
import com.rptech.course.GetCourseDetailsRequest;
import com.rptech.course.GetCourseDetailsResponse;
import com.rptech.course.Status;

@Endpoint
public class CourseDetailsEndpoint {


	private static final String ENDPINT_GET_COURSE_DETAILS = "GetCourseDetailsRequest";
	private static final String ENDPINT_GET_ALL_COURSE_DETAILS = "GetAllCourseDetailsRequest";
	public static final String WEBSERVICE_NAMESPACE = "http://rptech.com/course";
	private static final String ENDPINT_DELETE_COURSE_DETAILS = "DeleteCourseRequest";


	@Autowired
	CourseService courseService;


	@PayloadRoot (namespace=WEBSERVICE_NAMESPACE, localPart = ENDPINT_GET_COURSE_DETAILS )

	@ResponsePayload
	public GetCourseDetailsResponse getCourseDetails(@RequestPayload GetCourseDetailsRequest courseDetailsRequest)
	{
		GetCourseDetailsResponse courseDetailsResponse =  new GetCourseDetailsResponse();
		Course course = courseService.findCourseById(courseDetailsRequest.getId());
		if(course == null)
			throw new CourseNotFoundException(courseDetailsRequest.getId() +" Couse Not found !!!");
		
		CourseDetails courseDetails = mapCourse(course);			
		courseDetailsResponse.setCourseDetails(courseDetails);
		return courseDetailsResponse;

	}

	@PayloadRoot(namespace = WEBSERVICE_NAMESPACE, localPart = ENDPINT_GET_ALL_COURSE_DETAILS )
	@ResponsePayload
	public GetAllCourseDetailsResponse getAllCoursesDetails()
	{
		GetAllCourseDetailsResponse allCourseDetailsResponse = new GetAllCourseDetailsResponse();
		List<Course> allCourses = courseService.getAllCourses();
		for (Course course : allCourses) {
			CourseDetails mapCourse = mapCourse(course);
			allCourseDetailsResponse.getCourseDetails().add(mapCourse);
		}
		return allCourseDetailsResponse;
	}


	@PayloadRoot(namespace = WEBSERVICE_NAMESPACE , localPart = ENDPINT_DELETE_COURSE_DETAILS )
	@ResponsePayload
	public DeleteCourseResponse deletCourse( @RequestPayload DeleteCourseRequest deleteCourseRequest)
	{
		CourseStatus courseStatus = courseService.deleteCourse(deleteCourseRequest.getId());
		DeleteCourseResponse deleteCourseResponse = new DeleteCourseResponse();
		deleteCourseResponse.setStatus(mapStatus(courseStatus));	
		return deleteCourseResponse;
	}


	private Status mapStatus(CourseStatus courseStatus) {

		if(courseStatus == CourseStatus.SUCCESS)
			return Status.SUCCESS;

		else
			return Status.FAILED;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}


}
