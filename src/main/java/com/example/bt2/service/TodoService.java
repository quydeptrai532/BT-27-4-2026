package com.example.bt2.service;

import com.example.bt2.entity.Todo;
import com.example.bt2.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public void save(Todo todo) {
        // Hibernate tự động phân biệt:
        // Nếu todo có ID tồn tại -> Update
        // Nếu todo chưa có ID -> Insert
        todoRepository.save(todo);
    }

    public boolean deleteById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}