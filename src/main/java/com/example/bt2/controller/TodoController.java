package com.example.bt2.controller;

import com.example.bt2.entity.Todo;
import com.example.bt2.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Optional;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping({"", "/"})
    public String showTodoList(Model model) {
        String owner = (String) session.getAttribute("ownerName");
        if (owner == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("todos", todoService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @PostMapping("/save")
    public String saveTodo(@Valid @ModelAttribute("todo") Todo todo,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "form"; // Lỗi validation -> Trả lại form
        }

        boolean isUpdate = (todo.getId() != null);
        todoService.save(todo);

        // Flash Attribute thông báo
        String msg = isUpdate ? "Cập nhật thành công!" : "Thêm mới thành công!";
        redirectAttributes.addFlashAttribute("message", msg);

        return "redirect:/todos";
    }

    // UPDATE: Lấy dữ liệu đổ ra Form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Todo> todoOpt = todoService.findById(id);
        if (todoOpt.isPresent()) {
            model.addAttribute("todo", todoOpt.get());
            return "form"; // Tái sử dụng file form.html
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy ghi chú với ID này!");
            return "redirect:/todos";
        }
    }

    // DELETE: Xóa dữ liệu
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = todoService.deleteById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Lỗi: Không thể xóa hoặc bản ghi không tồn tại!");
        }
        return "redirect:/todos";
    }
}