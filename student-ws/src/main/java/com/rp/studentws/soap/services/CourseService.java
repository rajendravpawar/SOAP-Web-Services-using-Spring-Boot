package com.rp.studentws.soap.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rp.studentws.soap.bean.Course;
import com.rp.studentws.soap.bean.CourseStatus;

@Component
public class CourseService {

	private static List<Course> courses = new ArrayList<>();

	static
	{
		courses.add(new Course(1, "Course 1", "Course Description"));
		courses.add(new Course(2, "Course 2", "Course Description"));
		courses.add(new Course(3, "Course 3", "Course Description"));
		courses.add(new Course(4, "Course 4", "Course Description"));
		courses.add(new Course(5, "Course 5", "Course Description"));
		courses.add(new Course(6, "Course 6", "Course Description"));

	}

	public Course findCourseById(int id)
	{
		Course course =  null;

		for(Course courseObj : courses)
		{
			if( courseObj.getId() == id )
			{
				course = courseObj;
			}
		}
		return course;
	}


	public List<Course> getAllCourses()
	{
		return courses;
	}

	public CourseStatus deleteCourse(int id)
	{
		Iterator<Course> iterator = courses.iterator();
		CourseStatus status = CourseStatus.FAILED;
		while ( iterator.hasNext() )
		{
			Course course = iterator.next();
			if( course.getId() == id) {
				iterator.remove();
				status = CourseStatus.SUCCESS;
			}
		}
		return status;
	}
}
