package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.FeatureModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.FeatureService;
import com.ecommerceapp.ecommerceserver.model.entity.Feature;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/features")
    public ResponseEntity add(@RequestBody Feature feature) {
        EntityModel<Feature> featureEntityModel = featureModelAssembler.toModel(featureService.save(feature));

        return ResponseEntity.created(featureEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(featureEntityModel);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/features/{id}")
    public ResponseEntity<?> edit(@RequestBody Feature feature, @PathVariable Long id) {
        EntityModel<Feature> featureEntityModel = featureModelAssembler.toModel(featureService.edit(feature, id));

        return ResponseEntity.created(featureEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(featureEntityModel);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/features/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        featureService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
