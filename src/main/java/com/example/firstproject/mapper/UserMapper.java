package com.example.firstproject.mapper;

import com.example.firstproject.entity.Part;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Part> findBySearch(@Param("searchTerm") String searchTerm, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<Part> findPartBy(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int countParts();

    Part findByPartCode(@Param("partCode") String partCode);
}
