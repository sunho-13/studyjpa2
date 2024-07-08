package com.jpa.ICategory;

import java.util.List;

public interface ICategoryService {
    ICategory findById(Long id);
    ICategory findByName(String name);
    List<ICategory> getAllList();
    ICategory insert(ICategory category) throws Exception;
    boolean remove(Long id) throws Exception;
    ICategory update(Long id, ICategory cateogry) throws Exception;
    List<ICategory> findAllByNameContains(String name);
}
