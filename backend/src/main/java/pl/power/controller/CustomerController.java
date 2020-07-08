package pl.power.controller;


import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.power.model.CustomerInfo;
import pl.power.services.exception.DoesNotExistException;
import pl.power.services.impl.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {


    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    public List<CustomerInfo> searchForCustomers(@RequestParam("username") Optional<String> username,
                                                 KeycloakPrincipal<KeycloakSecurityContext> principal) throws DoesNotExistException {
        if (username.isPresent()) {
            return customerService.getCustomerByUsername(username.get(), principal);
        }
        return customerService.getCustomers(principal);
    }

}
