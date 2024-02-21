package com.tamizh.dao;

import java.util.List;

import com.tamizh.Exceptions.AdminException;
import com.tamizh.Exceptions.BatchException;
import com.tamizh.Exceptions.CourseException;
import com.tamizh.Exceptions.StudentException;
import com.tamizh.model.Admin;
import com.tamizh.model.Batch;
import com.tamizh.model.Course;
import com.tamizh.model.StudentBatch;
import com.tamizh.model.StudentDTO;

public interface AdminDao {

public String adminRegistration(Admin admin) throws AdminException;

public String login(String username , String password) throws AdminException;

public String addCourse(Course course) throws CourseException;

public String updateFees(int cid, int fee) throws CourseException;

public String deletCourse(int cid) throws CourseException;

public Course searchInfoCourse(String cname) throws CourseException;

public String createBatch(Batch batch) throws BatchException;

public String allocateStudent(StudentBatch sb) throws BatchException;

public String updateSeat(int bid,int value) throws BatchException;

public List<StudentDTO> viewStudentOfBatch() throws StudentException;



}
