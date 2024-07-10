package com.jpa.ICategory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CategoryJpaRepositoryTest {
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private void AssertFields(ICategory left, ICategory right){
        assertThat(left).isNotNull();
        assertThat(right).isNotNull();
        assertThat(left.getId()).isEqualTo(right.getId());
        assertThat(left.getName()).isEqualTo(right.getName());
    }

    @Test
    @Order(1)
    public void CategoryInsertTest() {
        CategoryEntity insert = CategoryEntity.builder()
                .name("123456789012345678901").build();
        Throwable exception = assertThrows(Exception.class, () -> {
            categoryJpaRepository.save(insert);
        });
        System.out.println(exception.toString());


        CategoryEntity insert2 = CategoryEntity.builder().build();
        exception = assertThrows(Exception.class, () -> {
            categoryJpaRepository.save(insert2);
        });
        System.out.println(exception.toString());


        CategoryEntity insert3 = CategoryEntity.builder()
                .name("abcdef").build();
        CategoryEntity resultInsert = categoryJpaRepository.save(insert3);
        assertThat(resultInsert).isNotNull();
        assertThat(resultInsert.getId()).isGreaterThan(0L);
        assertThat(resultInsert.getName()).isEqualTo("abcdef");

    }

    @Test
    @Order(2)
    public void CategoryFindTest() {
        Optional<CategoryEntity> find1 = this.categoryJpaRepository.findById(0L);
        assertThat(find1).isNotNull();
        //assertThat(find1.orElse(null)).isNull();

        Optional<CategoryEntity> find2 = this.categoryJpaRepository.findByName("abcdef");
        ICategory find2ICategory = find2.orElse(null);
        assertThat(find2ICategory).isNotNull();

        Optional<CategoryEntity> find3 = this.categoryJpaRepository.findById(find2ICategory.getId());
        ICategory find3ICategory = find3.orElse(null);
        assertThat(find3ICategory).isNotNull();
        
        this.AssertFields(find3ICategory, find2ICategory);

/*        assertThat(find3ICategory.getId()).isEqualTo(find2ICategory.getId());
        assertThat(find3ICategory.getName()).isEqualTo(find2ICategory.getName());*/
    }

    @Test
    @Order(3)
    public void CategoryUpdateTest(){

        Optional<CategoryEntity> find2 = this.categoryJpaRepository.findByName("abcdef");
        ICategory find2ICategory = find2.orElse(null);
        assertThat(find2ICategory).isNotNull();

        find2ICategory.setName("ABCDEFGH");
        ICategory save = this.categoryJpaRepository.save((CategoryEntity) find2ICategory);

        Optional<CategoryEntity> find3 = this.categoryJpaRepository.findById(find2ICategory.getId());
        ICategory find3ICategory = find3.orElse(null);
        assertThat(find3ICategory).isNotNull();

        this.AssertFields(find3ICategory, find2ICategory);
/*        assertThat(find3ICategory.getId()).isEqualTo(find2ICategory.getId());
        assertThat(find3ICategory.getName()).isEqualTo(find2ICategory.getName());*/

    }

    @Test
    @Order(4)
    public void CategoryDeleteTest(){
        Optional<CategoryEntity> find3 = this.categoryJpaRepository.findByName("ABCDEFGH");
        ICategory find2ICategory = find3.orElse(null);
        assertThat(find2ICategory).isNotNull();

        this.categoryJpaRepository.deleteById(find2ICategory.getId());

        Optional<CategoryEntity> find4 = this.categoryJpaRepository.findById(find2ICategory.getId());
        ICategory find3ICategory = find4.orElse(null);
        assertThat(find3ICategory).isNull();
    }
}

