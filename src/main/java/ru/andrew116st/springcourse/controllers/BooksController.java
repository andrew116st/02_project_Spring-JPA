package ru.andrew116st.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andrew116st.springcourse.models.Books;
import ru.andrew116st.springcourse.models.Person;
import ru.andrew116st.springcourse.services.BookService;
import ru.andrew116st.springcourse.services.PeopleService;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BooksController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()

    public String index(Model model, @RequestParam(value = "page",required = false) String page,
                        @RequestParam(value = "size",required = false ) String size,
                        @RequestParam(value = "sort",required = false ) String sort){

        boolean paginationEnabled = (page != null && size != null);
        boolean sortingEnabled = (sort != null && sort.equals("true"));


        // пагинация - сортировки НЕТ
        if (paginationEnabled && !sortingEnabled){

            int currentPage = Integer.parseInt(page);
            int currentSize = Integer.parseInt(size);

            model.addAttribute("books", bookService.findAll(currentPage, currentSize));

            // пагинация + сортировка ЕСТЬ
        }else if (paginationEnabled && sortingEnabled) {

            int currentPage = Integer.parseInt(page);
            int currentSize = Integer.parseInt(size);

            model.addAttribute("books",bookService.findComboFatality(currentPage, currentSize));

            // только сортировка
        } else if (sortingEnabled){
            model.addAttribute("books", bookService.findAllSortByYear());

            // по умолчанию
        } else{
            model.addAttribute("books", bookService.findAll());

        }
        return "books/index";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("books", bookService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("whoGrabBook", bookService.whoGrabBook(id));


        return "books/show";
    }


    @GetMapping("/new")
    public String newBooks(@ModelAttribute("books") Books books) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") @Valid Books books,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(books);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("books", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Books books, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, books);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook (@PathVariable("id") int id) {
             bookService.clearPersonBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/setPerson")
    public String setPerson (@ModelAttribute("whoGrabBook") Person selectedPerson,
                                 @PathVariable("id") int id)  {
        bookService.indexPersonBook(id, selectedPerson.getId());
        return "redirect:/books/" + id;
    }






}
