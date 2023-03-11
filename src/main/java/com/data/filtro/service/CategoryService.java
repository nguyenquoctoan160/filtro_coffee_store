package com.data.filtro.service;

import com.data.filtro.model.Category;
import com.data.filtro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


   public List<Category> getAll (){
        return categoryRepository.findAll();
    }


}
