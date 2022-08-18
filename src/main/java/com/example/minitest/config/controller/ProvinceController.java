package com.example.minitest.config.controller;

import com.example.minitest.config.model.Country;
import com.example.minitest.config.model.Province;
import com.example.minitest.config.service.ICountryService;
import com.example.minitest.config.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private ICountryService iCountryService;

    @Autowired
    private IProvinceService iProvinceService;

    @ModelAttribute("countries")
    public List<Country> getCountries() {
        return iCountryService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProvinces(@RequestParam("search") Optional<String> search,
                                     @RequestParam("id") Optional<Long> id,
                                     @PageableDefault(value = 4) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        if (search.isPresent()) {
            modelAndView.addObject("provinces", iProvinceService.pageProvincesByNameSearch(search.get(), pageable));
            modelAndView.addObject("search", search.get());
        } else if (id.isPresent()) {
            modelAndView.addObject("provinces", iProvinceService.pageProvincesByCountry(id.get(), pageable));
            modelAndView.addObject("id", id.get());
        } else {
            modelAndView.addObject("provinces", iProvinceService.pageProvinces(pageable));
        }
        return modelAndView;
    }

    @RequestMapping("/popular")
    public ModelAndView topPopular() {
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("provinces", iProvinceService.top3LessPopular());
        modelAndView.addObject("status", true);
        return modelAndView;
    }

    @RequestMapping("/area")
    public ModelAndView topArea() {
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("provinces", iProvinceService.top3GreatArea());
        modelAndView.addObject("status", true);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView formCreateProvince() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProvince(@ModelAttribute("province") Optional<Province> province,
                                 RedirectAttributes redirectAttributes) {
        if (province.isPresent()) {
            iProvinceService.save(province.get());
            redirectAttributes.addFlashAttribute("mess", "Create done!");
        } else {
            redirectAttributes.addFlashAttribute("mess", "Create fail!");
        }
        return "redirect:/province";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView formUpdateProvince(@PathVariable("id") Long id,
                                           @PageableDefault(value = 4) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("update");
        return getModelAndView(id, modelAndView, pageable);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateProvince(@ModelAttribute("province") Optional<Province> province,
                                 RedirectAttributes redirectAttributes) {
        if (province.isPresent()) {
            iProvinceService.save(province.get());
            redirectAttributes.addFlashAttribute("mess", "Update done!");
        } else {
            redirectAttributes.addFlashAttribute("mess", "Update fail!");
        }
        return "redirect:/province";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteProvince(@PathVariable("id") Long id) {
        iProvinceService.delete(id);
        return "redirect:/province";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView detailProvince(@PathVariable("id") Long id,
                                       @PageableDefault(value = 4) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("detail");
        return getModelAndView(id, modelAndView, pageable);
    }

    private ModelAndView getModelAndView(Long id, ModelAndView modelAndView, Pageable pageable) {
        Optional<Province> province = iProvinceService.findById(id);
        if (province.isPresent()) {
            modelAndView.addObject("province", province.get());
            return modelAndView;
        } else {
            ModelAndView errorView = new ModelAndView("display");
            errorView.addObject("mess", "Not exist update id!");
            errorView.addObject("provinces", iProvinceService.pageProvinces(pageable));
            return errorView;
        }
    }
}
