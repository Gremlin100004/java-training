package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class MasterRepositoryImpl implements MasterRepository {
    private static MasterRepository instance;
    private List<Master> masters;
    private final IdGenerator idGeneratorMaster;

    private MasterRepositoryImpl() {
        this.masters = new ArrayList<>();
        this.idGeneratorMaster = new IdGenerator();
    }

    public static MasterRepository getInstance() {
        if (instance == null) {
            instance = new MasterRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Master> getMasters() {
        return masters;
    }

    @Override
    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }

    @Override
    public void setMasters(List<Master> masters) {
        this.masters = masters;
    }
}
