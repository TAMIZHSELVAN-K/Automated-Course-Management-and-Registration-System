package com.tamizh.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.tamizh.utility.DBUtil;

public class AdminDaoImpl implements AdminDao{

	@Override
	public String adminRegistration(Admin admin) throws AdminException {
		
		String message = "PLEASE TRY AGAIN";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("INSERT INTO admin(aname,username,password) VALUE(?,?,?)");
			
			ps.setString(1, admin.getAname());
			ps.setString(2, admin.getUsername());
			ps.setString(3, admin.getPassword());
			
			int x= ps.executeUpdate();
			
			if(x > 0) {
				
				message = "Register successfully";
			}else
				throw new AdminException("Invalid details");
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new AdminException(message);
		}
		
		return message;
	}
	
	
	

	@Override
	public String login(String username, String password) throws AdminException {
		
		String message = "Login fail";
		
			try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM admin WHERE username = ? and password = ?");
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				message = "Login successfully";
			}
			else
			
			throw new AdminException("Invaid username or password");
			
			} catch (SQLException e) {
				// TODO: handle exception
				message = e.getMessage();
			
				throw new AdminException(message);
			}
		
		return message;
	}
	
	
	

	@Override
	public String addCourse(Course course) throws CourseException {
		
		String message = "Not Added";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("INSERT INTO course VALUE(?,?,?)");
			
			ps.setInt(1, course.getCid());
			ps.setString(2, course.getCname());
			ps.setInt(3, course.getFee());
			
			int x= ps.executeUpdate();
			
			if(x > 0) {
				
				message = "Added successfully";
			}else
				throw new CourseException("NOt valid course detail");
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new CourseException(message);
		}
		
		return message;
		
	}
	
	
	
	

	@Override
	public String updateFees(int cid, int fee) throws CourseException {
		String message = "Not update " + fee + "fee";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("UPDATE course SET fee = ? WHERE cid = ?");
			
			ps.setInt(1, fee);
			ps.setInt(2, cid);
			
			int x= ps.executeUpdate();
			
			if(x > 0) {
				
				message = "Update successfully";
			}else
				throw new CourseException("Invalid ocurse id");
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new CourseException(message);
		}
		
		return message;
	}

	
	
	
	
	@Override
	public String deletCourse(int cid) throws CourseException {
		
		String message = "Not Delete";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("DELETE FROM course WHERE cid = ?");
			
			ps.setInt(1, cid);
			
			int x= ps.executeUpdate();
			
			if(x > 0) {
				
				message = "Delete Successfully";
			}else
				throw new CourseException(" Couse Id is wrong ");
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new CourseException(message);
		}
		
		return message;
	}

	
	
	
	@Override
	public Course searchInfoCourse(String cname) throws CourseException {
		
		Course course = new Course();
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM course WHERE cname = ?");
			
			ps.setString(1, cname);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				course = new Course(rs.getInt("cid"),rs.getString("cname"),rs.getInt("fee"));
			}
			else
				
				throw new CourseException("Invalid Course Name");
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			
			throw new CourseException(e.getMessage());
		}
		
		return course;
	}
	
	
	

	@Override
	public String createBatch(Batch batch) throws BatchException {


		String message = "Not Create";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM course where cid = ?");
			
			ps.setInt(1, batch.getCid());
			
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				PreparedStatement ps1= conn.prepareStatement("INSERT INTO batch(bname,bduration,bseats,cid) VALUE(?,?,?,?)");
				
				ps1.setString(1, batch.getBname());
				ps1.setInt(2, batch.getBduration());
				ps1.setInt(3, batch.getBseats());
				ps1.setInt(4, batch.getCid());
				
				int x= ps1.executeUpdate();
				
				if(x > 0) {
					
					message = "Create successfully";
				}else
					throw new BatchException("Please try again with other value");
				
			}else 
				
				throw new BatchException("Course Id is Not Found");
				
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new BatchException(message);
		}
		
		return message;
	}
	
	
	

	@Override
	public String allocateStudent(StudentBatch sb) throws BatchException{
		String message = "Not Create";
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM student WHERE roll = ?");
			
			ps.setInt(1, sb.getRoll());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String sname = rs.getString("sname");
				PreparedStatement ps1= conn.prepareStatement("SELECT * FROM course WHERE cid = ?");
				ps1.setInt(1, sb.getCid());
				
				ResultSet rs1 = ps1.executeQuery();
				
				if(rs1.next()) {
					String cname = rs1.getString("cname");
					PreparedStatement ps2= conn.prepareStatement("SELECT * FROM batch WHERE bid = ? AND cid = ?");
					
					ps2.setInt(1, sb.getBid());
					ps2.setInt(2,sb.getCid());
					
					ResultSet  rs2= ps2.executeQuery();
					
					if(rs2.next()) {
						int bseats = rs2.getInt("bseats");
						String bname = rs2.getString("bname");
						if(bseats !=0 ) {
							bseats--;
							PreparedStatement ps3 = conn.prepareStatement("UPDATE batch SET bseats = ? WHERE bid = ?");
							ps3.setInt(1, bseats);
							ps3.setInt(2, sb.getBid());
							
							ps3.executeUpdate();
							
							
							PreparedStatement ps4= conn.prepareStatement("INSERT INTO student_batch VALUES (?,?,?)");
							
							ps4.setInt(1, sb.getRoll());
							ps4.setInt(2, sb.getCid());
							ps4.setInt(3, sb.getBid());
							
							int a= ps4.executeUpdate();
							
							if(a > 0) {
								message = "Student "+ sname + " Roll No "+sb.getRoll()+" Successfully added to Batch "+ bname+ " of Course "+ cname ;
							}
						}else
							message = "Seats are full for this batch";
					}else
						throw new BatchException("batch not found");
					
				}else
					throw new BatchException("Course is Not found");
				
				
			}else 
				throw new BatchException("Student is not found");
				
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
			
			throw new BatchException(message);
		}
		
		return message;
	}
	
	
	
	

	@Override
	public String updateSeat(int bid, int value) throws BatchException {
		String message = "Not update";
		
		try (Connection conn= DBUtil.provideConnection()){
		
		PreparedStatement ps= conn.prepareStatement("UPDATE batch SET bseats = bseats + ? WHERE bid = ?");
		
		ps.setInt(1, value);
		ps.setInt(2, bid);
		
		int x = ps.executeUpdate();
		
		if(x > 0) {
			
			message = "updated successfully";
		}
		else
		
		throw new BatchException("Invalid batch id");
		
		} catch (SQLException e) {
			// TODO: handle exception
			message = e.getMessage();
		
			throw new BatchException(message);
		}
	
	return message;
	}

	
	
	
	@Override
	public List<StudentDTO> viewStudentOfBatch() throws StudentException {
		List<StudentDTO> students = new ArrayList<>();
		
		try (Connection conn= DBUtil.provideConnection()){
			
			PreparedStatement ps= conn.prepareStatement("SELECT s.roll,s.sname,s.semail,c.cid,c.cname,c.fee,b.bid,b.bname FROM student s INNER JOIN batch b INNER JOIN course c INNER JOIN student_batch sb ON c.cid = sb.cid AND b.bid = sb.bid");
					
			
			
			ResultSet rs = ps.executeQuery();
			
			boolean flag = true;
			
			while(rs.next()) {
				flag = false;
				
				StudentDTO st = new StudentDTO(rs.getInt("roll"),rs.getString("sname"),rs.getString("semail"),rs.getInt("cid"),rs.getString("cname"),rs.getInt("fee"),rs.getInt("bid"),rs.getString("bname"));
				students.add(st);
				
			}
			
			if(flag) 
				throw new StudentException("No student added to Batch");
			
			
			} catch (SQLException e) {
				// TODO: handle exception
				
			
				throw new StudentException(e.getMessage());
			}
		
		return students;
	}

	


}
