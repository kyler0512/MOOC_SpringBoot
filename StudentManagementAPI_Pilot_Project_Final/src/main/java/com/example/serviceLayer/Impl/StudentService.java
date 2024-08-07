package com.example.serviceLayer.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.repositoryLayer.IBaseStudentRepository;
import com.example.repositoryLayer.IStudentRepository;
import com.example.repositoryLayer.Entities.StudentEntity;
import com.example.serviceLayer.IStudentService;
import com.example.serviceLayer.DTOs.StudentDTO;
import com.example.utils.ErrorMessage;

@Service
public class StudentService implements IStudentService {

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    IBaseStudentRepository baseStudentRepository;

    @Override
    public List<StudentDTO> read() {
        var studentEntities = baseStudentRepository.findAll();
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            var studentDTO = new StudentDTO(studentEntity.getId(), studentEntity.getFirstName(),
                    studentEntity.getLastName(), studentEntity.getPhone());
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO read(int id) {
        var studentEntity = studentRepository.read(id);
        return new StudentDTO(studentEntity.getId(), studentEntity.getFirstName(), studentEntity.getLastName(),
                studentEntity.getPhone());
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        var studentEntity = new StudentEntity(studentDTO.getFirstName(), studentDTO.getLastName(),
                studentDTO.getPhone());
        baseStudentRepository.save(studentEntity);
        return studentDTO;
    }

    @Override
    public void update(int id, StudentDTO studentDTO) {
        var studentEntity = baseStudentRepository.findById(id).get();
        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setPhone(studentDTO.getPhone());

        baseStudentRepository.save(studentEntity);
    }

    @Override
    public void delete(int id) {
        baseStudentRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> read(int pageNumber, int pageSize, String sortField, String sortType) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        Sort.Direction currentSortType = Sort.Direction.ASC;
        if (sortType.equalsIgnoreCase("desc")) {
            currentSortType = Sort.Direction.DESC;
        }
        Pageable paging = PageRequest.of(pageNumber, pageSize,
                Sort.by(currentSortType, sortField.replace("\"", "").trim()));
        Page<StudentEntity> dataEntityList = baseStudentRepository.findAll(paging);
        dataEntityList.stream().forEach(studentEntity -> {
            StudentDTO gradeDTO = new StudentDTO(studentEntity.getId(), studentEntity.getFirstName(),
                    studentEntity.getLastName(), studentEntity.getPhone());
            studentDTOList.add(gradeDTO);
        });
        return studentDTOList;
    }

    @Override
    public List<StudentDTO> read(String searchField, String searchValue) {
        Stream<StudentEntity> studentsEntity = baseStudentRepository.findAll().stream()
                .filter(s -> s.getLastName().contains(searchValue));
        if (searchField.equalsIgnoreCase("phone"))
            studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getPhone().contains(searchValue));

        else if (searchField.equalsIgnoreCase("firstname"))
            studentsEntity = baseStudentRepository.findAll().stream()
                    .filter(s -> s.getFirstName().contains(searchValue));

        // Manual Mapper
        List<StudentDTO> studentDTOs = new ArrayList<>();
        studentsEntity.forEach(student -> {
            StudentDTO dto = new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(),
                    student.getPhone());
            studentDTOs.add(dto);
        });

        return studentDTOs;
    }

}
