package com.jpa.ICategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Override
    public ICategory findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        Optional<CategoryEntity> entity = this.categoryJpaRepository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public ICategory findByName(String name) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        Optional<CategoryEntity> find = this.categoryJpaRepository.findByName(name);
        return find.orElse(null);
    }

    @Override
    public List<ICategory> getAllList() {
        List<ICategory> list = this.getICategoryList(
                this.categoryJpaRepository.findAll()
        );
        return list;
    }

    private List<ICategory> getICategoryList(List<CategoryEntity> list) {
        if ( list == null || list.size() <= 0 ) {
            return new ArrayList<>();
        }
        // input : [CategoryEntity|CategoryEntity|CategoryEntity|CategoryEntity|CategoryEntity]
//        List<ICategory> result = new ArrayList<>();
//        for( CategoryEntity entity : list ) {
//            result.add( (ICategory)entity );
//        }
        List<ICategory> result = list.stream()
                .map(entity -> (ICategory)entity)
                .toList();
        // return : [ICategory|ICategory|ICategory|ICategory|ICategory]
        return result;
    }

    @Override
    public ICategory insert(ICategory category) throws Exception {
        if ( !isValidInsert(category) ) {
            return null;
        }
        CategoryEntity entity = new CategoryEntity();
        entity.copyFields(category);
        entity.setId(0L);
        ICategory result = this.categoryJpaRepository.saveAndFlush(entity);
        return result;
    }

    private boolean isValidInsert(ICategory category) {
        if ( category == null ) {
            return false;
        } else if ( category.getName() == null || category.getName().isEmpty() ) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Long id) throws Exception {
        ICategory find = this.findById(id);
        if ( find == null ) {
            return false;
        }
        this.categoryJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public ICategory update(Long id, ICategory category) throws Exception {
        ICategory find = this.findById(id);
        if ( find == null ) {
            return null;
        }
        find.copyFields(category);
        ICategory result = this.categoryJpaRepository.saveAndFlush((CategoryEntity)find);
        return result;
    }

    @Override
    public List<ICategory> findAllByNameContains(String name) {
        if ( name == null || name.isEmpty() ) {
            //return List.of();
            return new ArrayList<>();
        }
        List<ICategory> list = this.getICategoryList(
                this.categoryJpaRepository.findAllByNameContains(name)
        );
        return list;
    }
}
