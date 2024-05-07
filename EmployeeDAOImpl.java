package com.nt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nt.model.Employee;

@Repository("empDAO")
public class EmployeeDAOImpl implements IEmployeeDAO {
	private  static final String GET_EMPS_BY_DESGS="SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP WHERE JOB IN(?,?,?) ORDER BY JOB ";
	@Autowired
	private  DataSource  ds;
	/*@Autowired
	private JdbcTemplate template;*/

	@Override
	public List<Employee> getEmployeesByDesgs(String desg1, String desg2, String desg3) throws Exception {
		System.out.println("EmployeeDAOImpl.getEmployeesByDesgs()::: DAtaSource obj class name::"+ds.getClass());
             List<Employee> list=null;
      try(//get  pooled connectoon from the  DataSource
    		    Connection con=ds.getConnection();
    		   //create PreparedStatemetn object  using the  con obj
    		   PreparedStatement ps=con.prepareStatement(GET_EMPS_BY_DESGS);
    		    ){  //try with resource
    	       //set  method args  as the  query param values
    	      ps.setString(1,desg1);  ps.setString(2,desg2); ps.setString(3, desg3);
    	     try(// execute  the SQL Query
    	    		 ResultSet rs=ps.executeQuery();
    	    		 ){   //nested try with resource
    	    	   // initialize the ArrayList
    	    	 list=new ArrayList();	
    	    	 //process the ResultSet to copy its  recods  List<Employee> obj  as the Employee objs
    	    	 while(rs.next()) {
    	    		 // copy each record  to Employee class obj
    	    		 Employee  emp=new Employee();
    	    		 emp.setEno(rs.getInt(1));
    	    		 emp.setEname(rs.getString(2));
    	    		 emp.setJob(rs.getString(3));
    	    		 emp.setSalary(rs.getDouble(4));
    	    		 emp.setDeptno(rs.getInt(5));
    	    		//add Employee class obj to List Colelction
    	    		 list.add(emp);
    	    	 }//while
    	     }//try2
      }//try
      catch(SQLException se) {  //for handling known exeception
    	  se.printStackTrace();
    	  throw se; // Exeption rethrowing for Exception Propagration
      }
      catch(Exception e) {  //for handling  unknown exception
    	   e.printStackTrace();
    	   throw e;
      }
		return list;
	}//method

}//class
