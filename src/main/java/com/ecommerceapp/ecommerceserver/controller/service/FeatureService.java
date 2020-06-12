package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.Feature;
import com.ecommerceapp.ecommerceserver.model.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {
    private FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> getAll() {
        return featureRepository.findAll();
    }

    public Feature getOne(Long id) {
        return featureRepository.getOne(id);
    }

    public Feature save(Feature newFeature) {
        if (newFeature != null) {
            return featureRepository.save(newFeature);
        }
        return null;
    }

    public Feature edit(Feature editedFeature, Long id) {
        return featureRepository.findById(id)
                .map(feature -> {
                    feature.setName(editedFeature.getName());
                    return featureRepository.save(feature);
                })
                .orElseGet(() -> {
                    editedFeature.setId(id);
                    return featureRepository.save(editedFeature);
                });
    }

    public void delete(Long id) {
        featureRepository.deleteById(id);
    }
}
