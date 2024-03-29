package org.example.web;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.entities.Patient;
import org.example.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;
@Controller @AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;


    @GetMapping("/user/index")
    public String patients(Model model, @RequestParam(name="page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String  keyword){
        //chercher dans db la liste des patients et la recuperer
        //List<Patient> patients = patientRepository.findAll();
        //model.addAttribute("listPatients",patients);
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }


    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
        }


    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam(name="id") Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(@Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/formPatients";
    }


    @GetMapping(path = "/admin/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@RequestParam(name="id") Long id,Model model){
        Patient patient= patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
        //        if(patient==null) throw new RuntimeException("patient introuvable");
    }

}


    /*approche rendu coté serveur : ytilisation de thymeleaf
    //approche rendu coté client : au dessous
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients (){
        return patientRepository.findAll();
    }*/

