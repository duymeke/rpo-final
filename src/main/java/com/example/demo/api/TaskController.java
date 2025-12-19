package com.example.demo.api;


import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.TaskService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Long userId = getCurrentUserId();
        return new ResponseEntity<>(service.getAll(userId), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ){
        Long userId = getCurrentUserId();
        TaskDto task = service.getById(id, userId);
        if (task == null) {
            return new ResponseEntity<>("Task not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TaskDto dto) {

        Long userId = getCurrentUserId();
        if (userId == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        dto.setUserId(userId);
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TaskDto dto) {
        Long userId = getCurrentUserId();
        TaskDto updated = service.update(id, dto, userId);
        if (updated == null) {
            return new ResponseEntity<>("Task not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        boolean deleted = service.delete(id, userId);
        if (!deleted) {
            return new ResponseEntity<>("Task not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return user != null ? user.getId() : null;
    }
}
