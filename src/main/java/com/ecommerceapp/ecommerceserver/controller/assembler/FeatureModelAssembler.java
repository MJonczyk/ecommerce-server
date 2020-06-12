package com.ecommerceapp.ecommerceserver.controller.assembler;

import com.ecommerceapp.ecommerceserver.controller.FeatureController;
import com.ecommerceapp.ecommerceserver.model.entity.Feature;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeatureModelAssembler implements RepresentationModelAssembler<Feature, EntityModel<Feature>> {
    @Override
    public EntityModel<Feature> toModel(Feature feature) {
        return new EntityModel<>(feature,
                linkTo(methodOn(FeatureController.class).getOne(feature.getId())).withSelfRel(),
                linkTo(methodOn(FeatureController.class).getAll()).withRel("features")
        );
    }
}
