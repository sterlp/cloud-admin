package org.sterl.cloudadmin.impl.identity;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

@RestController
public class IdentityBF {
    @Autowired Validator validator;

    @PostMapping("api/validate")
    ResponseEntity<IdentityBE> validate(@RequestBody @NotNull @Valid IdentityBE e) {
        return ResponseEntity.ok(e);
    }
}
