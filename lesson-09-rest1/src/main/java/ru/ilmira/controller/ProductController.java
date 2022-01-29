package ru.ilmira.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ilmira.persist.Category;
import ru.ilmira.persist.CategoryRepository;
import ru.ilmira.service.CategoryService;
import ru.ilmira.service.ProductService;
import ru.ilmira.service.dto.CategoryDto;
import ru.ilmira.service.dto.ProductDto;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("minPriceFilter") Optional<String> minPriceFilter,
                           @RequestParam("maxPriceFilter") Optional<String> maxPriceFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort,
                           @RequestParam("dir") Optional<String> dir) {

        logger.info("Product filter with name pattern {}", nameFilter.orElse(null));
        logger.info("Product filter with minPriceFilter pattern {}", minPriceFilter.orElse(null));
        logger.info("Product filter with maxPriceFilter pattern {}", maxPriceFilter.orElse(null));

        String sortField = sort.filter(s -> !s.isBlank()).orElse("id");
        boolean sortDir = dir.orElse("").equalsIgnoreCase(Sort.Direction.ASC.name());
        Sort sortObj = sortDir ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        model.addAttribute("products", productService.findAll(
                nameFilter,
                minPriceFilter,
                maxPriceFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sortObj
        ));
        return "product";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryRepository.findAll());
        return "product_form";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", new CategoryDto());
        return "product_form";
    }

    @PostMapping
    public String save(@Valid ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "product_form";
        }
        productService.save(productDto);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}
