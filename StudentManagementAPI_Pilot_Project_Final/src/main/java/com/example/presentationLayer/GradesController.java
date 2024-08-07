package com.example.presentationLayer;

import com.example.serviceLayer.DTOs.ErrorDTO;
import com.example.serviceLayer.DTOs.GradeDTO;
import com.example.serviceLayer.DTOs.ValidationDTO;
import com.example.serviceLayer.IGradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
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
    public ResponseEntity create(@RequestBody GradeDTO gradeDTO) {
        ErrorDTO errorDTO = gradeService.validateGrade(gradeDTO);
        if (StringUtils.isBlank(errorDTO.getMessage())) {
            var gradeAfterCreated = gradeService.create(gradeDTO);
            return new ResponseEntity<GradeDTO>(gradeAfterCreated, HttpStatus.CREATED);
        } else {
            var validationModel = new ValidationDTO("create(grade)", errorDTO.getDestination(), errorDTO.getMessage());
            return new ResponseEntity<ValidationDTO>(validationModel, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> read(@PathVariable("id") int gradeId, GradeDTO gradeDTO) {
        try {
            gradeService.update(gradeId, gradeDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            var errorModel = new ErrorDTO("update(Grade grade, int gradeId)",
                    Arrays.stream(exception.getStackTrace()).findFirst().toString(),
                    exception.getMessage(),
                    exception.getStackTrace().toString());

            return new ResponseEntity<ErrorDTO>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}", headers = {headerAPIVersion2})
    public ResponseEntity<?> delete(@PathVariable("id") int gradeId) {
        gradeService.delete(gradeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/gradesWithPagingAndSorting", headers = {headerAPIVersion2})
    public ResponseEntity<List<GradeDTO>> read(@RequestParam("pageSize") int pageSize,
                                                 @RequestParam("pageNumber") int pageNumber,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortType") String sortType) {
        return new ResponseEntity<List<GradeDTO>>(gradeService.read(pageNumber, pageSize, sortField, sortType), HttpStatus.OK);
    }

    @GetMapping(path = "/gradesWithSearching", headers = {headerAPIVersion2})
    public ResponseEntity<List<GradeDTO>> read(@RequestParam("searchField") String searchField,
                                                 @RequestParam("searchValue") String searchValue) {
        return new ResponseEntity<List<GradeDTO>>(gradeService.read(searchField, searchValue), HttpStatus.OK);
    }
}
