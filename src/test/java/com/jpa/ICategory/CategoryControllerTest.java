package com.jpa.ICategory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTempate;

    @Test
    public void CategoryTest() {
        String url = "http://localhost:"+port;
        CategoryDto requestInsert = CategoryDto.builder().build();
        ResponseEntity<ICategory> responseInsert = this.testRestTempate.postForEntity(url + "/ct"
                ,requestInsert, ICategory.class);
        assertThat(responseInsert).isNotNull();
        assertThat(responseInsert.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


        CategoryDto requestInsert2 = CategoryDto.builder().name("RestFulAPI Input").build();
        ResponseEntity<CategoryDto> responseInsert2 = this.testRestTempate.postForEntity(url + "/ct"
                ,requestInsert2, CategoryDto.class);
        assertThat(responseInsert2).isNotNull();
        assertThat(responseInsert2.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("responseInsert2.getBody().getId() = "+responseInsert2.getBody().getId());
        assertThat(responseInsert2.getBody().getName()).isEqualTo("RestFulAPI Input");


    }
}
