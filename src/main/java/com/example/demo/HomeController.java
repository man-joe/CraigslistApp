package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String listJobs(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String jobForm(Model model){
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute Job job, @RequestParam(name="date") String date){

        try {
            String pattern = "yyyy-MM-dd";
            System.out.println(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String formattedDate = date.substring(0);
            System.out.println("formatted: " + formattedDate);
            Date realDate = simpleDateFormat.parse(formattedDate);
            System.out.println(realDate.toString());
        }
        catch (java.text.ParseException e){
            e.printStackTrace();
        }
        jobRepository.save(job);
        return "redirect:/";
    }

    @PostMapping("/searchlist")
    public String searchResult(Model model, @RequestParam(name="search") String search) {
        model.addAttribute("jobs" , jobRepository.findByTitleContainingIgnoreCase(search));
        return "searchlist";
    }

    @RequestMapping("/update/{id}")
    public String updateFlight(@PathVariable("id") long id , Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("/detail/{id}")
    public String showFlight(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobdetail";
    }

    @RequestMapping("/delete/{id}")
    public String delFlight(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }
}
