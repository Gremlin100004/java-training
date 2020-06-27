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
    public void addMaster(Master master) {
        master.setId(this.idGeneratorMaster.getId());
        this.masters.add(master);
    }

    @Override
    public void updateMaster(Master master) {
        this.masters.remove(master);
        this.masters.add(master);
    }

    @Override
    public void deleteMaster(Master master) {
        this.masters.remove(master);
    }

    @Override
    public List<Master> getMasters() {
        return new ArrayList<>(this.masters);
    }

    @Override
    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }
}
