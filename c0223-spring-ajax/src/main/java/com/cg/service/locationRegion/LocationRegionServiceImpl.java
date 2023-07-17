package com.cg.service.locationRegion;

import com.cg.model.LocationRegion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LocationRegionServiceImpl implements ILocationRegionService{
    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {
        return null;
    }

    @Override
    public void delete(LocationRegion locationRegion) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
