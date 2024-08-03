package com.example;

import com.example.repositoryLayer.Entities.GradeEntity;
import com.example.repositoryLayer.IBaseGradeRepository;
import com.example.repositoryLayer.IBaseStudentRepository;
import com.example.repositoryLayer.Entities.StudentEntity;
import com.example.serviceLayer.DTOs.GradeDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(IBaseStudentRepository studentRepository, IBaseGradeRepository gradeRepository) {
		return (args) -> {
			// save few students
			StudentEntity student1 = new StudentEntity("Tran", "Vu Khang", "0972757056");
			StudentEntity student2 = new StudentEntity("Vo", "Hoang Thanh", "0465756767");
			StudentEntity student3 = new StudentEntity("Nguyen", "Trong Khang", "078978564");
			GradeEntity garde1 = new GradeEntity("Java", 10,1);
			GradeEntity garde2 = new GradeEntity("C#", 9,2);
			GradeEntity garde3 = new GradeEntity("Python", 8,3);

			studentRepository.save(student1);
			studentRepository.save(student2);
			studentRepository.save(student3);
			gradeRepository.save(garde1);
			gradeRepository.save(garde2);
			gradeRepository.save(garde3);
		};
	}
}
