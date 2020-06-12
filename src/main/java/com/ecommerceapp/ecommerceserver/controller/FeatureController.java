package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.FeatureModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.FeatureService;
import com.ecommerceapp.ecommerceserver.model.entity.Feature;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FeatureController {
    private FeatureService featureService;
    private FeatureModelAssembler featureModelAssembler;

    public FeatureController(FeatureService featureService, FeatureModelAssembler featureModelAssembler) {
        this.featureService = featureService;
        this.featureModelAssembler = featureModelAssembler;
    }

    @GetMapping("/features")
    public CollectionModel<EntityModel<Feature>> getAll() {
        List<EntityModel<Feature>> features = featureService.getAll()
                .stream()
                .map(featureModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(features, linkTo(methodOn(FeatureController.class).getAll()).withSelfRel());
    }

    @GetMapping("/features/{id}")
    public EntityModel<Feature> getOne(@PathVariable Long id) {
        return featureModelAssembler.toModel(featureService.getOne(id));
    }
}
