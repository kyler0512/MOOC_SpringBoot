package com.example.serviceLayer;

import com.example.serviceLayer.DTOs.GradeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGradeService {
    List<GradeDTO> read();
    GradeDTO read(int id);
    List<GradeDTO> read(int pageNumber, int pageSize, String sortField, String sortType);
    List<GradeDTO> read(String searchField, String searchValue);
    GradeDTO create(GradeDTO grade);
    void update(int id, GradeDTO grade);
    void delete(int id);
}
