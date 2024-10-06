package com.example.demo.api;
import com.example.demo.entity.CategoryCollection;
import com.example.demo.service.CategoryCollectionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "api")
@RequestMapping("/api/categoryCollection")
@RestController
public class CategoryCollectionAPI {
    @Autowired
    CategoryCollectionService selectService;

//    @PostMapping
//    public ResponseEntity<ShowMoreService> create(@Valid @RequestBody ShowMoreService select) {
//        ShowMoreService newSelect = selectService.createNewSelectService(select);
//        return ResponseEntity.ok(newSelect);
//    }

    @GetMapping
    public ResponseEntity get() {
        List<CategoryCollection> selectList = selectService.getAllSelect();
        return ResponseEntity.ok(selectList);
    }
}

//    @PutMapping("{id}")
//    public ResponseEntity<ShowMoreService> update (@Valid @RequestBody ShowMoreService select, @PathVariable long id) {
//        ShowMoreService newSelect =selectService.update(select, id);
//        return ResponseEntity.ok(newSelect);
//    }
//    @DeleteMapping("{id}")
//    public ResponseEntity<ShowMoreService> delete(@PathVariable long id) {
//        ShowMoreService select = selectService.delete(id);
//        return ResponseEntity.ok(select);
//    }
//}
