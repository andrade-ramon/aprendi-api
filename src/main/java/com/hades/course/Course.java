package com.hades.course;

import static javax.persistence.CascadeType.ALL;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hades.college.College;

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "modality")
	private String modality;
	
	@Column(name = "type")
	private String type;
	
	@ManyToMany(cascade = ALL)
	private List<College> colleges;
}
