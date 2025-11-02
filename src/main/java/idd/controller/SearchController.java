package idd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import idd.util.Oggetti.Risposta;
import idd.util.Searcher;

@Controller
class SearchController {

    private final Searcher searcher = new Searcher();
    private Risposta risultato;

    @GetMapping("/")
    public String home() {
        return "search"; //mostra la pagina HTML di ricerca
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        try {
            risultato = searcher.searchIndex(query);
            model.addAttribute("results", risultato);
            model.addAttribute("query", query);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "search";
    }
}

