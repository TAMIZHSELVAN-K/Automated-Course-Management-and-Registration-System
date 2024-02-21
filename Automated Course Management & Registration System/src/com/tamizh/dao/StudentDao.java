package com.tamizh.dao;

import java.util.List;
                                

import com.tamizh.Exceptions.CourseException;
import com.tamizh.Exceptions.StudentException;
import com.tamizh.model.CourseDTO;
import com.tamizh.model.Student;

public interface StudentDao {

	public String studentRegistration(Student student) throws StudentException;
	
	public String login(String username , String password) throws StudentException;
	
	public String updateDetails(int roll, String field, String Data) throws StudentException;
	
	public List<CourseDTO> availableCourseSeat() throws CourseException;

}
