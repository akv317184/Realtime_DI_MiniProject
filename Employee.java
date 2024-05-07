package com.nt.model;

import lombok.Data;

@Data
public class Employee {
	//bean properties
	private Integer eno;
	private String  ename;
	private  String job;
	private  Double salary;
	private  Integer deptno;
	private  Double grossSalary;
	private  Double netSalary;

}
