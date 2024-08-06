package com.example.presentationLayer;

import com.example.serviceLayer.DTOs.ErrorDTO;
import com.example.serviceLayer.DTOs.ValidationDTO;
import com.example.serviceLayer.IStudentService;
import com.example.serviceLayer.DTOs.StudentDTO;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final String headerAPIVersion1 = "X-API-VERSION=v1";

    @Autowired
    IStudentService studentService;

    @GetMapping("/studentsWithPagingAndSorting")
    public ResponseEntity<List<StudentDTO>> read(@RequestParam("pageSize") int pageSize,
                                                 @RequestParam("pageNumber") int pageNumber,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortType") String sortType) {
        return new ResponseEntity<List<StudentDTO>>(studentService.read(pageNumber, pageSize, sortField, sortType), HttpStatus.OK);
    }

    @GetMapping(path = "/studentsWithSearching", headers = {headerAPIVersion1})
    public ResponseEntity<List<StudentDTO>> read(@RequestParam("searchField") String searchField,
                                                 @RequestParam("searchValue") String searchValue) {
        return new ResponseEntity<List<StudentDTO>>(studentService.read(searchField, searchValue), HttpStatus.OK);
    }

    @GetMapping(path = "", headers = {headerAPIVersion1})
    public ResponseEntity<List<StudentDTO>> read() {
        return new ResponseEntity<List<StudentDTO>>(studentService.read(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", headers = {headerAPIVersion1})
    public ResponseEntity<StudentDTO> read(@PathVariable("id") int studentId) {
        return new ResponseEntity<StudentDTO>(studentService.read(studentId), HttpStatus.OK);
    }

    @PostMapping(path = "", headers = {headerAPIVersion1})
    public ResponseEntity create(@RequestBody StudentDTO studentDTO) {
        ErrorDTO errorDTO = studentService.validateStudent(studentDTO);
        if (StringUtils.isBlank(errorDTO.getMessage())) {
            var studentAfterCreated = studentService.create(studentDTO);
            return new ResponseEntity<StudentDTO>(studentAfterCreated, HttpStatus.CREATED);
        } else {
            var validationModel = new ValidationDTO("create(student)", errorDTO.getDestination(), errorDTO.getMessage());
            return new ResponseEntity<ValidationDTO>(validationModel, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}", headers = {headerAPIVersion1})
    public ResponseEntity<?> update(@RequestBody @Valid StudentDTO  studentDTO, @PathVariable("id") int studentId) {
        studentService.update(studentId, studentDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}", headers = {headerAPIVersion1})
    public ResponseEntity<?> delete(@PathVariable("id") int studentId) {
        studentService.delete(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
