package com.example.presentationLayer;

import com.example.serviceLayer.DTOs.GradeDTO;
import com.example.serviceLayer.IGradeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Validated

public class GradesController {

    private final String headerAPIVersion1 = "X-API-VERSION=v1";
    private final String headerAPIVersion2 = "X-API-VERSION=v2";

    @Autowired
    IGradeService gradeService;

    @GetMapping(path = "", headers = {headerAPIVersion2})
    public ResponseEntity<List<GradeDTO>> read(){
        return new ResponseEntity<List<GradeDTO>>(gradeService.read(), HttpStatus.OK);
    }

    @PostMapping(path = "", headers = {headerAPIVersion2})
    public ResponseEntity<GradeDTO> create(@RequestBody @Valid GradeDTO gradeDTO) {
        var gradeAfterCreated = gradeService.create(gradeDTO);
        return new ResponseEntity<GradeDTO>(gradeAfterCreated, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", headers = {headerAPIVersion2})
    public ResponseEntity<GradeDTO> read(@PathVariable("id") int id) {
        return new ResponseEntity<GradeDTO>(gradeService.read(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", headers = {headerAPIVersion1})
    public ResponseEntity<String> readV2(@PathVariable("id") int id) {
        return new ResponseEntity<String>("this is v2 API", HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", headers = {headerAPIVersion2})
    public ResponseEntity<?> read(@PathVariable("id") int gradeId, @RequestBody @Valid GradeDTO gradeDTO) {
        gradeService.update(gradeId, gradeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}", headers = {headerAPIVersion2})
    public ResponseEntity<?> delete(@PathVariable("id") int gradeId) {
        gradeService.delete(gradeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/gradesWithPagingAndSorting", headers = {headerAPIVersion2})
    public ResponseEntity<List<GradeDTO>> read( @Positive @RequestParam("pageSize") int pageSize,
                                                @Positive @RequestParam("pageNumber")  int pageNumber,
                                                @RequestParam("sortField")  String sortField,
                                                @RequestParam("sortType")  String sortType) {
        return new ResponseEntity<List<GradeDTO>>(gradeService.read(pageNumber, pageSize, sortField, sortType), HttpStatus.OK);
    }

    @GetMapping(path = "/gradesWithSearching", headers = {headerAPIVersion2})
    public ResponseEntity<List<GradeDTO>> read(@RequestParam("searchField") String searchField,
                                                 @RequestParam("searchValue") String searchValue) {
        return new ResponseEntity<List<GradeDTO>>(gradeService.read(searchField, searchValue), HttpStatus.OK);
    }
}
