package com.vmdev.eshop.http.controller;

import com.vmdev.eshop.dto.ClientCreateEditDto;
import com.vmdev.eshop.dto.ClientOrderDto;
import com.vmdev.eshop.entity.enums.Role;
import com.vmdev.eshop.service.ClientOrderService;
import com.vmdev.eshop.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientOrderService clientOrderService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return clientService.findById(id)
                .map(client -> {
                    userDetails.getAuthorities();
                    List<ClientOrderDto> clientOrders = clientOrderService.findAllByClient(client.getId());
                    model.addAttribute("client", client);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("orders", clientOrders);
                    return "client/client";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@Validated ClientCreateEditDto clientDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("client", clientDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        clientService.create(clientDto);
        return "redirect:/login";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable("id") Long id,
                         @Validated ClientCreateEditDto clientDto) {
        return clientService.update(id, clientDto)
                .map(it -> "redirect:/clients/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (clientService.delete(id)) {
            return "redirect:/clients";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
