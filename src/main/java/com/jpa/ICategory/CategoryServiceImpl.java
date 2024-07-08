package com.jpa.ICategory;

import com.jpa.PhoneBook.IPhoneBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private boolean isValidInsert(IPhoneBook dto) {
        if (dto == null) {
            return false;
        } else if (dto.getName() == null || dto.getName().isEmpty()) {
            return false;
        } else if (dto.getCategory() == null) {
            return false;
        }
        return true;
    }

    @Override
    public ICategory findById(Long id) {
        if(id==null || id <= 0){
            return null;
        }
        Optional<CategoryEntity> entity = this.categoryJpaRepository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public ICategory findByName(String name) {
        if(name ==null || name.isEmpty()){
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
        if (list == null || list.size() <= 0) {

            return new ArrayList<>();
        }
        List<ICategory> result = new ArrayList<>();

        for (CategoryEntity entity : list) {
            result.add((ICategory) entity);
        }
        return result;
    }

    @Override
    public ICategory insert(ICategory category) throws Exception {
        if (isValidInsert(category)) {
            return null;
        }
        CategoryEntity entity = CategoryEntity.builder()
                .id(0L).name(category.getName()).build();
        CategoryEntity result = this.categoryJpaRepository.saveAndFlush(entity);
        return result;
    }

    private boolean isValidInsert(ICategory category){
        if(category == null){
            return false;
        }else if(category.getName() == null || category.getName().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Long id) throws Exception {
        ICategory find = this.findById(id);
        if(find == null){
            return false;
        }
        this.categoryJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public ICategory update(Long id, ICategory category) throws Exception {
        ICategory find = this.findById(id);
        if(find == null){
            return null;
        }
        find.copyFields(category);
        CategoryEntity result = this.categoryJpaRepository.saveAndFlush((CategoryEntity)find);
        return result;
    }

    @Override
    public List<ICategory> findAllByNameContains(String name) {
        if(name==null || name.isEmpty()){
            return List.of();
        }
        List<ICategory> list = this.getICategoryList(
            this.categoryJpaRepository.findAllByNameContains(name)
        );
        return list;
    }
}
